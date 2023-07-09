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
package org.apache.rocketmq.example.quickstart;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class demonstrates how to send messages to brokers using provided {@link DefaultMQProducer}.
 */
public class Producer {
    private static final Logger log = LoggerFactory.getLogger(Producer.class);

    public static void main(String[] args) throws MQClientException, InterruptedException {

        /*
         * Instantiate with a producer group name.
         */
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        final CountDownLatch countDownLatch = new CountDownLatch(10000); // 相当于计数器，当所有都准备好了，再一起执行，模仿多并发，保证并发量
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1000, 1000, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        try {
            for (int i = 0; i <= 10000; i++) {
                threadPoolExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            countDownLatch.await();
                            sendMessage(producer);
                            log.info("thread:{} end.", Thread.currentThread().getName());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });
                countDownLatch.countDown();
            }
            threadPoolExecutor.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
        producer.shutdown();
    }

    private static void sendMessage(DefaultMQProducer producer) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            try {
                Message msg = new Message("TopicTest" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                SendResult sendResult = producer.send(msg);
//                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
