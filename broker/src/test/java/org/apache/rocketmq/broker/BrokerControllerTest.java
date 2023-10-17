

package org.apache.rocketmq.broker;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.broker.latency.FutureTaskExt;
import org.apache.rocketmq.common.BrokerConfig;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.remoting.netty.NettyClientConfig;
import org.apache.rocketmq.remoting.netty.NettyServerConfig;
import org.apache.rocketmq.remoting.netty.RequestTask;
import org.apache.rocketmq.store.config.MessageStoreConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BrokerControllerTest {

    private MessageStoreConfig messageStoreConfig;

    private BrokerConfig brokerConfig;

    private NettyServerConfig nettyServerConfig;


    @Before
    public void setUp() {
        messageStoreConfig = new MessageStoreConfig();
        String storePathRootDir = System.getProperty("java.io.tmpdir") + File.separator + "store-"
                + UUID.randomUUID().toString();
        messageStoreConfig.setStorePathRootDir(storePathRootDir);

        brokerConfig = new BrokerConfig();

        nettyServerConfig = new NettyServerConfig();
        nettyServerConfig.setListenPort(0);

    }

    @Test
    public void testBrokerRestart() throws Exception {
        BrokerController brokerController = new BrokerController(brokerConfig, nettyServerConfig, new NettyClientConfig(), messageStoreConfig);
        assertThat(brokerController.initialize()).isTrue();
        brokerController.start();
        brokerController.shutdown();
    }

    @After
    public void destroy() {
        UtilAll.deleteFile(new File(messageStoreConfig.getStorePathRootDir()));
    }

    @Test
    public void testHeadSlowTimeMills() throws Exception {
        BrokerController brokerController = new BrokerController(brokerConfig, nettyServerConfig, new NettyClientConfig(), messageStoreConfig);
        brokerController.initialize();
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

        //create task is not instance of FutureTaskExt;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };

        RequestTask requestTask = new RequestTask(runnable, null, null);
        // the requestTask is not the head of queue;
        queue.add(new FutureTaskExt<>(requestTask, null));

        long headSlowTimeMills = 100;
        TimeUnit.MILLISECONDS.sleep(headSlowTimeMills);
        assertThat(brokerController.headSlowTimeMills(queue)).isGreaterThanOrEqualTo(headSlowTimeMills);
    }
}
