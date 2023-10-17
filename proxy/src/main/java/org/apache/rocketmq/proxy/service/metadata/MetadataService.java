

package org.apache.rocketmq.proxy.service.metadata;

import org.apache.rocketmq.common.attribute.TopicMessageType;
import org.apache.rocketmq.common.subscription.SubscriptionGroupConfig;

public interface MetadataService {

    TopicMessageType getTopicMessageType(String topic);

    SubscriptionGroupConfig getSubscriptionGroupConfig(String group);
}
