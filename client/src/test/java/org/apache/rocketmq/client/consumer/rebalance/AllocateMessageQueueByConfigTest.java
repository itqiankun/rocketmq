
package org.apache.rocketmq.client.consumer.rebalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.Assert;

public class AllocateMessageQueueByConfigTest extends TestCase {

    public void testAllocateMessageQueueByConfig() {
        List<String> consumerIdList = createConsumerIdList(2);
        List<MessageQueue> messageQueueList = createMessageQueueList(4);
        AllocateMessageQueueByConfig allocateStrategy = new AllocateMessageQueueByConfig();
        allocateStrategy.setMessageQueueList(messageQueueList);

        Map<String, int[]> consumerAllocateQueue = new HashMap<String, int[]>(consumerIdList.size());
        for (String consumerId : consumerIdList) {
            List<MessageQueue> queues = allocateStrategy.allocate("", consumerId, messageQueueList, consumerIdList);
            int[] queueIds = new int[queues.size()];
            for (int i = 0; i < queues.size(); i++) {
                queueIds[i] = queues.get(i).getQueueId();
            }
            consumerAllocateQueue.put(consumerId, queueIds);
        }
        Assert.assertArrayEquals(new int[] {0, 1, 2, 3}, consumerAllocateQueue.get("CID_PREFIX0"));
        Assert.assertArrayEquals(new int[] {0, 1, 2, 3}, consumerAllocateQueue.get("CID_PREFIX1"));
    }

    private List<String> createConsumerIdList(int size) {
        List<String> consumerIdList = new ArrayList<String>(size);
        for (int i = 0; i < size; i++) {
            consumerIdList.add("CID_PREFIX" + i);
        }
        return consumerIdList;
    }

    private List<MessageQueue> createMessageQueueList(int size) {
        List<MessageQueue> messageQueueList = new ArrayList<MessageQueue>(size);
        for (int i = 0; i < size; i++) {
            MessageQueue mq = new MessageQueue("topic", "brokerName", i);
            messageQueueList.add(mq);
        }
        return messageQueueList;
    }
}

