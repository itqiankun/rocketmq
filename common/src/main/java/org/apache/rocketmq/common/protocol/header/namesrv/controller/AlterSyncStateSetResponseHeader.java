
package org.apache.rocketmq.common.protocol.header.namesrv.controller;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class AlterSyncStateSetResponseHeader implements CommandCustomHeader {
    private int newSyncStateSetEpoch;

    public AlterSyncStateSetResponseHeader() {
    }

    public int getNewSyncStateSetEpoch() {
        return newSyncStateSetEpoch;
    }

    public void setNewSyncStateSetEpoch(int newSyncStateSetEpoch) {
        this.newSyncStateSetEpoch = newSyncStateSetEpoch;
    }

    @Override
    public String toString() {
        return "AlterSyncStateSetResponseHeader{" +
            "newSyncStateSetEpoch=" + newSyncStateSetEpoch +
            '}';
    }

    @Override
    public void checkFields() throws RemotingCommandException {
    }
}
