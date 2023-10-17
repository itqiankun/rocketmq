

package org.apache.rocketmq.common.protocol.body;

import org.apache.rocketmq.common.DataVersion;
import org.apache.rocketmq.common.statictopic.TopicQueueMappingDetail;
import org.apache.rocketmq.common.statictopic.TopicQueueMappingInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TopicConfigAndMappingSerializeWrapper extends TopicConfigSerializeWrapper {
    private Map<String/* topic */, TopicQueueMappingInfo> topicQueueMappingInfoMap = new ConcurrentHashMap<String, TopicQueueMappingInfo>();

    private Map<String/* topic */, TopicQueueMappingDetail> topicQueueMappingDetailMap = new ConcurrentHashMap<String, TopicQueueMappingDetail>();

    private DataVersion mappingDataVersion = new DataVersion();


    public Map<String, TopicQueueMappingInfo> getTopicQueueMappingInfoMap() {
        return topicQueueMappingInfoMap;
    }

    public void setTopicQueueMappingInfoMap(Map<String, TopicQueueMappingInfo> topicQueueMappingInfoMap) {
        this.topicQueueMappingInfoMap = topicQueueMappingInfoMap;
    }

    public Map<String, TopicQueueMappingDetail> getTopicQueueMappingDetailMap() {
        return topicQueueMappingDetailMap;
    }

    public void setTopicQueueMappingDetailMap(Map<String, TopicQueueMappingDetail> topicQueueMappingDetailMap) {
        this.topicQueueMappingDetailMap = topicQueueMappingDetailMap;
    }

    public DataVersion getMappingDataVersion() {
        return mappingDataVersion;
    }

    public void setMappingDataVersion(DataVersion mappingDataVersion) {
        this.mappingDataVersion = mappingDataVersion;
    }

    public static TopicConfigAndMappingSerializeWrapper from(TopicConfigSerializeWrapper wrapper) {
        if (wrapper instanceof  TopicConfigAndMappingSerializeWrapper) {
            return (TopicConfigAndMappingSerializeWrapper) wrapper;
        }
        TopicConfigAndMappingSerializeWrapper mappingSerializeWrapper =  new TopicConfigAndMappingSerializeWrapper();
        mappingSerializeWrapper.setDataVersion(wrapper.getDataVersion());
        mappingSerializeWrapper.setTopicConfigTable(wrapper.getTopicConfigTable());
        return mappingSerializeWrapper;
    }
}
