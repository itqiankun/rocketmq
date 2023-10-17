
package org.apache.rocketmq.broker.client;

public enum ProducerGroupEvent {
    /**
     * The group of producer is unregistered.
     */
    GROUP_UNREGISTER,
    /**
     * The client of this producer is unregistered.
     */
    CLIENT_UNREGISTER
}
