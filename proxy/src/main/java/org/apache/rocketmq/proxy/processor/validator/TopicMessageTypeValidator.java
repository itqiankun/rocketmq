

package org.apache.rocketmq.proxy.processor.validator;

import org.apache.rocketmq.common.attribute.TopicMessageType;

public interface TopicMessageTypeValidator {
    /**
     * Will throw {@link org.apache.rocketmq.proxy.common.ProxyException} if validate failed.
     *
     * @param topicMessageType Target topic
     * @param messageType      Message's type
     */
    void validate(TopicMessageType topicMessageType, TopicMessageType messageType);
}
