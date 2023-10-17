
package org.apache.rocketmq.proxy.service.route;

import org.apache.rocketmq.client.common.ClientErrorCode;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.protocol.ResponseCode;

public class TopicRouteHelper {

    public static boolean isTopicNotExistError(Throwable e) {
        if (e instanceof MQBrokerException) {
            if (((MQBrokerException) e).getResponseCode() == ResponseCode.TOPIC_NOT_EXIST) {
                return true;
            }
        }

        if (e instanceof MQClientException) {
            int code = ((MQClientException) e).getResponseCode();
            if (code == ResponseCode.TOPIC_NOT_EXIST || code == ClientErrorCode.NOT_FOUND_TOPIC_EXCEPTION) {
                return true;
            }

            Throwable cause = e.getCause();
            if (cause instanceof MQClientException) {
                int causeCode = ((MQClientException) cause).getResponseCode();
                return causeCode == ResponseCode.TOPIC_NOT_EXIST || causeCode == ClientErrorCode.NOT_FOUND_TOPIC_EXCEPTION;
            }
        }

        return false;
    }
}
