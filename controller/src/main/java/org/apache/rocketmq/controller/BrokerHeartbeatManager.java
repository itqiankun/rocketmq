
package org.apache.rocketmq.controller;

import io.netty.channel.Channel;

public interface BrokerHeartbeatManager {

    /**
     * Broker new heartbeat.
     */
    void onBrokerHeartbeat(final String clusterName, final String brokerAddr, final Integer epoch, final Long maxOffset, final Long confirmOffset);

    /**
     * Change the metadata(brokerId ..) for a broker.
     */
    void changeBrokerMetadata(final String clusterName, final String brokerAddr, final Long brokerId);

    /**
     * Start heartbeat manager.
     */
    void start();

    /**
     * Shutdown heartbeat manager.
     */
    void shutdown();

    /**
     * Add BrokerLifecycleListener.
     */
    void addBrokerLifecycleListener(final BrokerLifecycleListener listener);

    /**
     * Register new broker to heartManager.
     */
    void registerBroker(final String clusterName, final String brokerName, final String brokerAddr, final long brokerId,
                        final Long timeoutMillis, final Channel channel, final Integer epoch, final Long maxOffset);

    /**
     * Broker channel close
     */
    void onBrokerChannelClose(final Channel channel);

    /**
     * Get broker live information by clusterName and brokerAddr
     */
    BrokerLiveInfo getBrokerLiveInfo(String clusterName, String brokerAddr);

    /**
     * Check whether broker active
     */
    boolean isBrokerActive(final String clusterName, final String brokerAddr);

    interface BrokerLifecycleListener {
        /**
         * Trigger when broker inactive.
         */
        void onBrokerInactive(final String clusterName, final String brokerName, final String brokerAddress, final long brokerId);
    }
}
