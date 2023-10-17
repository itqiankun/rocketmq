

package org.apache.rocketmq.store.ha.io;

public interface HAReadHook {
    void afterRead(int readSize);
}
