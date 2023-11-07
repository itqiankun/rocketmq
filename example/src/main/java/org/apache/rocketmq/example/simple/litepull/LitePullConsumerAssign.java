
package org.apache.rocketmq.example.simple.litepull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
public class LitePullConsumerAssign {

    public static volatile boolean running = true;

    public static void main(String[] args) throws Exception {
        DefaultLitePullConsumer litePullConsumer = new DefaultLitePullConsumer("please_rename_unique_group_name");
        litePullConsumer.setNamesrvAddr("127.0.0.1:9876");
        //设置是否自动提交offset，默认值是true
        litePullConsumer.setAutoCommit(false);
        litePullConsumer.start();
        //获取topic的MessageQueue集合
        Collection<MessageQueue> mqSet = litePullConsumer.fetchMessageQueues("TopicTest");
        List<MessageQueue> list = new ArrayList<>(mqSet);
        List<MessageQueue> assignList = new ArrayList<>();
        for (int i = 0; i < list.size() / 2; i++) {
            assignList.add(list.get(i));
        }
        //指定consumer消费的消息队列，使用该模式后消息队列不会自动重平衡，此时SubscriptionType为SubscriptionType.ASSIGN
        litePullConsumer.assign(assignList);
        //修改consumer拉取消息的偏移量
        litePullConsumer.seek(assignList.get(0), 10);
        try {
            while (running) {
                // 消息拉取
                List<MessageExt> messageExts = litePullConsumer.poll();
                System.out.printf("%s %n", messageExts);
                // 由于前面调用setAutoCommit方法将自动提交位点属性设置为false，所以这里调用commitSync将消费位点提交到内存中的offsetstore，最终会通过定时任务将消费位点提交给broker
                litePullConsumer.commitSync();
            }
        } finally {
            litePullConsumer.shutdown();
        }

    }
}
