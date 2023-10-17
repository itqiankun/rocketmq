

/**
 * $Id: GetEarliestMsgStoretimeRequestHeader.java 1835 2013-05-16 02:00:50Z vintagewang@apache.org $
 */
package org.apache.rocketmq.common.protocol.header;

import org.apache.rocketmq.common.rpc.TopicQueueRequestHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class GetEarliestMsgStoretimeRequestHeader extends TopicQueueRequestHeader {
    @CFNotNull
    private String topic;
    @CFNotNull
    private Integer queueId;

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
}
