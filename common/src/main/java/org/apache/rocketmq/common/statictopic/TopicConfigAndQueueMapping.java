
package org.apache.rocketmq.common.statictopic;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.rocketmq.common.TopicConfig;

public class TopicConfigAndQueueMapping extends TopicConfig {
    private TopicQueueMappingDetail mappingDetail;

    public TopicConfigAndQueueMapping() {
    }

    public TopicConfigAndQueueMapping(TopicConfig topicConfig, TopicQueueMappingDetail mappingDetail) {
        super(topicConfig);
        this.mappingDetail = mappingDetail;
    }

    public TopicQueueMappingDetail getMappingDetail() {
        return mappingDetail;
    }

    public void setMappingDetail(TopicQueueMappingDetail mappingDetail) {
        this.mappingDetail = mappingDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof TopicConfigAndQueueMapping)) return false;

        TopicConfigAndQueueMapping that = (TopicConfigAndQueueMapping) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(mappingDetail, that.mappingDetail)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(mappingDetail)
                .toHashCode();
    }
}
