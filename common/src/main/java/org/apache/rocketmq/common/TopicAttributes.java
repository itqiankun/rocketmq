
package org.apache.rocketmq.common;

import java.util.HashMap;
import java.util.Map;
import org.apache.rocketmq.common.attribute.Attribute;
import org.apache.rocketmq.common.attribute.EnumAttribute;
import org.apache.rocketmq.common.attribute.TopicMessageType;

import static com.google.common.collect.Sets.newHashSet;

public class TopicAttributes {
    public static final EnumAttribute QUEUE_TYPE_ATTRIBUTE = new EnumAttribute(
            "queue.type",
            false,
            newHashSet("BatchCQ", "SimpleCQ"),
            "SimpleCQ"
    );
    public static final EnumAttribute TOPIC_MESSAGE_TYPE_ATTRIBUTE = new EnumAttribute(
        "message.type",
        true,
        TopicMessageType.topicMessageTypeSet(),
        TopicMessageType.NORMAL.getValue()
    );
    public static final Map<String, Attribute> ALL;

    static {
        ALL = new HashMap<>();
        ALL.put(QUEUE_TYPE_ATTRIBUTE.getName(), QUEUE_TYPE_ATTRIBUTE);
        ALL.put(TOPIC_MESSAGE_TYPE_ATTRIBUTE.getName(), TOPIC_MESSAGE_TYPE_ATTRIBUTE);
    }
}
