

package org.apache.rocketmq.test.container;

import java.time.Duration;
import org.apache.rocketmq.common.BrokerConfig;
import org.apache.rocketmq.common.protocol.body.BrokerMemberGroup;
import org.junit.Ignore;
import org.junit.Test;

import static org.awaitility.Awaitility.await;

@Ignore
public class BrokerMemberGroupIT extends ContainerIntegrationTestBase {
    @Test
    public void testSyncBrokerMemberGroup() throws Exception {
        await().atMost(Duration.ofSeconds(5)).until(() -> {
            final BrokerConfig brokerConfig = master1With3Replicas.getBrokerConfig();
            final BrokerMemberGroup memberGroup = master1With3Replicas.getBrokerOuterAPI()
                .syncBrokerMemberGroup(brokerConfig.getBrokerClusterName(), brokerConfig.getBrokerName());

            return memberGroup.getBrokerAddrs().size() == 3;
        });

        await().atMost(Duration.ofSeconds(5)).until(() -> {
            final BrokerConfig brokerConfig = master3With3Replicas.getBrokerConfig();
            final BrokerMemberGroup memberGroup = master3With3Replicas.getBrokerOuterAPI()
                .syncBrokerMemberGroup(brokerConfig.getBrokerClusterName(), brokerConfig.getBrokerName());

            return memberGroup.getBrokerAddrs().size() == 3;
        });

        removeSlaveBroker(1, brokerContainer1, master3With3Replicas);
        removeSlaveBroker(1, brokerContainer2, master1With3Replicas);

        await().atMost(Duration.ofSeconds(5)).until(() -> {
            final BrokerConfig brokerConfig = master1With3Replicas.getBrokerConfig();
            final BrokerMemberGroup memberGroup = master1With3Replicas.getBrokerOuterAPI()
                .syncBrokerMemberGroup(brokerConfig.getBrokerClusterName(), brokerConfig.getBrokerName());

            return memberGroup.getBrokerAddrs().size() == 2 && memberGroup.getBrokerAddrs().get(1L) == null;
        });

        await().atMost(Duration.ofSeconds(5)).until(() -> {
            final BrokerConfig brokerConfig = master3With3Replicas.getBrokerConfig();
            final BrokerMemberGroup memberGroup = master3With3Replicas.getBrokerOuterAPI()
                .syncBrokerMemberGroup(brokerConfig.getBrokerClusterName(), brokerConfig.getBrokerName());
            return memberGroup.getBrokerAddrs().size() == 2 && memberGroup.getBrokerAddrs().get(1L) == null;
        });

        createAndAddSlave(1, brokerContainer2, master1With3Replicas);
        createAndAddSlave(1, brokerContainer1, master3With3Replicas);

        awaitUntilSlaveOK();
    }
}
