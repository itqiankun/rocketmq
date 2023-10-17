
package org.apache.rocketmq.controller.impl.event;

/**
 * The event trys to elect a new master for target broker.
 * Triggered by the ElectMasterApi.
 */
public class ElectMasterEvent implements EventMessage {
    // Mark whether a new master was elected.
    private final boolean newMasterElected;
    private final String brokerName;
    private final String newMasterAddress;
    private final String clusterName;

    public ElectMasterEvent(boolean newMasterElected, String brokerName) {
        this(newMasterElected, brokerName, "", "");
    }

    public ElectMasterEvent(String brokerName, String newMasterAddress) {
        this(true, brokerName, newMasterAddress, "");
    }

    public ElectMasterEvent(boolean newMasterElected, String brokerName, String newMasterAddress, String clusterName) {
        this.newMasterElected = newMasterElected;
        this.brokerName = brokerName;
        this.newMasterAddress = newMasterAddress;
        this.clusterName = clusterName;
    }

    @Override
    public EventType getEventType() {
        return EventType.ELECT_MASTER_EVENT;
    }

    public boolean getNewMasterElected() {
        return newMasterElected;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public String getNewMasterAddress() {
        return newMasterAddress;
    }

    public String getClusterName() {
        return clusterName;
    }

    @Override
    public String toString() {
        return "ElectMasterEvent{" +
            "isNewMasterElected=" + newMasterElected +
            ", brokerName='" + brokerName + '\'' +
            ", newMasterAddress='" + newMasterAddress + '\'' +
            ", clusterName='" + clusterName + '\'' +
            '}';
    }
}
