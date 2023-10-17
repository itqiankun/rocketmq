
package org.apache.rocketmq.controller.impl.event;

/**
 * Event type (name, id);
 */
public enum EventType {
    ALTER_SYNC_STATE_SET_EVENT("AlterSyncStateSetEvent", (short) 1),
    APPLY_BROKER_ID_EVENT("ApplyBrokerIdEvent", (short) 2),
    ELECT_MASTER_EVENT("ElectMasterEvent", (short) 3),
    READ_EVENT("ReadEvent", (short) 4),
    CLEAN_BROKER_DATA_EVENT("CleanBrokerDataEvent", (short) 5);

    private final String name;
    private final short id;

    EventType(String name, short id) {
        this.name = name;
        this.id = id;
    }

    public static EventType from(short id) {
        switch (id) {
            case 1:
                return ALTER_SYNC_STATE_SET_EVENT;
            case 2:
                return APPLY_BROKER_ID_EVENT;
            case 3:
                return ELECT_MASTER_EVENT;
            case 4:
                return READ_EVENT;
            case 5:
                return CLEAN_BROKER_DATA_EVENT;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public short getId() {
        return id;
    }
}
