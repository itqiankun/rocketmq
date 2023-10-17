

package org.apache.rocketmq.proxy.grpc.v2.common;

import apache.rocketmq.v2.MessageQueue;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GrpcConverterTest {
    @Test
    public void testBuildMessageQueue() {
        String topic = "topic";
        String brokerName = "brokerName";
        int queueId = 1;
        MessageExt messageExt = new MessageExt();
        messageExt.setQueueId(queueId);
        messageExt.setTopic(topic);

        MessageQueue messageQueue = GrpcConverter.getInstance().buildMessageQueue(messageExt, brokerName);
        assertThat(messageQueue.getTopic().getName()).isEqualTo(topic);
        assertThat(messageQueue.getBroker().getName()).isEqualTo(brokerName);
        assertThat(messageQueue.getId()).isEqualTo(queueId);
    }
}