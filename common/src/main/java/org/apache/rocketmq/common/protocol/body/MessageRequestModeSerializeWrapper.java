
package org.apache.rocketmq.common.protocol.body;

import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

import java.util.concurrent.ConcurrentHashMap;

public class MessageRequestModeSerializeWrapper extends RemotingSerializable {

    private ConcurrentHashMap<String/* Topic */, ConcurrentHashMap<String/* Group */, SetMessageRequestModeRequestBody>>
            messageRequestModeMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, SetMessageRequestModeRequestBody>>();

    public ConcurrentHashMap<String, ConcurrentHashMap<String, SetMessageRequestModeRequestBody>> getMessageRequestModeMap() {
        return messageRequestModeMap;
    }

    public void setMessageRequestModeMap(ConcurrentHashMap<String, ConcurrentHashMap<String, SetMessageRequestModeRequestBody>> messageRequestModeMap) {
        this.messageRequestModeMap = messageRequestModeMap;
    }
}
