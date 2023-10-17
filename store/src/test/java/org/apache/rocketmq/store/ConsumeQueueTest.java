

package org.apache.rocketmq.store;

import java.io.File;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import org.apache.rocketmq.common.BrokerConfig;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.message.MessageDecoder;
import org.apache.rocketmq.common.message.MessageExtBrokerInner;
import org.apache.rocketmq.store.config.MessageStoreConfig;
import org.apache.rocketmq.store.queue.ConsumeQueueInterface;
import org.apache.rocketmq.store.queue.CqUnit;
import org.apache.rocketmq.store.queue.ReferredIterator;
import org.apache.rocketmq.store.stats.BrokerStatsManager;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class ConsumeQueueTest {

    private static final String msg = "Once, there was a chance for me!";
    private static final byte[] msgBody = msg.getBytes();

    private static final String topic = "abc";
    private static final int queueId = 0;
    private static final String storePath = System.getProperty("java.io.tmpdir") + File.separator + "unit_test_store";
    private static final int commitLogFileSize = 1024 * 8;
    private static final int cqFileSize = 10 * 20;
    private static final int cqExtFileSize = 10 * (ConsumeQueueExt.CqExtUnit.MIN_EXT_UNIT_SIZE + 64);

    private static SocketAddress BornHost;

    private static SocketAddress StoreHost;

    static {
        try {
            StoreHost = new InetSocketAddress(InetAddress.getLocalHost(), 8123);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            BornHost = new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 0);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public MessageExtBrokerInner buildMessage() {
        MessageExtBrokerInner msg = new MessageExtBrokerInner();
        msg.setTopic(topic);
        msg.setTags("TAG1");
        msg.setKeys("Hello");
        msg.setBody(msgBody);
        msg.setKeys(String.valueOf(System.currentTimeMillis()));
        msg.setQueueId(queueId);
        msg.setSysFlag(0);
        msg.setBornTimestamp(System.currentTimeMillis());
        msg.setStoreHost(StoreHost);
        msg.setBornHost(BornHost);
        for (int i = 0; i < 1; i++) {
            msg.putUserProperty(String.valueOf(i), "imagoodperson" + i);
        }
        msg.setPropertiesString(MessageDecoder.messageProperties2String(msg.getProperties()));

        return msg;
    }

    public MessageExtBrokerInner buildIPv6HostMessage() {
        MessageExtBrokerInner msg = new MessageExtBrokerInner();
        msg.setTopic(topic);
        msg.setTags("TAG1");
        msg.setKeys("Hello");
        msg.setBody(msgBody);
        msg.setMsgId("24084004018081003FAA1DDE2B3F898A00002A9F0000000000000CA0");
        msg.setKeys(String.valueOf(System.currentTimeMillis()));
        msg.setQueueId(queueId);
        msg.setSysFlag(0);
        msg.setBornHostV6Flag();
        msg.setStoreHostAddressV6Flag();
        msg.setBornTimestamp(System.currentTimeMillis());
        msg.setBornHost(new InetSocketAddress("1050:0000:0000:0000:0005:0600:300c:326b", 123));
        msg.setStoreHost(new InetSocketAddress("::1", 124));
        for (int i = 0; i < 1; i++) {
            msg.putUserProperty(String.valueOf(i), "imagoodperson" + i);
        }
        msg.setPropertiesString(MessageDecoder.messageProperties2String(msg.getProperties()));

        return msg;
    }

    public MessageStoreConfig buildStoreConfig(int commitLogFileSize, int cqFileSize,
        boolean enableCqExt, int cqExtFileSize) {
        MessageStoreConfig messageStoreConfig = new MessageStoreConfig();
        messageStoreConfig.setMappedFileSizeCommitLog(commitLogFileSize);
        messageStoreConfig.setMappedFileSizeConsumeQueue(cqFileSize);
        messageStoreConfig.setMappedFileSizeConsumeQueueExt(cqExtFileSize);
        messageStoreConfig.setMessageIndexEnable(false);
        messageStoreConfig.setEnableConsumeQueueExt(enableCqExt);
        messageStoreConfig.setHaListenPort(0);
        messageStoreConfig.setStorePathRootDir(storePath);
        messageStoreConfig.setStorePathCommitLog(storePath + File.separator + "commitlog");

        return messageStoreConfig;
    }

    protected DefaultMessageStore gen() throws Exception {
        MessageStoreConfig messageStoreConfig = buildStoreConfig(
            commitLogFileSize, cqFileSize, true, cqExtFileSize
        );

        BrokerConfig brokerConfig = new BrokerConfig();

        DefaultMessageStore master = new DefaultMessageStore(
            messageStoreConfig,
            new BrokerStatsManager(brokerConfig.getBrokerClusterName(), brokerConfig.isEnableDetailStat()),
            new MessageArrivingListener() {
                @Override
                public void arriving(String topic, int queueId, long logicOffset, long tagsCode,
                    long msgStoreTime, byte[] filterBitMap, Map<String, String> properties) {
                }
            }
            , brokerConfig);

        assertThat(master.load()).isTrue();

        master.start();

        return master;
    }

    protected DefaultMessageStore genForMultiQueue() throws Exception {
        MessageStoreConfig messageStoreConfig = buildStoreConfig(
            commitLogFileSize, cqFileSize, true, cqExtFileSize
        );

        messageStoreConfig.setEnableLmq(true);
        messageStoreConfig.setEnableMultiDispatch(true);

        BrokerConfig brokerConfig = new BrokerConfig();

        DefaultMessageStore master = new DefaultMessageStore(
            messageStoreConfig,
            new BrokerStatsManager(brokerConfig.getBrokerClusterName(), brokerConfig.isEnableDetailStat()),
            new MessageArrivingListener() {
                @Override
                public void arriving(String topic, int queueId, long logicOffset, long tagsCode,
                    long msgStoreTime, byte[] filterBitMap, Map<String, String> properties) {
                }
            }
            , brokerConfig);

        assertThat(master.load()).isTrue();

        master.start();

        return master;
    }

    protected void putMsg(DefaultMessageStore master) {
        long totalMsgs = 200;

        for (long i = 0; i < totalMsgs; i++) {
            if (i < totalMsgs / 2) {
                master.putMessage(buildMessage());
            } else {
                master.putMessage(buildIPv6HostMessage());
            }
        }
    }

    protected void putMsgMultiQueue(DefaultMessageStore master) {
        for (long i = 0; i < 1; i++) {
            master.putMessage(buildMessageMultiQueue());
        }
    }

    private MessageExtBrokerInner buildMessageMultiQueue() {
        MessageExtBrokerInner msg = new MessageExtBrokerInner();
        msg.setTopic(topic);
        msg.setTags("TAG1");
        msg.setKeys("Hello");
        msg.setBody(msgBody);
        msg.setKeys(String.valueOf(System.currentTimeMillis()));
        msg.setQueueId(queueId);
        msg.setSysFlag(0);
        msg.setBornTimestamp(System.currentTimeMillis());
        msg.setStoreHost(StoreHost);
        msg.setBornHost(BornHost);
        for (int i = 0; i < 1; i++) {
            msg.putUserProperty(MessageConst.PROPERTY_INNER_MULTI_DISPATCH, "%LMQ%123,%LMQ%456");
            msg.putUserProperty(String.valueOf(i), "imagoodperson" + i);
        }
        msg.setPropertiesString(MessageDecoder.messageProperties2String(msg.getProperties()));

        return msg;
    }

    protected void deleteDirectory(String rootPath) {
        File file = new File(rootPath);
        deleteFile(file);
    }

    protected void deleteFile(File file) {
        File[] subFiles = file.listFiles();
        if (subFiles != null) {
            for (File sub : subFiles) {
                deleteFile(sub);
            }
        }

        file.delete();
    }

    @Test
    public void testPutMessagePositionInfo_buildCQRepeatedly() throws Exception {
        DefaultMessageStore messageStore = null;
        try {

            messageStore = gen();

            int totalMessages = 10;

            for (int i = 0; i < totalMessages; i++) {
                putMsg(messageStore);
            }
            Thread.sleep(5);

            ConsumeQueueInterface cq = messageStore.getConsumeQueueTable().get(topic).get(queueId);
            Method method = cq.getClass().getDeclaredMethod("putMessagePositionInfo", long.class, int.class, long.class, long.class);

            assertThat(method).isNotNull();

            method.setAccessible(true);

            SelectMappedBufferResult result = messageStore.getCommitLog().getData(0);
            assertThat(result != null).isTrue();

            DispatchRequest dispatchRequest = messageStore.getCommitLog().checkMessageAndReturnSize(result.getByteBuffer(), false, false);

            assertThat(cq).isNotNull();

            Object dispatchResult = method.invoke(cq, dispatchRequest.getCommitLogOffset(),
                dispatchRequest.getMsgSize(), dispatchRequest.getTagsCode(), dispatchRequest.getConsumeQueueOffset());

            assertThat(Boolean.parseBoolean(dispatchResult.toString())).isTrue();

        } finally {
            if (messageStore != null) {
                messageStore.shutdown();
                messageStore.destroy();
            }
            deleteDirectory(storePath);
        }

    }

    @Test
    public void testPutMessagePositionInfoWrapper_MultiQueue() throws Exception {
        DefaultMessageStore messageStore = null;
        try {
            messageStore = genForMultiQueue();

            int totalMessages = 10;

            for (int i = 0; i < totalMessages; i++) {
                putMsgMultiQueue(messageStore);
            }
            Thread.sleep(5);

            ConsumeQueueInterface cq = messageStore.getConsumeQueueTable().get(topic).get(queueId);
            Method method = ((ConsumeQueue) cq).getClass().getDeclaredMethod("putMessagePositionInfoWrapper", DispatchRequest.class);

            assertThat(method).isNotNull();

            method.setAccessible(true);

            SelectMappedBufferResult result = messageStore.getCommitLog().getData(0);
            assertThat(result != null).isTrue();

            DispatchRequest dispatchRequest = messageStore.getCommitLog().checkMessageAndReturnSize(result.getByteBuffer(), false, false);

            assertThat(cq).isNotNull();

            Object dispatchResult = method.invoke(cq, dispatchRequest);

            ConsumeQueueInterface lmqCq1 = messageStore.getConsumeQueueTable().get("%LMQ%123").get(0);

            ConsumeQueueInterface lmqCq2 = messageStore.getConsumeQueueTable().get("%LMQ%456").get(0);

            assertThat(lmqCq1).isNotNull();

            assertThat(lmqCq2).isNotNull();

        } finally {
            if (messageStore != null) {
                messageStore.shutdown();
                messageStore.destroy();
            }
            deleteDirectory(storePath);
        }

    }

    @Test
    public void testPutMessagePositionInfoMultiQueue() throws Exception {
        DefaultMessageStore messageStore = null;
        try {

            messageStore = genForMultiQueue();

            int totalMessages = 10;

            for (int i = 0; i < totalMessages; i++) {
                putMsgMultiQueue(messageStore);
            }
            Thread.sleep(5);

            ConsumeQueueInterface cq = messageStore.getConsumeQueueTable().get(topic).get(queueId);

            ConsumeQueueInterface lmqCq1 = messageStore.getConsumeQueueTable().get("%LMQ%123").get(0);

            ConsumeQueueInterface lmqCq2 = messageStore.getConsumeQueueTable().get("%LMQ%456").get(0);

            assertThat(cq).isNotNull();

            assertThat(lmqCq1).isNotNull();

            assertThat(lmqCq2).isNotNull();

        } finally {
            if (messageStore != null) {
                messageStore.shutdown();
                messageStore.destroy();
            }
            deleteDirectory(storePath);
        }
    }

    @Test
    public void testConsumeQueueWithExtendData() {
        DefaultMessageStore master = null;
        try {
            master = gen();
        } catch (Exception e) {
            e.printStackTrace();
            assertThat(Boolean.FALSE).isTrue();
        }

        master.getDispatcherList().addFirst(new CommitLogDispatcher() {

            @Override
            public void dispatch(DispatchRequest request) {
                runCount++;
            }

            private int runCount = 0;
        });

        try {

            putMsg(master);
            final DefaultMessageStore master1 = master;
            ConsumeQueueInterface cq = await().atMost(3, SECONDS).until(() -> {
                ConcurrentMap<Integer, ConsumeQueueInterface> map = master1.getConsumeQueueTable().get(topic);
                if (map == null) {
                    return null;
                }
                ConsumeQueueInterface anInterface = map.get(queueId);
                return anInterface;
            }, item -> null != item);

            assertThat(cq).isNotNull();

            ReferredIterator<CqUnit> bufferResult = cq.iterateFrom(0);

            assertThat(bufferResult).isNotNull();

            Assert.assertTrue(bufferResult.hasNext());

            try {
                while (bufferResult.hasNext()) {
                    CqUnit cqUnit = bufferResult.next();
                    Assert.assertNotNull(cqUnit);
                    long phyOffset = cqUnit.getPos();
                    int size = cqUnit.getSize();
                    long tagsCode = cqUnit.getTagsCode();

                    assertThat(phyOffset).isGreaterThanOrEqualTo(0);
                    assertThat(size).isGreaterThan(0);
                    assertThat(tagsCode).isGreaterThan(0);

                    ConsumeQueueExt.CqExtUnit cqExtUnit = cqUnit.getCqExtUnit();
                    assertThat(cqExtUnit).isNotNull();
                    assertThat(tagsCode).isEqualTo(cqExtUnit.getTagsCode());
                    assertThat(cqExtUnit.getSize()).isGreaterThan((short) 0);
                    assertThat(cqExtUnit.getMsgStoreTime()).isGreaterThan(0);
                    assertThat(cqExtUnit.getTagsCode()).isGreaterThan(0);
                }

            } finally {
                bufferResult.release();
            }

        } finally {
            master.shutdown();
            master.destroy();
            UtilAll.deleteFile(new File(storePath));
        }
    }

    @Test
    public void testCorrectMinOffset() {
        String topic = "T1";
        int queueId = 0;
        MessageStoreConfig storeConfig = new MessageStoreConfig();
        File tmpDir = new File(System.getProperty("java.io.tmpdir"), "test_correct_min_offset");
        tmpDir.deleteOnExit();
        storeConfig.setStorePathRootDir(tmpDir.getAbsolutePath());
        storeConfig.setEnableConsumeQueueExt(false);
        DefaultMessageStore messageStore = Mockito.mock(DefaultMessageStore.class);
        Mockito.when(messageStore.getMessageStoreConfig()).thenReturn(storeConfig);

        RunningFlags runningFlags = new RunningFlags();
        Mockito.when(messageStore.getRunningFlags()).thenReturn(runningFlags);

        StoreCheckpoint storeCheckpoint = Mockito.mock(StoreCheckpoint.class);
        Mockito.when(messageStore.getStoreCheckpoint()).thenReturn(storeCheckpoint);

        ConsumeQueue consumeQueue = new ConsumeQueue(topic, queueId, storeConfig.getStorePathRootDir(),
            storeConfig.getMappedFileSizeConsumeQueue(), messageStore);

        int max = 10000;
        int message_size = 100;
        for (int i = 0; i < max; ++i) {
            DispatchRequest dispatchRequest = new DispatchRequest(topic, queueId, message_size * i, message_size, 0, 0, i, null, null, 0, 0, null);
            consumeQueue.putMessagePositionInfoWrapper(dispatchRequest);
        }

        consumeQueue.setMinLogicOffset(0L);
        consumeQueue.correctMinOffset(0L);
        Assert.assertEquals(0, consumeQueue.getMinOffsetInQueue());

        consumeQueue.setMinLogicOffset(100);
        consumeQueue.correctMinOffset(2000);
        Assert.assertEquals(20, consumeQueue.getMinOffsetInQueue());

        consumeQueue.setMinLogicOffset((max - 1) * ConsumeQueue.CQ_STORE_UNIT_SIZE);
        consumeQueue.correctMinOffset(max * message_size);
        Assert.assertEquals(max * ConsumeQueue.CQ_STORE_UNIT_SIZE, consumeQueue.getMinLogicOffset());

        consumeQueue.setMinLogicOffset(max * ConsumeQueue.CQ_STORE_UNIT_SIZE);
        consumeQueue.correctMinOffset(max * message_size);
        Assert.assertEquals(max * ConsumeQueue.CQ_STORE_UNIT_SIZE, consumeQueue.getMinLogicOffset());
        consumeQueue.destroy();
    }
}
