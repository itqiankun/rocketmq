
package org.apache.rocketmq.proxy.service.transaction;

import java.util.List;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.proxy.common.ProxyContext;

public interface TransactionService {

    void addTransactionSubscription(String group, List<String> topicList);

    void addTransactionSubscription(String group, String topic);

    void replaceTransactionSubscription(String group, List<String> topicList);

    void unSubscribeAllTransactionTopic(String group);

    TransactionData addTransactionDataByBrokerAddr(String brokerAddr, String producerGroup, long tranStateTableOffset, long commitLogOffset, String transactionId,
        Message message);

    TransactionData addTransactionDataByBrokerName(String brokerName, String producerGroup, long tranStateTableOffset, long commitLogOffset, String transactionId,
        Message message);

    EndTransactionRequestData genEndTransactionRequestHeader(String producerGroup, Integer commitOrRollback,
        boolean fromTransactionCheck, String msgId, String transactionId);

    void onSendCheckTransactionStateFailed(ProxyContext context, String producerGroup, TransactionData transactionData);
}
