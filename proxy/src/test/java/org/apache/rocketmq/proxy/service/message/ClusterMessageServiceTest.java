
package org.apache.rocketmq.proxy.service.message;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ReceiptHandle;
import org.apache.rocketmq.common.message.MessageClientIDSetter;
import org.apache.rocketmq.common.protocol.ResponseCode;
import org.apache.rocketmq.common.protocol.header.AckMessageRequestHeader;
import org.apache.rocketmq.proxy.common.ProxyContext;
import org.apache.rocketmq.proxy.common.ProxyException;
import org.apache.rocketmq.proxy.common.ProxyExceptionCode;
import org.apache.rocketmq.proxy.service.mqclient.MQClientAPIFactory;
import org.apache.rocketmq.proxy.service.route.TopicRouteService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClusterMessageServiceTest {

    private TopicRouteService topicRouteService;
    private ClusterMessageService clusterMessageService;

    @Before
    public void before() {
        this.topicRouteService = mock(TopicRouteService.class);
        MQClientAPIFactory mqClientAPIFactory = mock(MQClientAPIFactory.class);
        this.clusterMessageService = new ClusterMessageService(this.topicRouteService, mqClientAPIFactory);
    }

    @Test
    public void testAckMessageByInvalidBrokerNameHandle() throws Exception {
        when(topicRouteService.getBrokerAddr(anyString())).thenThrow(new MQClientException(ResponseCode.TOPIC_NOT_EXIST, ""));
        try {
            this.clusterMessageService.ackMessage(
                ProxyContext.create(),
                ReceiptHandle.builder()
                    .startOffset(0L)
                    .retrieveTime(System.currentTimeMillis())
                    .invisibleTime(3000)
                    .reviveQueueId(1)
                    .topicType(ReceiptHandle.NORMAL_TOPIC)
                    .brokerName("notExistBroker")
                    .queueId(0)
                    .offset(123)
                    .commitLogOffset(0L)
                    .build(),
                MessageClientIDSetter.createUniqID(),
                new AckMessageRequestHeader(),
                3000);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof ProxyException);
            ProxyException proxyException = (ProxyException) e;
            assertEquals(ProxyExceptionCode.INVALID_RECEIPT_HANDLE, proxyException.getCode());
        }
    }
}