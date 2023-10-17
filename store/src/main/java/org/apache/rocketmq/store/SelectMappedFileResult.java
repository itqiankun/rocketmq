

package org.apache.rocketmq.store;

import org.apache.rocketmq.store.logfile.MappedFile;

public class SelectMappedFileResult {

    protected int size;

    protected MappedFile mappedFile;

    public SelectMappedFileResult(int size, MappedFile mappedFile) {
        this.size = size;
        this.mappedFile = mappedFile;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public MappedFile getMappedFile() {
        return mappedFile;
    }
}
