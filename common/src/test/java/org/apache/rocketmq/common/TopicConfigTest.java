

package org.apache.rocketmq.common;

import org.apache.rocketmq.common.attribute.TopicMessageType;
import org.apache.rocketmq.common.constant.PermName;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TopicConfigTest {
    String topicName = "topic";
    int queueNums = 8;
    int perm = PermName.PERM_READ | PermName.PERM_WRITE;
    TopicFilterType topicFilterType = TopicFilterType.SINGLE_TAG;

    @Test
    public void testEncode() {
        TopicConfig topicConfig = new TopicConfig();
        topicConfig.setTopicName(topicName);
        topicConfig.setReadQueueNums(queueNums);
        topicConfig.setWriteQueueNums(queueNums);
        topicConfig.setPerm(perm);
        topicConfig.setTopicFilterType(topicFilterType);
        topicConfig.setTopicMessageType(TopicMessageType.FIFO);

        String encode = topicConfig.encode();
        assertThat(encode).isEqualTo("topic 8 8 6 SINGLE_TAG {\"message.type\":\"FIFO\"}");
    }

    @Test
    public void testDecode() {
        String encode = "topic 8 8 6 SINGLE_TAG {\"message.type\":\"FIFO\"}";
        TopicConfig decodeTopicConfig = new TopicConfig();
        decodeTopicConfig.decode(encode);

        TopicConfig topicConfig = new TopicConfig();
        topicConfig.setTopicName(topicName);
        topicConfig.setReadQueueNums(queueNums);
        topicConfig.setWriteQueueNums(queueNums);
        topicConfig.setPerm(perm);
        topicConfig.setTopicFilterType(topicFilterType);
        topicConfig.setTopicMessageType(TopicMessageType.FIFO);

        assertThat(decodeTopicConfig).isEqualTo(topicConfig);
    }

    @Test
    public void testDecodeWhenCompatible() {
        String encode = "topic 8 8 6 SINGLE_TAG";
        TopicConfig decodeTopicConfig = new TopicConfig();
        decodeTopicConfig.decode(encode);

        TopicConfig topicConfig = new TopicConfig();
        topicConfig.setTopicName(topicName);
        topicConfig.setReadQueueNums(queueNums);
        topicConfig.setWriteQueueNums(queueNums);
        topicConfig.setPerm(perm);
        topicConfig.setTopicFilterType(topicFilterType);

        assertThat(decodeTopicConfig).isEqualTo(topicConfig);
    }
}