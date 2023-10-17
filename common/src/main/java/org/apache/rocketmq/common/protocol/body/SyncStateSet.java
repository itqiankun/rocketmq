

package org.apache.rocketmq.common.protocol.body;

import java.util.HashSet;
import java.util.Set;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class SyncStateSet extends RemotingSerializable {
    private Set<String> syncStateSet;
    private int syncStateSetEpoch;

    public SyncStateSet(Set<String> syncStateSet, int syncStateSetEpoch) {
        this.syncStateSet = new HashSet<>(syncStateSet);
        this.syncStateSetEpoch = syncStateSetEpoch;
    }

    public Set<String> getSyncStateSet() {
        return new HashSet<>(syncStateSet);
    }

    public void setSyncStateSet(Set<String> syncStateSet) {
        this.syncStateSet = new HashSet<>(syncStateSet);
    }

    public int getSyncStateSetEpoch() {
        return syncStateSetEpoch;
    }

    public void setSyncStateSetEpoch(int syncStateSetEpoch) {
        this.syncStateSetEpoch = syncStateSetEpoch;
    }

    @Override
    public String toString() {
        return "SyncStateSet{" +
            "syncStateSet=" + syncStateSet +
            ", syncStateSetEpoch=" + syncStateSetEpoch +
            '}';
    }
}
