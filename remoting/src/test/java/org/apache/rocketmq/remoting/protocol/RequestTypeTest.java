

package org.apache.rocketmq.remoting.protocol;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestTypeTest {
    @Test
    public void testValueOf() {
        RequestType requestType = RequestType.valueOf(RequestType.STREAM.getCode());
        assertThat(requestType).isEqualTo(RequestType.STREAM);

        requestType = RequestType.valueOf((byte) 1);
        assertThat(requestType).isNull();
    }
}