package org.apache.rocketmq.example.simple.consumer.retry;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Date;
import java.util.List;

/**
 * author: ma_qiankun
 * date:  2023/12/2
 **/
public class ConsumerPushRetry {

    // 一般消费者应该这样配置，这样就不需要再处理`死信队列`了
    public static void main(String[] args) throws InterruptedException, MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumerPushRetry");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("retryTopic", "*");

        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt messageExt = list.get(0);
                try {
                    int i= 10 / 0;
                } catch (Exception e) {
                    if (messageExt.getReconsumeTimes() >= 2) {
                        //不要重试了
                        System.out.println("消息体：" + new String(messageExt.getBody()));
                        System.out.println("记录到特别的位置如mysql，发送邮件或短信通知人工处理");
                    } else {
                        //重试
                        System.out.println("时间：" + new Date() + "\t消息体：" + new String(messageExt.getBody()) + "\t重试次数：" + messageExt.getReconsumeTimes());
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

        });
        consumer.start();

    }
}
