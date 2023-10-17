
package org.apache.rocketmq.proxy.common.utils;

import java.util.Set;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;

public class FilterUtils {
    /**
     * Whether the message's tag matches consumerGroup's SubscriptionData
     *
     * @param tagsSet, tagSet in {@link SubscriptionData}, tagSet empty means SubscriptionData.SUB_ALL(*)
     * @param tags,    message's tags, null means not tag attached to the message.
     */
    public static boolean isTagMatched(Set<String> tagsSet, String tags) {
        if (tagsSet.isEmpty()) {
            return true;
        }

        if (tags == null) {
            return false;
        }

        return tagsSet.contains(tags);
    }
}
