

/**
 * $Id: SendMessageResponseHeader.java 1835 2013-05-16 02:00:50Z vintagewang@apache.org $
 */
package org.apache.rocketmq.common.protocol.header;

import java.util.HashMap;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;
import org.apache.rocketmq.remoting.protocol.FastCodesHeader;

import io.netty.buffer.ByteBuf;

public class SendMessageResponseHeader implements CommandCustomHeader, FastCodesHeader {
    @CFNotNull
    private String msgId;
    @CFNotNull
    private Integer queueId;
    @CFNotNull
    private Long queueOffset;
    private String transactionId;
    private String batchUniqId;

    @Override
    public void checkFields() throws RemotingCommandException {
    }

    @Override
    public void encode(ByteBuf out) {
        writeIfNotNull(out, "msgId", msgId);
        writeIfNotNull(out, "queueId", queueId);
        writeIfNotNull(out, "queueOffset", queueOffset);
        writeIfNotNull(out, "transactionId", transactionId);
        writeIfNotNull(out, "batchUniqId", batchUniqId);
    }

    @Override
    public void decode(HashMap<String, String> fields) throws RemotingCommandException {
        String str = getAndCheckNotNull(fields, "msgId");
        if (str != null) {
            this.msgId = str;
        }

        str = getAndCheckNotNull(fields, "queueId");
        if (str != null) {
            this.queueId = Integer.parseInt(str);
        }

        str = getAndCheckNotNull(fields, "queueOffset");
        if (str != null) {
            this.queueOffset = Long.parseLong(str);
        }

        str = fields.get("transactionId");
        if (str != null) {
            this.transactionId = str;
        }

        str = fields.get("batchUniqId");
        if (str != null) {
            this.batchUniqId = str;
        }
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Integer getQueueId() {
        return queueId;
    }

    public void setQueueId(Integer queueId) {
        this.queueId = queueId;
    }

    public Long getQueueOffset() {
        return queueOffset;
    }

    public void setQueueOffset(Long queueOffset) {
        this.queueOffset = queueOffset;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getBatchUniqId() {
        return batchUniqId;
    }

    public void setBatchUniqId(String batchUniqId) {
        this.batchUniqId = batchUniqId;
    }
}
