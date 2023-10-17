
package org.apache.rocketmq.controller.impl.manager;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages the syncStateSet of broker replicas.
 */
public class SyncStateInfo {
    private final String clusterName;
    private final String brokerName;

    private Set<String/*Address*/> syncStateSet;
    private int syncStateSetEpoch;

    private String masterAddress;
    private int masterEpoch;

    public SyncStateInfo(String clusterName, String brokerName, String masterAddress) {
        this.clusterName = clusterName;
        this.brokerName = brokerName;
        this.masterAddress = masterAddress;
        this.masterEpoch = 1;
        this.syncStateSet = new HashSet<>();
        this.syncStateSet.add(masterAddress);
        this.syncStateSetEpoch = 1;
    }

    public void updateMasterInfo(String masterAddress) {
        this.masterAddress = masterAddress;
        this.masterEpoch++;
    }

    public void updateSyncStateSetInfo(Set<String> newSyncStateSet) {
        this.syncStateSet = new HashSet<>(newSyncStateSet);
        this.syncStateSetEpoch++;
    }

    public boolean isMasterExist() {
        return !this.masterAddress.isEmpty();
    }

    public String getClusterName() {
        return clusterName;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public Set<String> getSyncStateSet() {
        return new HashSet<>(syncStateSet);
    }

    public int getSyncStateSetEpoch() {
        return syncStateSetEpoch;
    }

    public String getMasterAddress() {
        return masterAddress;
    }

    public int getMasterEpoch() {
        return masterEpoch;
    }

    public void removeSyncState(final String address) {
        syncStateSet.remove(address);
    }
}
