

package org.apache.rocketmq.common.protocol.body;

import org.apache.rocketmq.common.DataVersion;
import org.apache.rocketmq.common.statictopic.TopicQueueMappingDetail;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

import java.util.Map;

public class TopicQueueMappingSerializeWrapper extends RemotingSerializable {
    private Map<String/* topic */, TopicQueueMappingDetail> topicQueueMappingInfoMap;
    private DataVersion dataVersion = new DataVersion();

    public Map<String, TopicQueueMappingDetail> getTopicQueueMappingInfoMap() {
        return topicQueueMappingInfoMap;
    }

    public void setTopicQueueMappingInfoMap(Map<String, TopicQueueMappingDetail> topicQueueMappingInfoMap) {
        this.topicQueueMappingInfoMap = topicQueueMappingInfoMap;
    }

    public DataVersion getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(DataVersion dataVersion) {
        this.dataVersion = dataVersion;
    }
}
