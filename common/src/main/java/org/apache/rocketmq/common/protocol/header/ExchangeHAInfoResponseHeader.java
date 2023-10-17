

package org.apache.rocketmq.common.protocol.header;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.annotation.CFNullable;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class ExchangeHAInfoResponseHeader implements CommandCustomHeader {
    @CFNullable
    public String masterHaAddress;

    @CFNullable
    public Long masterFlushOffset;

    @CFNullable
    public String masterAddress;

    @Override
    public void checkFields() throws RemotingCommandException {

    }

    public String getMasterHaAddress() {
        return masterHaAddress;
    }

    public void setMasterHaAddress(String masterHaAddress) {
        this.masterHaAddress = masterHaAddress;
    }

    public Long getMasterFlushOffset() {
        return masterFlushOffset;
    }

    public void setMasterFlushOffset(Long masterFlushOffset) {
        this.masterFlushOffset = masterFlushOffset;
    }

    public String getMasterAddress() {
        return masterAddress;
    }

    public void setMasterAddress(String masterAddress) {
        this.masterAddress = masterAddress;
    }
}
