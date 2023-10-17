

package org.apache.rocketmq.common.attribute;

import com.google.common.collect.Sets;
import java.util.Set;

public enum TopicMessageType {
    UNSPECIFIED("UNSPECIFIED"),
    NORMAL("NORMAL"),
    FIFO("FIFO"),
    DELAY("DELAY"),
    TRANSACTION("TRANSACTION");

    private final String value;
    TopicMessageType(String value) {
        this.value = value;
    }

    public static Set<String> topicMessageTypeSet() {
        return Sets.newHashSet(UNSPECIFIED.value, NORMAL.value, FIFO.value, DELAY.value, TRANSACTION.value);
    }

    public String getValue() {
        return value;
    }
}
