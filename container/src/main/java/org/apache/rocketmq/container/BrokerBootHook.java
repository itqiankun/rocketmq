

package org.apache.rocketmq.container;

import java.util.Properties;
import org.apache.rocketmq.broker.BrokerController;

public interface BrokerBootHook {
    /**
     * Name of the hook.
     *
     * @return name of the hook
     */
    String hookName();

    /**
     * Code to execute before broker start.
     *
     * @param brokerController broker to start
     * @param properties broker properties
     * @throws Exception when execute hook
     */
    void executeBeforeStart(BrokerController brokerController, Properties properties) throws Exception;

    /**
     * Code to execute after broker start.
     *
     * @param brokerController broker to start
     * @param properties broker properties
     * @throws Exception when execute hook
     */
    void executeAfterStart(BrokerController brokerController, Properties properties) throws Exception;
}
