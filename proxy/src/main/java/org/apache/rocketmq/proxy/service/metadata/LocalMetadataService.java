

package org.apache.rocketmq.proxy.service.metadata;

import org.apache.rocketmq.broker.BrokerController;
import org.apache.rocketmq.common.TopicConfig;
import org.apache.rocketmq.common.attribute.TopicMessageType;
import org.apache.rocketmq.common.subscription.SubscriptionGroupConfig;

public class LocalMetadataService implements MetadataService {
    private final BrokerController brokerController;

    public LocalMetadataService(BrokerController brokerController) {
        this.brokerController = brokerController;
    }

    @Override
    public TopicMessageType getTopicMessageType(String topic) {
        TopicConfig topicConfig = brokerController.getTopicConfigManager().selectTopicConfig(topic);
        if (topicConfig == null) {
            return TopicMessageType.UNSPECIFIED;
        }
        return topicConfig.getTopicMessageType();
    }

    @Override
    public SubscriptionGroupConfig getSubscriptionGroupConfig(String group) {
        return this.brokerController.getSubscriptionGroupManager().getSubscriptionGroupTable().get(group);
    }
}
