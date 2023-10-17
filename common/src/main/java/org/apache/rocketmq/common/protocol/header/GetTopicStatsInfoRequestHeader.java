

package org.apache.rocketmq.common.protocol.header;

import org.apache.rocketmq.common.rpc.TopicRequestHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class GetTopicStatsInfoRequestHeader extends TopicRequestHeader {
    @CFNotNull
    private String topic;

    @Override
    public void checkFields() throws RemotingCommandException {
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
