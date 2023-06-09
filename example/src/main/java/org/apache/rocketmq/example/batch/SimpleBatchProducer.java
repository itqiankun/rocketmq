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
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.rocketmq.example.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

public class SimpleBatchProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("BatchProducerGroupName");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        //If you just send messages of no more than 1MiB at a time, it is easy to use batch
        //Messages of the same batch should have: same topic, same waitStoreMsgOK and no schedule support
        String topic = "BatchTest1";
        String str="{\"code\":200,\"data\":{\"similarList\":[],\"success\":[{\"image\":\"https://fos.fjecloud.com:8888/webdav//data/img/20230708/orgId22af2f501a044e5db001d1a5de2f2ede/videoId1/1677708917364228096.jpg\",\"videoValidation\":\"3481\",\"bbox_list\":[],\"kwargs\":{\"displayAllBboxes\":true,\"coldActivation\":{\"includeClasses\":\"[]\",\"type\":\"\"},\"bigModelQueryPositive\":[],\"displayScore\":true,\"classesNeeded\":\"[]\",\"boxEnlargeSize\":\"1\",\"yBMZhaRqXOXBJmjJ\":0.5,\"bigModelIncludeCoincideRatio\":[],\"isStart\":false,\"polygon_regions\":[],\"man_count\":{\"person_num\":0.0},\"includeCoincideRatio\":\"[]\",\"polygon_regions_label\":[],\"num_background\":0.0,\"LC_CP_num_person_thresh\":1.0,\"encodingFilter\":{\"indexId\":\"od210_210_001835_008_rfjs5fhx\",\"threshold\":0.9,\"videoId\":27897.0,\"orgId\":\"mu_100195\"},\"startTime\":\"00:00:00\",\"interval\":5.0,\"endTime\":\"23:59:59\",\"bigModelQueryNegative\":[],\"excludeCoincideRatio\":\"[]\",\"bigModelExcludeCoincideRatio\":[],\"classesThreshold\":0.5,\"num_alerts\":0.0},\"description\":\"注意！当前画面出现告警，请关注！\",\"id\":\"101630712\",\"status\":true}],\"error\":[]},\"appSourceId\":\"1677709120012025856\",\"taskId\":\"1258\"}";
        List<Message> messages = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            messages.add(new Message(topic, "Tag", "OrderID00"+i, str.getBytes()));
        }
        producer.send(messages);
        producer.shutdown();

    }
}
