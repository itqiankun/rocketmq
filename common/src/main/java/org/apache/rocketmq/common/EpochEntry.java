

package org.apache.rocketmq.common;

import java.util.Objects;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;

public class EpochEntry extends RemotingSerializable {

    private int epoch;
    private long startOffset;
    private long endOffset = Long.MAX_VALUE;

    public EpochEntry(EpochEntry entry) {
        this.epoch = entry.getEpoch();
        this.startOffset = entry.getStartOffset();
        this.endOffset = entry.getEndOffset();
    }

    public EpochEntry(int epoch, long startOffset) {
        this.epoch = epoch;
        this.startOffset = startOffset;
    }

    public int getEpoch() {
        return epoch;
    }

    public void setEpoch(int epoch) {
        this.epoch = epoch;
    }

    public long getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(long startOffset) {
        this.startOffset = startOffset;
    }

    public long getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(long endOffset) {
        this.endOffset = endOffset;
    }

    @Override
    public String toString() {
        return "EpochEntry{" +
            "epoch=" + epoch +
            ", startOffset=" + startOffset +
            ", endOffset=" + endOffset +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EpochEntry entry = (EpochEntry) o;
        return epoch == entry.epoch && startOffset == entry.startOffset && endOffset == entry.endOffset;
    }

    @Override
    public int hashCode() {
        return Objects.hash(epoch, startOffset, endOffset);
    }
}
