

package org.apache.rocketmq.common.protocol.header;

import org.apache.rocketmq.common.rpc.TopicRequestHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class GetTopicConfigRequestHeader extends TopicRequestHeader {
    @Override
    public void checkFields() throws RemotingCommandException {
    }

    @CFNotNull
    private String topic;


    /**
     * @return the topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * @param topic the topic to set
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }
}