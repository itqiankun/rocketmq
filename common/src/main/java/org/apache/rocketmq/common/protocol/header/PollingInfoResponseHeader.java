

package org.apache.rocketmq.common.protocol.header;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class PollingInfoResponseHeader implements CommandCustomHeader {


    @CFNotNull
    private int pollingNum;

    public int getPollingNum() {
        return pollingNum;
    }

    public void setPollingNum(int pollingNum) {
        this.pollingNum = pollingNum;
    }

    @Override
    public void checkFields() throws RemotingCommandException {
    }
}
