package org.apache.rocketmq.example.simple.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByRandom;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ProducerAsyncRandomLoadBalance {

    public static final int MESSAGE_COUNT = 10;
    public static final String PRODUCER_GROUP = "please_rename_unique_group_name";
    public static final String DEFAULT_NAMESRVADDR = "127.0.0.1:9876";
    public static final String TOPIC = "TopicTest";
    public static final String TAG = "TagA";

    public static void main(String[] args) throws MQClientException, InterruptedException {

        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        producer.setNamesrvAddr(DEFAULT_NAMESRVADDR);
        producer.start();

        for (int i = 0; i < MESSAGE_COUNT; i++) {
            try {
                Message msg = new Message(TOPIC, TAG, ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                producer.send(msg,new SelectMessageQueueByRandom(), new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        log.info("发送数据成功:{}", sendResult.getOffsetMsgId());
                    }
                    @Override
                    public void onException(Throwable e) {
                        log.info("发送失败:", e);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }

        // 阻塞当前线程3秒，等待异步发送结果，这个是必须的，不然主线程不阻塞，producer就直接杀死了，producer就没有异步线程执行了
        TimeUnit.SECONDS.sleep(3);

        producer.shutdown();
    }
}
