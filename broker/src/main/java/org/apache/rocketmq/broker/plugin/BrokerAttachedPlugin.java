

package org.apache.rocketmq.broker.plugin;

import java.util.Map;

public interface BrokerAttachedPlugin {

    /**
     * Get plugin name
     *
     * @return plugin name
     */
    String pluginName();

    /**
     * Load broker attached plugin.
     *
     * @return load success or failed
     */
    boolean load();

    /**
     * Start broker attached plugin.
     */
    void start();

    /**
     * Shutdown broker attached plugin.
     */
    void shutdown();

    /**
     * Sync metadata from master.
     */
    void syncMetadata();

    /**
     * Sync metadata reverse from slave
     *
     * @param brokerAddr
     */
    void syncMetadataReverse(String brokerAddr) throws Exception;

    /**
     * Some plugin need build runningInfo when prepare runtime info.
     *
     * @param runtimeInfo
     */
    void buildRuntimeInfo(Map<String, String> runtimeInfo);

    /**
     * Some plugin need do something when status changed. For example, brokerRole change to master or slave.
     *
     * @param shouldStart
     */
    void statusChanged(boolean shouldStart);

}
