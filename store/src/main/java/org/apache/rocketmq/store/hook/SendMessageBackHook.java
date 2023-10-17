
package org.apache.rocketmq.store.hook;

import java.util.List;
import org.apache.rocketmq.common.message.MessageExt;

public interface SendMessageBackHook {

    /**
     * Slave send message back to master at certain offset when HA handshake
     *
     * @param msgList
     * @param brokerName
     * @param brokerAddr
     * @return
     */
    boolean executeSendMessageBack(List<MessageExt> msgList, String brokerName, String brokerAddr);
}
