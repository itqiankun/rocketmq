
package org.apache.rocketmq.example.simple.litepull;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class LitePullConsumerSubscribeHandleCommit {

    public static volatile boolean running = true;

    public static void main(String[] args) throws Exception {
        DefaultLitePullConsumer litePullConsumer = new DefaultLitePullConsumer("lite_pull_consumer_test");
        // 设置是否自动提交offset，默认值是true
        litePullConsumer.setAutoCommit(false);
        litePullConsumer.setNamesrvAddr("127.0.0.1:9876");
        litePullConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 此时SubscriptionType为SubscriptionType.SUBSCRIBE，使用该方式后用户不需要考虑消息队列分配的问题
        litePullConsumer.subscribe("TopicTest", "*");
        litePullConsumer.start();
        try {
            while (running) {
                List<MessageExt> messageExts = litePullConsumer.poll();
                System.out.printf("%s%n", messageExts);
                // 由于前面调用setAutoCommit方法将自动提交位点属性设置为false，所以这里调用commitSync将消费位点提交到内存中的offsetstore，最终会通过定时任务将消费位点提交给broker
                litePullConsumer.commitSync();
            }
        } finally {
            litePullConsumer.shutdown();
        }
    }
}

