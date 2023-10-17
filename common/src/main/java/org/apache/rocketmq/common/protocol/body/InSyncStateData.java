
package org.apache.rocketmq.common.protocol.body;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class InSyncStateData extends RemotingSerializable  {
    private Map<String/*brokerName*/, InSyncStateSet> inSyncStateTable;

    public InSyncStateData() {
        this.inSyncStateTable = new HashMap<>();
    }

    public void addInSyncState(final String brokerName, final InSyncStateSet inSyncState) {
        this.inSyncStateTable.put(brokerName, inSyncState);
    }

    public Map<String, InSyncStateSet> getInSyncStateTable() {
        return inSyncStateTable;
    }

    public void setInSyncStateTable(
        Map<String, InSyncStateSet> inSyncStateTable) {
        this.inSyncStateTable = inSyncStateTable;
    }

    public static class InSyncStateSet extends RemotingSerializable {
        private String masterAddress;
        private int masterEpoch;
        private int syncStateSetEpoch;
        private List<InSyncMember> inSyncMembers;

        public InSyncStateSet(String masterAddress, int masterEpoch, int syncStateSetEpoch,
            List<InSyncMember> inSyncMembers) {
            this.masterAddress = masterAddress;
            this.masterEpoch = masterEpoch;
            this.syncStateSetEpoch = syncStateSetEpoch;
            this.inSyncMembers = inSyncMembers;
        }

        public String getMasterAddress() {
            return masterAddress;
        }

        public void setMasterAddress(String masterAddress) {
            this.masterAddress = masterAddress;
        }

        public int getMasterEpoch() {
            return masterEpoch;
        }

        public void setMasterEpoch(int masterEpoch) {
            this.masterEpoch = masterEpoch;
        }

        public int getSyncStateSetEpoch() {
            return syncStateSetEpoch;
        }

        public void setSyncStateSetEpoch(int syncStateSetEpoch) {
            this.syncStateSetEpoch = syncStateSetEpoch;
        }

        public List<InSyncMember> getInSyncMembers() {
            return inSyncMembers;
        }

        public void setInSyncMembers(
            List<InSyncMember> inSyncMembers) {
            this.inSyncMembers = inSyncMembers;
        }
    }

    public static class InSyncMember extends RemotingSerializable {
        private String address;
        private Long brokerId;

        public InSyncMember(String address, Long brokerId) {
            this.address = address;
            this.brokerId = brokerId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Long getBrokerId() {
            return brokerId;
        }

        public void setBrokerId(Long brokerId) {
            this.brokerId = brokerId;
        }

        @Override
        public String toString() {
            return "InSyncMember{" +
                "address='" + address + '\'' +
                ", brokerId=" + brokerId +
                '}';
        }
    }
}
