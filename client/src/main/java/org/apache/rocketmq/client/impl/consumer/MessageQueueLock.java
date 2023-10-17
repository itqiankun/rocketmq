
package org.apache.rocketmq.client.impl.consumer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.apache.rocketmq.common.message.MessageQueue;

/**
 * Message lock,strictly ensure the single queue only one thread at a time consuming
 */
public class MessageQueueLock {
    private ConcurrentMap<MessageQueue, ConcurrentMap<Integer, Object>> mqLockTable =
        new ConcurrentHashMap<MessageQueue, ConcurrentMap<Integer, Object>>(32);

    public Object fetchLockObject(final MessageQueue mq) {
        return fetchLockObject(mq, -1);
    }

    public Object fetchLockObject(final MessageQueue mq, final int shardingKeyIndex) {
        ConcurrentMap<Integer, Object> objMap = this.mqLockTable.get(mq);
        if (null == objMap) {
            objMap = new ConcurrentHashMap<Integer, Object>(32);
            ConcurrentMap<Integer, Object> prevObjMap = this.mqLockTable.putIfAbsent(mq, objMap);
            if (prevObjMap != null) {
                objMap = prevObjMap;
            }
        }

        Object lock = objMap.get(shardingKeyIndex);
        if (null == lock) {
            lock = new Object();
            Object prevLock = objMap.putIfAbsent(shardingKeyIndex, lock);
            if (prevLock != null) {
                lock = prevLock;
            }
        }

        return lock;
    }
}
