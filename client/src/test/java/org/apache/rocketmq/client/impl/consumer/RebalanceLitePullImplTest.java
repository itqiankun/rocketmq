
package org.apache.rocketmq.client.impl.consumer;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.store.OffsetStore;
import org.apache.rocketmq.client.consumer.store.ReadOffsetType;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.impl.MQAdminImpl;
import org.apache.rocketmq.client.impl.factory.MQClientInstance;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RebalanceLitePullImplTest {
    private MessageQueue mq = new MessageQueue("topic1", "broker1", 0);
    private MessageQueue retryMq = new MessageQueue(MixAll.RETRY_GROUP_TOPIC_PREFIX + "group", "broker1", 0);
    private DefaultLitePullConsumerImpl consumerImpl = mock(DefaultLitePullConsumerImpl.class);
    private RebalanceLitePullImpl rebalanceImpl = new RebalanceLitePullImpl(consumerImpl);
    private OffsetStore offsetStore = mock(OffsetStore.class);
    private DefaultLitePullConsumer consumer = new DefaultLitePullConsumer();
    private MQClientInstance client = mock(MQClientInstance.class);
    private MQAdminImpl admin = mock(MQAdminImpl.class);

    public RebalanceLitePullImplTest() {
        when(consumerImpl.getDefaultLitePullConsumer()).thenReturn(consumer);
        when(consumerImpl.getOffsetStore()).thenReturn(offsetStore);
        rebalanceImpl.setmQClientFactory(client);
        when(client.getMQAdminImpl()).thenReturn(admin);
    }

    @Test
    public void testComputePullFromWhereWithException_ne_minus1() throws MQClientException {
        for (ConsumeFromWhere where : new ConsumeFromWhere[]{
                ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET,
                ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET,
                ConsumeFromWhere.CONSUME_FROM_TIMESTAMP}) {
            consumer.setConsumeFromWhere(where);

            when(offsetStore.readOffset(any(MessageQueue.class), any(ReadOffsetType.class))).thenReturn(0L);
            assertEquals(0, rebalanceImpl.computePullFromWhereWithException(mq));

            when(offsetStore.readOffset(any(MessageQueue.class), any(ReadOffsetType.class))).thenReturn(-2L);
            assertEquals(-1, rebalanceImpl.computePullFromWhereWithException(mq));
        }
    }

    @Test
    public void testComputePullFromWhereWithException_eq_minus1_last() throws MQClientException {
        when(offsetStore.readOffset(any(MessageQueue.class), any(ReadOffsetType.class))).thenReturn(-1L);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        when(admin.maxOffset(any(MessageQueue.class))).thenReturn(12345L);

        assertEquals(12345L, rebalanceImpl.computePullFromWhereWithException(mq));

        assertEquals(0L, rebalanceImpl.computePullFromWhereWithException(retryMq));
    }

    @Test
    public void testComputePullFromWhereWithException_eq_minus1_first() throws MQClientException {
        when(offsetStore.readOffset(any(MessageQueue.class), any(ReadOffsetType.class))).thenReturn(-1L);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        assertEquals(0, rebalanceImpl.computePullFromWhereWithException(mq));
    }

    @Test
    public void testComputePullFromWhereWithException_eq_minus1_timestamp() throws MQClientException {
        when(offsetStore.readOffset(any(MessageQueue.class), any(ReadOffsetType.class))).thenReturn(-1L);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
        when(admin.searchOffset(any(MessageQueue.class), anyLong())).thenReturn(12345L);
        when(admin.maxOffset(any(MessageQueue.class))).thenReturn(23456L);

        assertEquals(12345L, rebalanceImpl.computePullFromWhereWithException(mq));

        assertEquals(23456L, rebalanceImpl.computePullFromWhereWithException(retryMq));
    }


}
