
package org.apache.rocketmq.broker;

public interface ShutdownHook {
    /**
     * Code to execute before broker shutdown.
     *
     * @param controller broker to shutdown
     */
    void beforeShutdown(BrokerController controller);
}
