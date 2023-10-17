
package org.apache.rocketmq.common.rpc;

import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.RequestCode;
import org.apache.rocketmq.common.protocol.header.PullMessageRequestHeader;

import java.util.HashMap;
import java.util.Map;

public class RequestBuilder {

    private static Map<Integer, Class> requestCodeMap = new HashMap<Integer, Class>();
    static {
        requestCodeMap.put(RequestCode.PULL_MESSAGE, PullMessageRequestHeader.class);
    }

    public static RpcRequestHeader buildCommonRpcHeader(int requestCode, String destBrokerName) {
        return buildCommonRpcHeader(requestCode, null, destBrokerName);
    }

    public static RpcRequestHeader buildCommonRpcHeader(int requestCode, Boolean oneway, String destBrokerName) {
        Class requestHeaderClass = requestCodeMap.get(requestCode);
        if (requestHeaderClass == null) {
            throw new UnsupportedOperationException("unknown " + requestCode);
        }
        try {
            RpcRequestHeader requestHeader = (RpcRequestHeader) requestHeaderClass.newInstance();
            requestHeader.setOway(oneway);
            requestHeader.setBname(destBrokerName);
            return requestHeader;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static TopicQueueRequestHeader buildTopicQueueRequestHeader(int requestCode, MessageQueue mq) {
        return buildTopicQueueRequestHeader(requestCode, null, mq.getBrokerName(), mq.getTopic(), mq.getQueueId(), null);
    }

    public static TopicQueueRequestHeader buildTopicQueueRequestHeader(int requestCode, MessageQueue mq, Boolean logic) {
        return buildTopicQueueRequestHeader(requestCode, null, mq.getBrokerName(), mq.getTopic(), mq.getQueueId(), logic);
    }

    public static TopicQueueRequestHeader buildTopicQueueRequestHeader(int requestCode, Boolean oneway, MessageQueue mq, Boolean logic) {
        return buildTopicQueueRequestHeader(requestCode, oneway, mq.getBrokerName(), mq.getTopic(), mq.getQueueId(), logic);
    }

    public static TopicQueueRequestHeader buildTopicQueueRequestHeader(int requestCode,  Boolean oneway, String destBrokerName, String topic, int queueId, Boolean logic) {
        Class requestHeaderClass = requestCodeMap.get(requestCode);
        if (requestHeaderClass == null) {
            throw new UnsupportedOperationException("unknown " + requestCode);
        }
        try {
            TopicQueueRequestHeader requestHeader = (TopicQueueRequestHeader) requestHeaderClass.newInstance();
            requestHeader.setOway(oneway);
            requestHeader.setBname(destBrokerName);
            requestHeader.setTopic(topic);
            requestHeader.setQueueId(queueId);
            requestHeader.setLo(logic);
            return requestHeader;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
