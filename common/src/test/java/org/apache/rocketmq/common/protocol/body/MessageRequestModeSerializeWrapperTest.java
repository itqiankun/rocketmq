
package org.apache.rocketmq.common.protocol.body;

import org.apache.rocketmq.common.message.MessageRequestMode;
import org.apache.rocketmq.remoting.protocol.RemotingSerializable;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageRequestModeSerializeWrapperTest {

    @Test
    public void testFromJson(){
        MessageRequestModeSerializeWrapper  messageRequestModeSerializeWrapper = new MessageRequestModeSerializeWrapper();
        ConcurrentHashMap<String, ConcurrentHashMap<String, SetMessageRequestModeRequestBody>>
                messageRequestModeMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, SetMessageRequestModeRequestBody>>();
        String topic = "TopicTest";
        String group = "Consumer";
        MessageRequestMode requestMode = MessageRequestMode.POP;
        int popShareQueueNum = -1;
        SetMessageRequestModeRequestBody requestBody = new SetMessageRequestModeRequestBody();
        requestBody.setTopic(topic);
        requestBody.setConsumerGroup(group);
        requestBody.setMode(requestMode);
        requestBody.setPopShareQueueNum(popShareQueueNum);
        ConcurrentHashMap<String, SetMessageRequestModeRequestBody> map = new ConcurrentHashMap<>();
        map.put(group, requestBody);
        messageRequestModeMap.put(topic, map);

        messageRequestModeSerializeWrapper.setMessageRequestModeMap(messageRequestModeMap);

        String json = RemotingSerializable.toJson(messageRequestModeSerializeWrapper, true);
        MessageRequestModeSerializeWrapper fromJson = RemotingSerializable.fromJson(json, MessageRequestModeSerializeWrapper.class);
        assertThat(fromJson.getMessageRequestModeMap()).containsKey(topic);
        assertThat(fromJson.getMessageRequestModeMap().get(topic)).containsKey(group);
        assertThat(fromJson.getMessageRequestModeMap().get(topic).get(group).getTopic()).isEqualTo(topic);
        assertThat(fromJson.getMessageRequestModeMap().get(topic).get(group).getConsumerGroup()).isEqualTo(group);
        assertThat(fromJson.getMessageRequestModeMap().get(topic).get(group).getMode()).isEqualTo(requestMode);
        assertThat(fromJson.getMessageRequestModeMap().get(topic).get(group).getPopShareQueueNum()).isEqualTo(popShareQueueNum);
    }
}
