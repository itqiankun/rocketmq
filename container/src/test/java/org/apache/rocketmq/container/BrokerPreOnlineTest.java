

package org.apache.rocketmq.container;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.rocketmq.broker.BrokerController;
import org.apache.rocketmq.broker.BrokerPreOnlineService;
import org.apache.rocketmq.broker.out.BrokerOuterAPI;
import org.apache.rocketmq.broker.transaction.TransactionalMessageCheckService;
import org.apache.rocketmq.common.BrokerConfig;
import org.apache.rocketmq.common.protocol.body.BrokerMemberGroup;
import org.apache.rocketmq.store.DefaultMessageStore;
import org.apache.rocketmq.store.config.MessageStoreConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BrokerPreOnlineTest {
    @Mock
    private BrokerContainer brokerContainer;

    private InnerBrokerController innerBrokerController;

    @Mock
    private BrokerOuterAPI brokerOuterAPI;

    public void init() throws Exception {
        when(brokerContainer.getBrokerOuterAPI()).thenReturn(brokerOuterAPI);

        BrokerMemberGroup brokerMemberGroup1 = new BrokerMemberGroup();
        Map<Long, String> brokerAddrMap = new HashMap<>();
        brokerAddrMap.put(1L, "127.0.0.1:20911");
        brokerMemberGroup1.setBrokerAddrs(brokerAddrMap);

        BrokerMemberGroup brokerMemberGroup2 = new BrokerMemberGroup();
        brokerMemberGroup2.setBrokerAddrs(new HashMap<>());

//        when(brokerOuterAPI.syncBrokerMemberGroup(anyString(), anyString()))
//            .thenReturn(brokerMemberGroup1)
//            .thenReturn(brokerMemberGroup2);
//        doNothing().when(brokerOuterAPI).sendBrokerHaInfo(anyString(), anyString(), anyLong(), anyString());

        DefaultMessageStore defaultMessageStore = mock(DefaultMessageStore.class);
        when(defaultMessageStore.getMessageStoreConfig()).thenReturn(new MessageStoreConfig());
        when(defaultMessageStore.getBrokerConfig()).thenReturn(new BrokerConfig());

//        HAService haService = new DefaultHAService();
//        haService.init(defaultMessageStore);
//        haService.start();
//
//        when(defaultMessageStore.getHaService()).thenReturn(haService);

        innerBrokerController = new InnerBrokerController(brokerContainer,
            defaultMessageStore.getBrokerConfig(),
            defaultMessageStore.getMessageStoreConfig());

        innerBrokerController.setTransactionalMessageCheckService(new TransactionalMessageCheckService(innerBrokerController));

        Field field = BrokerController.class.getDeclaredField("isIsolated");
        field.setAccessible(true);
        field.set(innerBrokerController, true);

        field = BrokerController.class.getDeclaredField("messageStore");
        field.setAccessible(true);
        field.set(innerBrokerController, defaultMessageStore);
    }

    @Test
    public void testMasterOnlineConnTimeout() throws Exception {
        init();
        BrokerPreOnlineService brokerPreOnlineService = new BrokerPreOnlineService(innerBrokerController);

        brokerPreOnlineService.start();

        await().atMost(Duration.ofSeconds(30)).until(() -> !innerBrokerController.isIsolated());
    }
}
