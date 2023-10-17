

package org.apache.rocketmq.store;

import java.util.Map;

public interface MessageArrivingListener {

    /**
     * Notify that a new message arrives in a consume queue
     * @param topic topic name
     * @param queueId consume queue id
     * @param logicOffset consume queue offset
     * @param tagsCode message tags hash code
     * @param msgStoreTime message store time
     * @param filterBitMap message bloom filter
     * @param properties message properties
     */
    void arriving(String topic, int queueId, long logicOffset, long tagsCode,
        long msgStoreTime, byte[] filterBitMap, Map<String, String> properties);
}
