
package org.apache.rocketmq.store;

import org.apache.rocketmq.store.logfile.MappedFile;

public class FileQueueSnapshot {
    private MappedFile firstFile;
    private long firstFileIndex;
    private MappedFile lastFile;
    private long lastFileIndex;
    private long currentFile;
    private long currentFileIndex;
    private long behindCount;
    private boolean exist;

    public FileQueueSnapshot() {
    }

    public FileQueueSnapshot(MappedFile firstFile, long firstFileIndex, MappedFile lastFile, long lastFileIndex, long currentFile, long currentFileIndex, long behindCount, boolean exist) {
        this.firstFile = firstFile;
        this.firstFileIndex = firstFileIndex;
        this.lastFile = lastFile;
        this.lastFileIndex = lastFileIndex;
        this.currentFile = currentFile;
        this.currentFileIndex = currentFileIndex;
        this.behindCount = behindCount;
        this.exist = exist;
    }

    public MappedFile getFirstFile() {
        return firstFile;
    }

    public long getFirstFileIndex() {
        return firstFileIndex;
    }

    public MappedFile getLastFile() {
        return lastFile;
    }

    public long getLastFileIndex() {
        return lastFileIndex;
    }

    public long getCurrentFile() {
        return currentFile;
    }

    public long getCurrentFileIndex() {
        return currentFileIndex;
    }

    public long getBehindCount() {
        return behindCount;
    }

    public boolean isExist() {
        return exist;
    }

    @Override
    public String toString() {
        return "FileQueueSnapshot{" +
                "firstFile=" + firstFile +
                ", firstFileIndex=" + firstFileIndex +
                ", lastFile=" + lastFile +
                ", lastFileIndex=" + lastFileIndex +
                ", currentFile=" + currentFile +
                ", currentFileIndex=" + currentFileIndex +
                ", behindCount=" + behindCount +
                ", exist=" + exist +
                '}';
    }
}
