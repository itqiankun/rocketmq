
package org.apache.rocketmq.common.rpc;

public abstract class TopicQueueRequestHeader extends TopicRequestHeader {

    public abstract Integer getQueueId();
    public abstract void setQueueId(Integer queueId);

}
