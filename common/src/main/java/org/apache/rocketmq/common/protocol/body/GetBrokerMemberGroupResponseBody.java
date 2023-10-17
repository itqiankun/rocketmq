

package org.apache.rocketmq.common.protocol.body;

import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class GetBrokerMemberGroupResponseBody extends RemotingSerializable {
    // Contains the broker member info of the same broker group
    private BrokerMemberGroup brokerMemberGroup;

    public BrokerMemberGroup getBrokerMemberGroup() {
        return brokerMemberGroup;
    }

    public void setBrokerMemberGroup(final BrokerMemberGroup brokerMemberGroup) {
        this.brokerMemberGroup = brokerMemberGroup;
    }
}
