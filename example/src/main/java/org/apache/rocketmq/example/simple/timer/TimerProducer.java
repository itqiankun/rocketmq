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
package org.apache.rocketmq.example.simple.timer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalField;

@Slf4j
public class TimerProducer {


    public static void main(String[] args) throws MQClientException, InterruptedException {

        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
            try {
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.of(10, 25, 30);
                LocalDateTime dateTime = LocalDateTime.of(localDate, localTime);
                long milliseconds = dateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();

                Message msg = new Message("TopicTest",
                    "TagA",
                    "OrderID188",
                    "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));
                msg.putUserProperty("deliverTimeStamp", String.valueOf(milliseconds));
                SendResult sendResult = producer.send(msg);
                log.info("打印结果:{}", sendResult.getOffsetMsgId());


            } catch (Exception e) {
                e.printStackTrace();
            }
        producer.shutdown();
    }



}
