
package org.apache.rocketmq.controller.impl.event;

import java.util.HashSet;
import java.util.Set;

/**
 * The event alters the syncStateSet of target broker.
 * Triggered by the AlterSyncStateSetApi.
 */
public class AlterSyncStateSetEvent implements EventMessage {

    private final String brokerName;
    private final Set<String/*Address*/> newSyncStateSet;

    public AlterSyncStateSetEvent(String brokerName, Set<String> newSyncStateSet) {
        this.brokerName = brokerName;
        this.newSyncStateSet = new HashSet<>(newSyncStateSet);
    }

    @Override
    public EventType getEventType() {
        return EventType.ALTER_SYNC_STATE_SET_EVENT;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public Set<String> getNewSyncStateSet() {
        return new HashSet<>(newSyncStateSet);
    }

    @Override
    public String toString() {
        return "AlterSyncStateSetEvent{" +
            "brokerName='" + brokerName + '\'' +
            ", newSyncStateSet=" + newSyncStateSet +
            '}';
    }
}
