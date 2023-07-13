/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rocketmq.example.simple;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public class OffsetIllegalConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("please_rename_unique_group_name_5");
        consumer.setNamesrvAddr("192.1.3.118:32219");
        Set<String> topics = new HashSet<>();
        topics.add("om-huizhi-topic");
        consumer.setRegisterTopics(topics);
        consumer.start();

        ExecutorService executors = Executors.newFixedThreadPool(topics.size(), r -> new Thread(r, "PullConsumerThread"));
        for (String topic : consumer.getRegisterTopics()) {

            executors.execute(new Runnable() {
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
                                    pullResult = consumer.pull(messageQueue, "*", 1, 32);
                                    switch (pullResult.getPullStatus()) {
                                        case FOUND:
                                            List<MessageExt> msgs = pullResult.getMsgFoundList();

                                            if (msgs != null && !msgs.isEmpty()) {
                                                log.info("获取消费消息:{}", JSONObject.toJSON(msgs));
                                                //update offset to broker
                                                consumer.updateConsumeOffset(messageQueue, pullResult.getNextBeginOffset());
                                            }
                                            break;
                                        case OFFSET_ILLEGAL:
                                            log.info("获取消息违法");
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
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }
//        executors.shutdown();
//        consumer.shutdown();
    }
}
