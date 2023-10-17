
package org.apache.rocketmq.proxy.service.route;

import com.google.common.base.MoreObjects;
import org.apache.rocketmq.common.protocol.route.TopicRouteData;

public class MessageQueueView {
    public static final MessageQueueView WRAPPED_EMPTY_QUEUE = new MessageQueueView("", new TopicRouteData());

    private final MessageQueueSelector readSelector;
    private final MessageQueueSelector writeSelector;
    private final TopicRouteWrapper topicRouteWrapper;

    public MessageQueueView(String topic, TopicRouteData topicRouteData) {
        this.topicRouteWrapper = new TopicRouteWrapper(topicRouteData, topic);

        this.readSelector = new MessageQueueSelector(topicRouteWrapper, true);
        this.writeSelector = new MessageQueueSelector(topicRouteWrapper, false);
    }

    public TopicRouteData getTopicRouteData() {
        return topicRouteWrapper.getTopicRouteData();
    }

    public String getTopicName() {
        return topicRouteWrapper.getTopicName();
    }

    public boolean isEmptyCachedQueue() {
        return this == WRAPPED_EMPTY_QUEUE;
    }

    public MessageQueueSelector getReadSelector() {
        return readSelector;
    }

    public MessageQueueSelector getWriteSelector() {
        return writeSelector;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("readSelector", readSelector)
            .add("writeSelector", writeSelector)
            .add("topicRouteWrapper", topicRouteWrapper)
            .toString();
    }
}