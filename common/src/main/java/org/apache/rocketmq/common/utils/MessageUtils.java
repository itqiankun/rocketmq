
package org.apache.rocketmq.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.common.hash.Hashing;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.message.MessageExt;

public class MessageUtils {

    public static int getShardingKeyIndex(String shardingKey, int indexSize) {
        return Math.abs(Hashing.murmur3_32().hashBytes(shardingKey.getBytes(StandardCharsets.UTF_8)).asInt() % indexSize);
    }

    public static int getShardingKeyIndexByMsg(MessageExt msg, int indexSize) {
        String shardingKey = msg.getProperty(MessageConst.PROPERTY_SHARDING_KEY);
        if (shardingKey == null) {
            shardingKey = "";
        }

        return getShardingKeyIndex(shardingKey, indexSize);
    }

    public static Set<Integer> getShardingKeyIndexes(Collection<MessageExt> msgs, int indexSize) {
        Set<Integer> indexSet = new HashSet<Integer>(indexSize);
        for (MessageExt msg : msgs) {
            indexSet.add(getShardingKeyIndexByMsg(msg, indexSize));
        }
        return indexSet;
    }
}
