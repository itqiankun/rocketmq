

package org.apache.rocketmq.store.ha;

import org.apache.rocketmq.common.BrokerConfig;
import org.apache.rocketmq.store.DefaultMessageStore;
import org.apache.rocketmq.store.ha.DefaultHAClient;
import org.apache.rocketmq.store.ha.HAClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HAClientTest {
    private HAClient haClient;

    @Mock
    private DefaultMessageStore messageStore;

    @Before
    public void setUp() throws Exception {
//        when(messageStore.getMessageStoreConfig()).thenReturn(new MessageStoreConfig());
        when(messageStore.getBrokerConfig()).thenReturn(new BrokerConfig());
        this.haClient = new DefaultHAClient(this.messageStore);
    }

    @After
    public void tearDown() throws Exception {
        this.haClient.shutdown();
    }

    @Test
    public void updateMasterAddress() {
        assertThat(this.haClient.getMasterAddress()).isNull();
        this.haClient.updateMasterAddress("127.0.0.1:10911");
        assertThat(this.haClient.getMasterAddress()).isEqualTo("127.0.0.1:10911");

        this.haClient.updateMasterAddress("127.0.0.1:10912");
        assertThat(this.haClient.getMasterAddress()).isEqualTo("127.0.0.1:10912");
    }

    @Test
    public void updateHaMasterAddress() {
        assertThat(this.haClient.getHaMasterAddress()).isNull();
        this.haClient.updateHaMasterAddress("127.0.0.1:10911");
        assertThat(this.haClient.getHaMasterAddress()).isEqualTo("127.0.0.1:10911");

        this.haClient.updateHaMasterAddress("127.0.0.1:10912");
        assertThat(this.haClient.getHaMasterAddress()).isEqualTo("127.0.0.1:10912");
    }
}
