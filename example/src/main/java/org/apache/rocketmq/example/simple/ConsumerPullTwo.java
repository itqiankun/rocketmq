
package org.apache.rocketmq.example.simple;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.store.ReadOffsetType;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@SuppressWarnings("deprecation")
public class ConsumerPullTwo {

    public static void main(String[] args) throws MQClientException {

        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("please_rename_unique_group_name_5");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        Set<String> topics = new HashSet<>();
        //You would better to register topics,It will use in rebalance when starting
        topics.add("om-huizhi-topic-cut");
        consumer.setRegisterTopics(topics);
        consumer.start();

        ExecutorService executors = Executors.newFixedThreadPool(topics.size(), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "PullConsumerThread");
            }
        });
        for (String topic : consumer.getRegisterTopics()) {

            executors.execute(new Runnable() {

                public void doSomething(List<MessageExt> msgs) {
                    //do your business
                    System.out.println(msgs.size());
                }

                @Override
                public void run() {
                    while (true) {
                        try {
                            Set<MessageQueue> messageQueues = consumer.fetchMessageQueuesInBalance(topic);
                            if (messageQueues == null || messageQueues.isEmpty()) {
                                Thread.sleep(1000);
                                continue;
                            }
                            PullResult pullResult = null;
                            for (MessageQueue messageQueue : messageQueues) {
                                try {
                                    long offset = this.consumeFromOffset(messageQueue);
                                    pullResult = consumer.pull(messageQueue, "*", offset, 1);
                                    switch (pullResult.getPullStatus()) {
                                        case FOUND:
                                            List<MessageExt> msgs = pullResult.getMsgFoundList();

                                            if (msgs != null && !msgs.isEmpty()) {
                                                this.doSomething(msgs);
                                                //update offset to broker
                                                consumer.updateConsumeOffset(messageQueue, pullResult.getNextBeginOffset());
                                                //print pull tps
                                                this.incPullTPS(topic, pullResult.getMsgFoundList().size());
                                            }
                                            break;
                                        case OFFSET_ILLEGAL:
                                            consumer.updateConsumeOffset(messageQueue, pullResult.getNextBeginOffset());
                                            break;
                                        case NO_NEW_MSG:
                                            Thread.sleep(1);
                                            consumer.updateConsumeOffset(messageQueue, pullResult.getNextBeginOffset());
                                            break;
                                        case NO_MATCHED_MSG:
                                            consumer.updateConsumeOffset(messageQueue, pullResult.getNextBeginOffset());
                                            break;
                                        default:
                                    }
                                } catch (RemotingException e) {
                                    e.printStackTrace();
                                } catch (MQBrokerException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (MQClientException e) {
                            //reblance error
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                public long consumeFromOffset(MessageQueue messageQueue) throws MQClientException {
                    //-1 when started
                    long offset = consumer.getOffsetStore().readOffset(messageQueue, ReadOffsetType.READ_FROM_MEMORY);
                    if (offset < 0) {
                        //query from broker
                        offset = consumer.getOffsetStore().readOffset(messageQueue, ReadOffsetType.READ_FROM_STORE);
                    }
                    if (offset < 0) {
                        //first time start from last offset
                        offset = consumer.maxOffset(messageQueue);
                    }
                    //make sure
                    if (offset < 0) {
                        offset = 0;
                    }
                    return offset;
                }

                public void incPullTPS(String topic, int pullSize) {
                    consumer.getDefaultMQPullConsumerImpl().getRebalanceImpl().getmQClientFactory()
                            .getConsumerStatsManager().incPullTPS(consumer.getConsumerGroup(), topic, pullSize);
                }
            });

        }
//        executors.shutdown();
//        consumer.shutdown();
    }
}
