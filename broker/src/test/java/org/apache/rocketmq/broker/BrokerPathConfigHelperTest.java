

package org.apache.rocketmq.broker;

import java.io.File;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BrokerPathConfigHelperTest {

    @Test
    public void testGetLmqConsumerOffsetPath() {
        String lmqConsumerOffsetPath = BrokerPathConfigHelper.getLmqConsumerOffsetPath("/home/admin/store".replace("/", File.separator));
        assertEquals("/home/admin/store/config/lmqConsumerOffset.json".replace("/", File.separator), lmqConsumerOffsetPath);

        String consumerOffsetPath = BrokerPathConfigHelper.getConsumerOffsetPath("/home/admin/store".replace("/", File.separator));
        assertEquals("/home/admin/store/config/consumerOffset.json".replace("/", File.separator), consumerOffsetPath);

        String topicConfigPath = BrokerPathConfigHelper.getTopicConfigPath("/home/admin/store".replace("/", File.separator));
        assertEquals("/home/admin/store/config/topics.json".replace("/", File.separator), topicConfigPath);

        String subscriptionGroupPath = BrokerPathConfigHelper.getSubscriptionGroupPath("/home/admin/store".replace("/", File.separator));
        assertEquals("/home/admin/store/config/subscriptionGroup.json".replace("/", File.separator), subscriptionGroupPath);

    }
}