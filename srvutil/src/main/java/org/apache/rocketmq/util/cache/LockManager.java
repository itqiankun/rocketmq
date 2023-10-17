
package org.apache.rocketmq.util.cache;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.rocketmq.common.PopAckConstants;
import org.apache.rocketmq.common.protocol.header.PopMessageRequestHeader;

public class LockManager {
    private static ExpiredLocalCache<String, AtomicBoolean> expiredLocalCache = new ExpiredLocalCache<String, AtomicBoolean>(100000);

    public static boolean tryLock(String key, long lockTime) {
        AtomicBoolean v = expiredLocalCache.get(key);
        if (v == null) {
            return expiredLocalCache.putIfAbsent(key, new AtomicBoolean(false), lockTime) == null;
        } else {
            return v.compareAndSet(true, false);
        }
    }

    public static void unLock(String key) {
        AtomicBoolean v = expiredLocalCache.get(key);
        if (v != null) {
            v.set(true);
        }
    }

    public static String buildKey(PopMessageRequestHeader requestHeader, int queueId) {
        return requestHeader.getConsumerGroup() + PopAckConstants.SPLIT + requestHeader.getTopic() + PopAckConstants.SPLIT + queueId;
    }

    public static String buildKey(String topic, String cid, int queueId) {
        return topic + PopAckConstants.SPLIT + cid + PopAckConstants.SPLIT + queueId;
    }

    public static String buildKey(String prefix, int queueId) {
        return prefix + PopAckConstants.SPLIT + queueId;
    }
}
