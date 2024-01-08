
package org.apache.rocketmq.example.simple.OrderSendAndConsumer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * This class demonstrates how to send messages to brokers using provided {@link DefaultMQProducer}.
 */
public class ProducerOrderSend {

    /**
     * The number of produced messages.
     */
    public static final int MESSAGE_COUNT = 1;
    public static final String PRODUCER_GROUP = "please_rename_unique_group_name";
    public static final String DEFAULT_NAMESRVADDR = "127.0.0.1:9876";
    public static final String TOPIC = "orderTopic";
    public static final String TAG = "TagA";

    private static final String[] ORDER_MESSAGES = {"下单","结算","支付","完成"};


    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
        producer.start();

        //注：要实现顺序消费，必须同步发送消息
        for (int i = 0;i < 3;i++){
            for (int j = 0,size = ORDER_MESSAGES.length;j < size;j++){
                String message = "Order-" + i + "-" + ORDER_MESSAGES[j];
                byte[] messageBody = message.getBytes(RemotingHelper.DEFAULT_CHARSET);
                Message mqMsg = new Message(TOPIC, messageBody);
                producer.send(mqMsg, new MessageQueueSelector(){

                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        // 这里的arg就是`send()`里面的`i`
                        Integer id = (Integer) arg;
                        int index = id % mqs.size();
                        return mqs.get(index);
                    }
                }, i);
            }
        }
        producer.shutdown();
    }
}
