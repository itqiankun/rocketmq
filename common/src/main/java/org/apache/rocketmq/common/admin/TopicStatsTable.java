
package org.apache.rocketmq.common.admin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class TopicStatsTable extends RemotingSerializable {
    private Map<MessageQueue, TopicOffset> offsetTable = new ConcurrentHashMap<MessageQueue, TopicOffset>();

    public Map<MessageQueue, TopicOffset> getOffsetTable() {
        return offsetTable;
    }

    public void setOffsetTable(Map<MessageQueue, TopicOffset> offsetTable) {
        this.offsetTable = offsetTable;
    }
}
