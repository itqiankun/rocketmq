
package org.apache.rocketmq.client.producer.selector;

import org.apache.rocketmq.client.impl.producer.TopicPublishInfo;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SelectMessageQueueRetryTest {

    private String topic = "TEST";

    @Test
    public void testSelect() throws Exception {

        TopicPublishInfo topicPublishInfo = new TopicPublishInfo();
        List<MessageQueue> messageQueueList = new ArrayList();
        for (int i = 0; i < 3; i++) {
            MessageQueue mq = new MessageQueue();
            mq.setBrokerName("broker-" + i);
            mq.setQueueId(0);
            mq.setTopic(topic);
            messageQueueList.add(mq);
        }

        topicPublishInfo.setMessageQueueList(messageQueueList);

        Set<String> retryBrokerNameSet = retryBroker(topicPublishInfo);
        //always in Set （broker-0，broker-1，broker-2）
        assertThat(retryBroker(topicPublishInfo)).isEqualTo(retryBrokerNameSet);
    }

    private Set<String> retryBroker(TopicPublishInfo topicPublishInfo) {
        MessageQueue mqTmp = null;
        Set<String> retryBrokerNameSet = new HashSet();
        for (int times = 0; times < 3; times++) {
            String lastBrokerName = null == mqTmp ? null : mqTmp.getBrokerName();
            mqTmp = topicPublishInfo.selectOneMessageQueue(lastBrokerName);
            retryBrokerNameSet.add(mqTmp.getBrokerName());
        }
        return retryBrokerNameSet;
    }

}