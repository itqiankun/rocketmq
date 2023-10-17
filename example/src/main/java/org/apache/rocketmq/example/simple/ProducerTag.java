
package org.apache.rocketmq.example.simple;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
@Slf4j
public class ProducerTag {

    public static final String PRODUCER_GROUP = "ProducerGroupName";
    public static final String DEFAULT_NAMESRVADDR = "127.0.0.1:9876";
    public static final String TOPIC = "TopicTest";
    public static final String TAG = "TagA";

    public static void main(String[] args) throws MQClientException, InterruptedException {
        sendMessageByTopic(TOPIC,"TagA");
        sendMessageByTopic(TOPIC,"TagB");
        sendMessageByTopic(TOPIC,"TagC");
        sendMessageByTopic(TOPIC,"TagD");
    }

    private static void sendMessageByTopic(String topic, String tag) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
        producer.start();
        for (int i = 0; i < 128; i++){
            try {
                Message msg = new Message(TOPIC, TAG, "OrderID188", "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.send(msg);
                log.info("返回结果:{}", JSONObject.toJSONString(sendResult));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        producer.shutdown();
    }
}
