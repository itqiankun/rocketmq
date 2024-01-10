package org.apache.rocketmq.example.simple.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.TimeUnit;

/**
 * author: ma_qiankun
 * date:  2023/12/2
 **/
public class ProducerRetryAsync {
    /**
     * The number of produced messages.
     */
    public static final int MESSAGE_COUNT = 1;
    public static final String PRODUCER_GROUP = "please_rename_unique_group_name";
    public static final String DEFAULT_NAMESRVADDR = "127.0.0.1:9876";
    public static final String TOPIC = "TopicTest";
    public static final String TAG = "TagA";


    // 暂时模拟不了发送失败进行重试的场景，不过底层原理怎么进行重试的，一看就知道了
    public static void main(String[] args) throws MQClientException, InterruptedException {

        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
        // 设置同步发送失败时重试发送的次数，默认为2次
        producer.setRetryTimesWhenSendFailed(10);
        // 设置发送超时时限为5s，默认3s
        producer.setSendMsgTimeout(5000);
        producer.start();

        TimeUnit.SECONDS.sleep(10);

        for (int i = 0; i < MESSAGE_COUNT; i++) {
            try {
                Message msg = new Message(TOPIC, TAG, ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }
//        producer.shutdown();
    }
}
