

package org.apache.rocketmq.store;

/**
 * Dispatcher of commit log.
 */
public interface CommitLogDispatcher {

    /**
     *  Dispatch messages from store to build consume queues, indexes, and filter data
     * @param request dispatch message request
     */
    void dispatch(final DispatchRequest request);
}
