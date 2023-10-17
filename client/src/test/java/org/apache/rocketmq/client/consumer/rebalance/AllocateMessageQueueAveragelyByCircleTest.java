
package org.apache.rocketmq.client.consumer.rebalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.Assert;

public class AllocateMessageQueueAveragelyByCircleTest extends TestCase {

    public void testAllocateMessageQueueAveragelyByCircle() {
        List<String> consumerIdList = createConsumerIdList(4);
        List<MessageQueue> messageQueueList = createMessageQueueList(10);
        // the consumerId not in cidAll
        List<MessageQueue> allocateQueues = new AllocateMessageQueueAveragelyByCircle().allocate("", "CID_PREFIX", messageQueueList, consumerIdList);
        Assert.assertEquals(0, allocateQueues.size());

        Map<String, int[]> consumerAllocateQueue = new HashMap<String, int[]>(consumerIdList.size());
        for (String consumerId : consumerIdList) {
            List<MessageQueue> queues = new AllocateMessageQueueAveragelyByCircle().allocate("", consumerId, messageQueueList, consumerIdList);
            int[] queueIds = new int[queues.size()];
            for (int i = 0; i < queues.size(); i++) {
                queueIds[i] = queues.get(i).getQueueId();
            }
            consumerAllocateQueue.put(consumerId, queueIds);
        }
        Assert.assertArrayEquals(new int[] {0, 4, 8}, consumerAllocateQueue.get("CID_PREFIX0"));
        Assert.assertArrayEquals(new int[] {1, 5, 9}, consumerAllocateQueue.get("CID_PREFIX1"));
        Assert.assertArrayEquals(new int[] {2, 6}, consumerAllocateQueue.get("CID_PREFIX2"));
        Assert.assertArrayEquals(new int[] {3, 7}, consumerAllocateQueue.get("CID_PREFIX3"));
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

