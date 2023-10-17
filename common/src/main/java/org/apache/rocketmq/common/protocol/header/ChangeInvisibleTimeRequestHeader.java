
package org.apache.rocketmq.common.protocol.header;

import com.google.common.base.MoreObjects;
import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class ChangeInvisibleTimeRequestHeader implements CommandCustomHeader {
    @CFNotNull
    private String consumerGroup;
    @CFNotNull
    private String topic;
    @CFNotNull
    private Integer queueId;
    /**
     * startOffset popTime invisibleTime queueId
     */
    @CFNotNull
    private String extraInfo;

    @CFNotNull
    private Long offset;

    @CFNotNull
    private Long invisibleTime;

    @Override
    public void checkFields() throws RemotingCommandException {
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getOffset() {
        return offset;
    }

    public Long getInvisibleTime() {
        return invisibleTime;
    }

    public void setInvisibleTime(Long invisibleTime) {
        this.invisibleTime = invisibleTime;
    }

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    /**
     * startOffset popTime invisibleTime queueId
     */
    public String getExtraInfo() {
        return extraInfo;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getQueueId() {
        return queueId;
    }

    public void setQueueId(Integer queueId) {
        this.queueId = queueId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("consumerGroup", consumerGroup)
            .add("topic", topic)
            .add("queueId", queueId)
            .add("extraInfo", extraInfo)
            .add("offset", offset)
            .add("invisibleTime", invisibleTime)
            .toString();
    }
}
