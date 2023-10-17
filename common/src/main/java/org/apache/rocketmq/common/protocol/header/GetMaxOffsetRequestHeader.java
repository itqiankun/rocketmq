

/**
 * $Id: GetMaxOffsetRequestHeader.java 1835 2013-05-16 02:00:50Z vintagewang@apache.org $
 */
package org.apache.rocketmq.common.protocol.header;

import com.google.common.base.MoreObjects;
import org.apache.rocketmq.common.rpc.TopicQueueRequestHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.annotation.CFNullable;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class GetMaxOffsetRequestHeader extends TopicQueueRequestHeader {
    @CFNotNull
    private String topic;
    @CFNotNull
    private Integer queueId;

    /**
     * A message at committed offset has been dispatched from Topic to MessageQueue, so it can be consumed immediately,
     * while a message at inflight offset is not visible for a consumer temporarily.
     * Set this flag true if the max committed offset is needed, or false if the max inflight offset is preferred.
     * The default value is true.
     */
    @CFNullable
    private boolean committed = true;

    @Override
    public void checkFields() throws RemotingCommandException {
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public Integer getQueueId() {
        return queueId;
    }

    @Override
    public void setQueueId(Integer queueId) {
        this.queueId = queueId;
    }

    public boolean isCommitted() {
        return committed;
    }

    public void setCommitted(final boolean committed) {
        this.committed = committed;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("topic", topic)
            .add("queueId", queueId)
            .add("committed", committed)
            .toString();
    }
}
