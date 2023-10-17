

package org.apache.rocketmq.proxy.processor.validator;

import org.apache.rocketmq.common.attribute.TopicMessageType;
import org.apache.rocketmq.proxy.common.ProxyException;
import org.apache.rocketmq.proxy.common.ProxyExceptionCode;

public class DefaultTopicMessageTypeValidator implements TopicMessageTypeValidator {

    public void validate(TopicMessageType topicMessageType, TopicMessageType messageType) {
        if (messageType.equals(TopicMessageType.UNSPECIFIED) || !messageType.equals(topicMessageType)) {
            String errorInfo = String.format("TopicMessageType validate failed, topic type is %s, message type is %s", topicMessageType, messageType);
            throw new ProxyException(ProxyExceptionCode.MESSAGE_PROPERTY_CONFLICT_WITH_TYPE, errorInfo);
        }
    }
}
