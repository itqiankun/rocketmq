

package org.apache.rocketmq.store.queue;

import org.apache.rocketmq.store.logfile.MappedFile;

public class BatchOffsetIndex {

    private final MappedFile mappedFile;
    private final int indexPos;
    private final long msgOffset;
    private final short batchSize;
    private final long storeTimestamp;

    public BatchOffsetIndex(MappedFile file, int pos, long msgOffset, short size, long storeTimestamp) {
        mappedFile = file;
        indexPos = pos;
        this.msgOffset = msgOffset;
        batchSize = size;
        this.storeTimestamp = storeTimestamp;
    }

    public MappedFile getMappedFile() {
        return mappedFile;
    }

    public int getIndexPos() {
        return indexPos;
    }

    public long getMsgOffset() {
        return msgOffset;
    }

    public short getBatchSize() {
        return batchSize;
    }

    public long getStoreTimestamp() {
        return storeTimestamp;
    }
}
