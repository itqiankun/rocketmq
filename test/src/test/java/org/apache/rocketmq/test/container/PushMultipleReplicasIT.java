

package org.apache.rocketmq.test.container;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.rocketmq.container.InnerSalveBrokerController;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.awaitility.Awaitility.await;

@Ignore
public class PushMultipleReplicasIT extends ContainerIntegrationTestBase {
    private static DefaultMQProducer producer;

    private static final String TOPIC = PushMultipleReplicasIT.class.getSimpleName() + "_TOPIC";
    private static final String REDIRECT_TOPIC = PushMultipleReplicasIT.class.getSimpleName() + "_REDIRECT_TOPIC";
    private static final String CONSUMER_GROUP = PushMultipleReplicasIT.class.getSimpleName() + "_Consumer";
    private static final int MESSAGE_COUNT = 32;

    public PushMultipleReplicasIT() throws UnsupportedEncodingException {
    }

    @BeforeClass
    public static void beforeClass() throws Throwable {
        createTopicTo(master1With3Replicas, TOPIC,1, 1);
        producer = createProducer(PushMultipleReplicasIT.class.getSimpleName() + "_PRODUCER");
        producer.setSendMsgTimeout(15 * 1000);
        producer.start();

        for (int i = 0; i < MESSAGE_COUNT; i++) {
            producer.send(new Message(TOPIC, Integer.toString(i).getBytes()));
        }

        createTopicTo(master3With3Replicas, REDIRECT_TOPIC, 1, 1);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        producer.shutdown();
    }

    @Test
    public void consumeMessageFromSlave_PushConsumer() throws MQClientException {
        // Wait topic synchronization
        await().atMost(Duration.ofMinutes(1)).until(() -> {
            InnerSalveBrokerController slaveBroker = brokerContainer2.getSlaveBrokers().iterator().next();
           return slaveBroker.getTopicConfigManager().selectTopicConfig(TOPIC) != null;
        });
        isolateBroker(master1With3Replicas);
        DefaultMQPushConsumer pushConsumer = createPushConsumer(CONSUMER_GROUP);
        pushConsumer.subscribe(TOPIC, "*");
        pushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        AtomicInteger receivedMsgCount = new AtomicInteger(0);
        pushConsumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            receivedMsgCount.addAndGet(msgs.size());
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        pushConsumer.start();
        await().atMost(Duration.ofMinutes(5)).until(() -> receivedMsgCount.get() >= MESSAGE_COUNT);

        await().atMost(Duration.ofMinutes(1)).until(() -> {
            pushConsumer.getDefaultMQPushConsumerImpl().persistConsumerOffset();
            Map<Integer, Long> slaveOffsetTable = null;
            for (InnerSalveBrokerController slave : brokerContainer2.getSlaveBrokers()) {
                if (slave.getBrokerConfig().getBrokerName().equals(master1With3Replicas.getBrokerConfig().getBrokerName())) {
                    slaveOffsetTable = slave.getConsumerOffsetManager().queryOffset(CONSUMER_GROUP, TOPIC);
                }
            }

            if (slaveOffsetTable != null) {
                long totalOffset = 0;
                for (final Long offset : slaveOffsetTable.values()) {
                    totalOffset += offset;
                }

                return totalOffset >= MESSAGE_COUNT;
            }
            return false;
        });

        pushConsumer.shutdown();
        cancelIsolatedBroker(master1With3Replicas);

        awaitUntilSlaveOK();
    }
}
