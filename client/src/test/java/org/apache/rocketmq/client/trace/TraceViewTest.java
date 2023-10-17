

package org.apache.rocketmq.client.trace;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageType;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class TraceViewTest {

    @Test
    public void testDecodeFromTraceTransData() {
        String messageBody = new StringBuilder()
            .append("Pub").append(TraceConstants.CONTENT_SPLITOR)
            .append(System.currentTimeMillis()).append(TraceConstants.CONTENT_SPLITOR)
            .append("DefaultRegion").append(TraceConstants.CONTENT_SPLITOR)
            .append("PID-test").append(TraceConstants.CONTENT_SPLITOR)
            .append("topic-test").append(TraceConstants.CONTENT_SPLITOR)
            .append("AC1415116D1418B4AAC217FE1B4E0000").append(TraceConstants.CONTENT_SPLITOR)
            .append("Tags").append(TraceConstants.CONTENT_SPLITOR)
            .append("Keys").append(TraceConstants.CONTENT_SPLITOR)
            .append("127.0.0.1:10911").append(TraceConstants.CONTENT_SPLITOR)
            .append(26).append(TraceConstants.CONTENT_SPLITOR)
            .append(245).append(TraceConstants.CONTENT_SPLITOR)
            .append(MessageType.Normal_Msg.ordinal()).append(TraceConstants.CONTENT_SPLITOR)
            .append("0A9A002600002A9F0000000000002329").append(TraceConstants.CONTENT_SPLITOR)
            .append(true).append(TraceConstants.FIELD_SPLITOR)
            .toString();
        MessageExt message = new MessageExt();
        message.setBody(messageBody.getBytes(StandardCharsets.UTF_8));
        String key = "AC1415116D1418B4AAC217FE1B4E0000";
        List<TraceView> traceViews = TraceView.decodeFromTraceTransData(key, message);
        Assert.assertEquals(traceViews.size(), 1);
        Assert.assertEquals(traceViews.get(0).getMsgId(), key);

        key = "AD4233434334AAC217FEFFD0000";
        traceViews = TraceView.decodeFromTraceTransData(key, message);
        Assert.assertEquals(traceViews.size(), 0);
    }
}
