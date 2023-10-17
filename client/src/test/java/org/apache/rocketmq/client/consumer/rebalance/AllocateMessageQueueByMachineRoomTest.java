
package org.apache.rocketmq.client.consumer.rebalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import junit.framework.TestCase;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.Assert;

public class AllocateMessageQueueByMachineRoomTest extends TestCase {

    public void testAllocateMessageQueueByMachineRoom() {
        List<String> consumerIdList = createConsumerIdList(2);
        List<MessageQueue> messageQueueList = createMessageQueueList(10);
        Set<String> consumeridcs = new HashSet<String>();
        consumeridcs.add("room1");
        AllocateMessageQueueByMachineRoom allocateStrategy = new AllocateMessageQueueByMachineRoom();
        allocateStrategy.setConsumeridcs(consumeridcs);

        // mqAll is null or mqAll empty
        try {
            allocateStrategy.allocate("", consumerIdList.get(0), new ArrayList<MessageQueue>(), consumerIdList);
        } catch (Exception e) {
            assert e instanceof IllegalArgumentException;
            Assert.assertEquals("mqAll is null or mqAll empty", e.getMessage());
        }

        Map<String, int[]> consumerAllocateQueue = new HashMap<String, int[]>(consumerIdList.size());
        for (String consumerId : consumerIdList) {
            List<MessageQueue> queues = allocateStrategy.allocate("", consumerId, messageQueueList, consumerIdList);
            int[] queueIds = new int[queues.size()];
            for (int i = 0; i < queues.size(); i++) {
                queueIds[i] = queues.get(i).getQueueId();
            }
            consumerAllocateQueue.put(consumerId, queueIds);
        }
        Assert.assertArrayEquals(new int[] {0, 1, 4}, consumerAllocateQueue.get("CID_PREFIX0"));
        Assert.assertArrayEquals(new int[] {2, 3}, consumerAllocateQueue.get("CID_PREFIX1"));
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
            MessageQueue mq;
            if (i < size / 2) {
                mq = new MessageQueue("topic", "room1@broker-a", i);
            } else {
                mq = new MessageQueue("topic", "room2@broker-b", i);
            }
            messageQueueList.add(mq);
        }
        return messageQueueList;
    }
}

