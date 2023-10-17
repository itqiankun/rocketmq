
package org.apache.rocketmq.proxy.processor;

import java.util.concurrent.CompletableFuture;
import org.apache.rocketmq.common.sysflag.MessageSysFlag;
import org.apache.rocketmq.proxy.common.ProxyContext;
import org.apache.rocketmq.proxy.common.ProxyException;
import org.apache.rocketmq.proxy.common.ProxyExceptionCode;
import org.apache.rocketmq.proxy.service.ServiceManager;
import org.apache.rocketmq.proxy.service.transaction.EndTransactionRequestData;

public class TransactionProcessor extends AbstractProcessor {

    public TransactionProcessor(MessagingProcessor messagingProcessor,
        ServiceManager serviceManager) {
        super(messagingProcessor, serviceManager);
    }

    public CompletableFuture<Void> endTransaction(ProxyContext ctx, String transactionId, String messageId, String producerGroup,
        TransactionStatus transactionStatus, boolean fromTransactionCheck, long timeoutMillis) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        try {
            EndTransactionRequestData headerData = serviceManager.getTransactionService().genEndTransactionRequestHeader(
                producerGroup,
                buildCommitOrRollback(transactionStatus),
                fromTransactionCheck,
                messageId,
                transactionId
            );
            if (headerData == null) {
                future.completeExceptionally(new ProxyException(ProxyExceptionCode.TRANSACTION_DATA_NOT_FOUND, "cannot found transaction data"));
                return future;
            }
            return this.serviceManager.getMessageService().endTransactionOneway(
                ctx,
                headerData.getBrokerName(),
                headerData.getRequestHeader(),
                timeoutMillis
            );
        } catch (Throwable t) {
            future.completeExceptionally(t);
        }
        return future;
    }

    protected int buildCommitOrRollback(TransactionStatus transactionStatus) {
        switch (transactionStatus) {
            case COMMIT:
                return MessageSysFlag.TRANSACTION_COMMIT_TYPE;
            case ROLLBACK:
                return MessageSysFlag.TRANSACTION_ROLLBACK_TYPE;
            default:
                return MessageSysFlag.TRANSACTION_NOT_TYPE;
        }
    }

    public void addTransactionSubscription(ProxyContext ctx, String producerGroup, String topic) {
        this.serviceManager.getTransactionService().addTransactionSubscription(producerGroup, topic);
    }
}
