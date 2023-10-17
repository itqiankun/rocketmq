
package org.apache.rocketmq.client.consumer;

import java.util.List;
import org.apache.rocketmq.common.message.MessageExt;

public class PopResult {
    private List<MessageExt> msgFoundList;
    private PopStatus popStatus;
    private long popTime;
    private long invisibleTime;
    private long restNum;

    public PopResult(PopStatus popStatus, List<MessageExt> msgFoundList) {
        this.popStatus = popStatus;
        this.msgFoundList = msgFoundList;
    }

    public long getPopTime() {
        return popTime;
    }


    public void setPopTime(long popTime) {
        this.popTime = popTime;
    }

    public long getRestNum() {
        return restNum;
    }

    public void setRestNum(long restNum) {
        this.restNum = restNum;
    }

    public long getInvisibleTime() {
        return invisibleTime;
    }


    public void setInvisibleTime(long invisibleTime) {
        this.invisibleTime = invisibleTime;
    }


    public void setPopStatus(PopStatus popStatus) {
        this.popStatus = popStatus;
    }

    public PopStatus getPopStatus() {
        return popStatus;
    }

    public List<MessageExt> getMsgFoundList() {
        return msgFoundList;
    }

    public void setMsgFoundList(List<MessageExt> msgFoundList) {
        this.msgFoundList = msgFoundList;
    }

    @Override
    public String toString() {
        return "PopResult [popStatus=" + popStatus + ",msgFoundList="
            + (msgFoundList == null ? 0 : msgFoundList.size()) + ",restNum=" + restNum + "]";
    }
}
