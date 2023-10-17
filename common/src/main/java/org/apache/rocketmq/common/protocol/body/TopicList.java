
package org.apache.rocketmq.common.protocol.body;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class TopicList extends RemotingSerializable {
    private Set<String> topicList = new CopyOnWriteArraySet<>();
    private String brokerAddr;

    public Set<String> getTopicList() {
        return topicList;
    }

    public void setTopicList(Set<String> topicList) {
        this.topicList = topicList;
    }

    public String getBrokerAddr() {
        return brokerAddr;
    }

    public void setBrokerAddr(String brokerAddr) {
        this.brokerAddr = brokerAddr;
    }
}
