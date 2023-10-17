
package org.apache.rocketmq.common.protocol.header;

import org.apache.rocketmq.remoting.CommandCustomHeader;
import org.apache.rocketmq.remoting.annotation.CFNotNull;
import org.apache.rocketmq.remoting.exception.RemotingCommandException;

public class PopMessageResponseHeader implements CommandCustomHeader {


    @CFNotNull
    private long popTime;
    @CFNotNull
    private long invisibleTime;

    @CFNotNull
    private int reviveQid;
    /**
     * the rest num in queue
     */
    @CFNotNull
    private long restNum;

    private String startOffsetInfo;
    private String msgOffsetInfo;
    private String orderCountInfo;

    @Override
    public void checkFields() throws RemotingCommandException {
    }

    public long getPopTime() {
        return popTime;
    }

    public void setPopTime(long popTime) {
        this.popTime = popTime;
    }

    public long getInvisibleTime() {
        return invisibleTime;
    }

    public long getRestNum() {
        return restNum;
    }

    public void setRestNum(long restNum) {
        this.restNum = restNum;
    }

    public void setInvisibleTime(long invisibleTime) {
        this.invisibleTime = invisibleTime;
    }

    public int getReviveQid() {
        return reviveQid;
    }

    public void setReviveQid(int reviveQid) {
        this.reviveQid = reviveQid;
    }

    public String getStartOffsetInfo() {
        return startOffsetInfo;
    }

    public void setStartOffsetInfo(String startOffsetInfo) {
        this.startOffsetInfo = startOffsetInfo;
    }

    public String getMsgOffsetInfo() {
        return msgOffsetInfo;
    }

    public void setMsgOffsetInfo(String msgOffsetInfo) {
        this.msgOffsetInfo = msgOffsetInfo;
    }

    public String getOrderCountInfo() {
        return orderCountInfo;
    }

    public void setOrderCountInfo(String orderCountInfo) {
        this.orderCountInfo = orderCountInfo;
    }
}
