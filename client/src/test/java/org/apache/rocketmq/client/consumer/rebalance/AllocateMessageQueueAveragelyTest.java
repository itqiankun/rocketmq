
package org.apache.rocketmq.client.consumer.rebalance;

import junit.framework.TestCase;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class AllocateMessageQueueAveragelyTest extends TestCase {

    public void testAllocateMessageQueueAveragely() {
        List<String> consumerIdList = createConsumerIdList(4);
        List<MessageQueue> messageQueueList = createMessageQueueList(10);
        int[] results = new int[consumerIdList.size()];
        for (int i = 0; i < consumerIdList.size(); i++) {
            List<MessageQueue> result = new AllocateMessageQueueAveragely().allocate("", consumerIdList.get(i), messageQueueList, consumerIdList);
            results[i] = result.size();
        }
        Assert.assertArrayEquals(new int[]{3, 3, 2, 2}, results);
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
