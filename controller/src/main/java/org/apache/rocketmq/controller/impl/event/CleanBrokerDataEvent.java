

package org.apache.rocketmq.controller.impl.event;

import java.util.Set;

public class CleanBrokerDataEvent implements EventMessage {

    private String brokerName;

    private Set<String> brokerAddressSet;

    public CleanBrokerDataEvent(String brokerName, Set<String> brokerAddressSet) {
        this.brokerName = brokerName;
        this.brokerAddressSet = brokerAddressSet;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public Set<String> getBrokerAddressSet() {
        return brokerAddressSet;
    }

    public void setBrokerAddressSet(Set<String> brokerAddressSet) {
        this.brokerAddressSet = brokerAddressSet;
    }

    /**
     * Returns the event type of this message
     */
    @Override
    public EventType getEventType() {
        return EventType.CLEAN_BROKER_DATA_EVENT;
    }

    @Override
    public String toString() {
        return "CleanBrokerDataEvent{" +
            "brokerName='" + brokerName + '\'' +
            ", brokerAddressSet=" + brokerAddressSet +
            '}';
    }
}
