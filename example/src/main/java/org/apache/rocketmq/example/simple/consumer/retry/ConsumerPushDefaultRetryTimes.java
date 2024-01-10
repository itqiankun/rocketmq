package org.apache.rocketmq.example.simple.consumer.retry;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.example.simple.ConsumerPushListenerTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * author: ma_qiankun
 * date:  2023/12/2
 **/
public class ConsumerPushDefaultRetryTimes {
    public static void main(String[] args) throws InterruptedException, MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumerPushRetry");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("retryTopic1", "*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            System.out.println("date="+new Date()+" *******");

            for(MessageExt msg :list){
                System.out.println("msg="+msg.getMsgId());
                System.out.println("date="+new Date());
                System.out.println("ReconsumeTimes="+msg.getReconsumeTimes());
                System.out.println();
            }
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();
    }
}
