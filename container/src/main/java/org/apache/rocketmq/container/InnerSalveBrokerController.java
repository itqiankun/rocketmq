

package org.apache.rocketmq.container;

import com.google.common.base.Preconditions;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.rocketmq.common.BrokerConfig;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.store.config.MessageStoreConfig;

public class InnerSalveBrokerController extends InnerBrokerController {

    private final Lock lock = new ReentrantLock();

    public InnerSalveBrokerController(final BrokerContainer brokerContainer,
        final BrokerConfig brokerConfig,
        final MessageStoreConfig storeConfig) {
        super(brokerContainer, brokerConfig, storeConfig);
        // Check configs
        checkSlaveBrokerConfig();
    }

    private void checkSlaveBrokerConfig() {
        Preconditions.checkNotNull(brokerConfig.getBrokerClusterName());
        Preconditions.checkNotNull(brokerConfig.getBrokerName());
        Preconditions.checkArgument(brokerConfig.getBrokerId() != MixAll.MASTER_ID);
    }
}
