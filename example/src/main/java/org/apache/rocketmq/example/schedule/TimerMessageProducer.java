
package org.apache.rocketmq.example.schedule;

import java.nio.charset.StandardCharsets;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

@Slf4j
public class TimerMessageProducer {

    public static final String PRODUCER_GROUP = "TimerMessageProducerGroup";
    public static final String DEFAULT_NAMESRVADDR = "127.0.0.1:9876";
    public static final String TOPIC = "TimerTopic";

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
        producer.start();
        int totalMessagesToSend = 2;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message(TOPIC, ("Hello scheduled message " + i).getBytes(StandardCharsets.UTF_8));
            // 1分钟后执行
            message.setDeliverTimeMs(System.currentTimeMillis() + 1L * 60 * 1000);
            SendResult result = producer.send(message);
            log.info("send message result:{}", JSONObject.toJSONString(result));
        }
        producer.shutdown();
    }
}
