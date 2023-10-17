
package org.apache.rocketmq.store.timer;

import org.apache.rocketmq.common.message.MessageExt;

import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class TimerRequest {

    private final long offsetPy;
    private final int sizePy;
    private final long delayTime;

    private final long enqueueTime;
    private final int magic;
    private MessageExt msg;


    //optional would be a good choice, but it relies on JDK 8
    private CountDownLatch latch;

    private boolean released;

    //whether the operation is successful
    private boolean succ;

    private Set<String> deleteList;

    public TimerRequest(long offsetPy, int sizePy, long delayTime, long enqueueTime, int magic) {
        this(offsetPy, sizePy, delayTime, enqueueTime, magic, null);
    }

    public TimerRequest(long offsetPy, int sizePy, long delayTime, long enqueueTime, int magic, MessageExt msg) {
        this.offsetPy = offsetPy;
        this.sizePy = sizePy;
        this.delayTime = delayTime;
        this.enqueueTime = enqueueTime;
        this.magic = magic;
        this.msg = msg;
    }

    public long getOffsetPy() {
        return offsetPy;
    }

    public int getSizePy() {
        return sizePy;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public long getEnqueueTime() {
        return enqueueTime;
    }

    public MessageExt getMsg() {
        return msg;
    }

    public void setMsg(MessageExt msg) {
        this.msg = msg;
    }

    public int getMagic() {
        return magic;
    }

    public Set<String> getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(Set<String> deleteList) {
        this.deleteList = deleteList;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public void idempotentRelease() {
        idempotentRelease(true);
    }

    public void idempotentRelease(boolean succ) {
        this.succ = succ;
        if (!released && latch != null) {
            released = true;
            latch.countDown();
        }
    }

    public boolean isSucc() {
        return succ;
    }
}
