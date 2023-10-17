
package org.apache.rocketmq.controller.impl.event;

/**
 * The event trys to apply a new id for a new broker.
 * Triggered by the RegisterBrokerApi.
 */
public class ApplyBrokerIdEvent implements EventMessage {
    private final String brokerName;
    private final String brokerAddress;
    private final long newBrokerId;

    public ApplyBrokerIdEvent(String brokerName, String brokerAddress, long newBrokerId) {
        this.brokerName = brokerName;
        this.brokerAddress = brokerAddress;
        this.newBrokerId = newBrokerId;
    }

    @Override
    public EventType getEventType() {
        return EventType.APPLY_BROKER_ID_EVENT;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public String getBrokerAddress() {
        return brokerAddress;
    }

    public long getNewBrokerId() {
        return newBrokerId;
    }

    @Override
    public String toString() {
        return "ApplyBrokerIdEvent{" +
            "brokerName='" + brokerName + '\'' +
            ", brokerAddress='" + brokerAddress + '\'' +
            ", newBrokerId=" + newBrokerId +
            '}';
    }
}
