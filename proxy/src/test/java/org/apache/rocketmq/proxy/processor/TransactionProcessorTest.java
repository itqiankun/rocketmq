

package org.apache.rocketmq.proxy.processor;

import java.util.concurrent.CompletableFuture;
import org.apache.rocketmq.common.protocol.header.EndTransactionRequestHeader;
import org.apache.rocketmq.common.sysflag.MessageSysFlag;
import org.apache.rocketmq.proxy.service.transaction.EndTransactionRequestData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class TransactionProcessorTest extends BaseProcessorTest {

    private static final String PRODUCER_GROUP = "producerGroup";
    private TransactionProcessor transactionProcessor;

    @Before
    public void before() throws Throwable {
        super.before();
        this.transactionProcessor = new TransactionProcessor(this.messagingProcessor, this.serviceManager);
    }

    @Test
    public void testEndTransaction() throws Throwable {
        testEndTransaction(MessageSysFlag.TRANSACTION_COMMIT_TYPE, TransactionStatus.COMMIT);
        testEndTransaction(MessageSysFlag.TRANSACTION_NOT_TYPE, TransactionStatus.UNKNOWN);
        testEndTransaction(MessageSysFlag.TRANSACTION_ROLLBACK_TYPE, TransactionStatus.ROLLBACK);
    }

    protected void testEndTransaction(int sysFlag, TransactionStatus transactionStatus) throws Throwable {
        when(this.messageService.endTransactionOneway(any(), any(), any(), anyLong())).thenReturn(CompletableFuture.completedFuture(null));
        ArgumentCaptor<Integer> commitOrRollbackCaptor = ArgumentCaptor.forClass(Integer.class);
        when(transactionService.genEndTransactionRequestHeader(anyString(), commitOrRollbackCaptor.capture(), anyBoolean(), anyString(), anyString()))
            .thenReturn(new EndTransactionRequestData("brokerName", new EndTransactionRequestHeader()));

        this.transactionProcessor.endTransaction(
            createContext(),
            "transactionId",
            "msgId",
            PRODUCER_GROUP,
            transactionStatus,
            true,
            3000
        );

        assertEquals(sysFlag, commitOrRollbackCaptor.getValue().intValue());

        reset(this.messageService);
    }
}