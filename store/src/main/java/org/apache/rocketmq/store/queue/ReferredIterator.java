

package org.apache.rocketmq.store.queue;

import java.util.Iterator;

public interface ReferredIterator<T> extends Iterator<T> {

    /**
     * Release the referred resources.
     */
    void release();

    T nextAndRelease();
}
