

package org.apache.rocketmq.store.ha;

import java.time.Duration;
import org.apache.rocketmq.store.config.MessageStoreConfig;
import org.junit.Assert;
import org.junit.Test;

import static org.awaitility.Awaitility.await;

public class FlowMonitorTest {

    @Test
    public void testLimit() throws Exception {
        MessageStoreConfig messageStoreConfig = new MessageStoreConfig();
        messageStoreConfig.setHaFlowControlEnable(true);
        messageStoreConfig.setMaxHaTransferByteInSecond(10);

        FlowMonitor flowMonitor = new FlowMonitor(messageStoreConfig);
        flowMonitor.start();

        flowMonitor.addByteCountTransferred(3);
        Boolean flag = await().atMost(Duration.ofSeconds(2)).until(() -> 7 == flowMonitor.canTransferMaxByteNum(), item -> item);
        flag &= await().atMost(Duration.ofSeconds(2)).until(() -> 10 == flowMonitor.canTransferMaxByteNum(), item -> item);
        Assert.assertTrue(flag);

        flowMonitor.shutdown();
    }

    @Test
    public void testSpeed() throws Exception {
        MessageStoreConfig messageStoreConfig = new MessageStoreConfig();
        messageStoreConfig.setHaFlowControlEnable(true);
        messageStoreConfig.setMaxHaTransferByteInSecond(10);

        FlowMonitor flowMonitor = new FlowMonitor(messageStoreConfig);

        flowMonitor.addByteCountTransferred(3);
        flowMonitor.calculateSpeed();
        Assert.assertEquals(3, flowMonitor.getTransferredByteInSecond());

        flowMonitor.addByteCountTransferred(5);
        flowMonitor.calculateSpeed();
        Assert.assertEquals(5, flowMonitor.getTransferredByteInSecond());
    }
}
