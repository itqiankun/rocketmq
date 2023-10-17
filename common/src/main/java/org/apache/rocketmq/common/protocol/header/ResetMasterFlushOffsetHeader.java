

package org.apache.rocketmq.common.protocol.header;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class ResetMasterFlushOffsetHeader implements CommandCustomHeader {
    @CFNotNull
    private Long masterFlushOffset;

    @Override
    public void checkFields() throws RemotingCommandException {

    }

    public Long getMasterFlushOffset() {
        return masterFlushOffset;
    }

    public void setMasterFlushOffset(Long masterFlushOffset) {
        this.masterFlushOffset = masterFlushOffset;
    }
}
