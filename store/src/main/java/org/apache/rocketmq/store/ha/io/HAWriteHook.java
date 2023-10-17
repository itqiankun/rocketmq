

package org.apache.rocketmq.store.ha.io;

public interface HAWriteHook {
    void afterWrite(int writeSize);
}
