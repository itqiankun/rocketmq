

package org.apache.rocketmq.store;

import org.apache.rocketmq.store.CommitLog.GroupCommitRequest;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class FlushDiskWatcherTest {

    private final long timeoutMill = 5000;

    @Test
    public void testTimeout() throws Exception {
        FlushDiskWatcher flushDiskWatcher = new FlushDiskWatcher();
        flushDiskWatcher.setDaemon(true);
        flushDiskWatcher.start();

        int count = 100;
        List<GroupCommitRequest> requestList = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            GroupCommitRequest groupCommitRequest =
                    new GroupCommitRequest(0, timeoutMill);
            requestList.add(groupCommitRequest);
            flushDiskWatcher.add(groupCommitRequest);
        }
        Thread.sleep(2 * timeoutMill);

        for (GroupCommitRequest request : requestList) {
            request.wakeupCustomer(PutMessageStatus.PUT_OK);
        }

        for (GroupCommitRequest request : requestList) {
            Assert.assertTrue(request.future().isDone());
            Assert.assertEquals(request.future().get(), PutMessageStatus.FLUSH_DISK_TIMEOUT);
        }
        Assert.assertEquals(flushDiskWatcher.queueSize(), 0);
        flushDiskWatcher.shutdown();
    }

    @Test
    public void testWatcher() throws Exception {
        FlushDiskWatcher flushDiskWatcher = new FlushDiskWatcher();
        flushDiskWatcher.setDaemon(true);
        flushDiskWatcher.start();

        int count = 100;
        List<GroupCommitRequest> requestList = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            GroupCommitRequest groupCommitRequest =
                    new GroupCommitRequest(0, timeoutMill);
            requestList.add(groupCommitRequest);
            flushDiskWatcher.add(groupCommitRequest);
            groupCommitRequest.wakeupCustomer(PutMessageStatus.PUT_OK);
        }
        Thread.sleep((timeoutMill << 20) / 1000000);
        for (GroupCommitRequest request : requestList) {
            Assert.assertTrue(request.future().isDone());
            Assert.assertEquals(request.future().get(), PutMessageStatus.PUT_OK);
        }
        Assert.assertEquals(flushDiskWatcher.queueSize(), 0);
        flushDiskWatcher.shutdown();
    }


}
