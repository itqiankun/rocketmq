
package org.apache.rocketmq.common.rpc;

public abstract class TopicRequestHeader extends RpcRequestHeader {
    //logical
    protected Boolean lo;

    public abstract String getTopic();
    public abstract void setTopic(String topic);

    public Boolean getLo() {
        return lo;
    }
    public void setLo(Boolean lo) {
        this.lo = lo;
    }
}
