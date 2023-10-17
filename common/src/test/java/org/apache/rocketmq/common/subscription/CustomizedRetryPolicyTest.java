

package org.apache.rocketmq.common.subscription;

import java.util.concurrent.TimeUnit;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomizedRetryPolicyTest {

    @Test
    public void testNextDelayDuration() {
        CustomizedRetryPolicy customizedRetryPolicy = new CustomizedRetryPolicy();
        long actual = customizedRetryPolicy.nextDelayDuration(0);
        assertThat(actual).isEqualTo(TimeUnit.SECONDS.toMillis(10));
        actual = customizedRetryPolicy.nextDelayDuration(10);
        assertThat(actual).isEqualTo(TimeUnit.MINUTES.toMillis(9));
    }

    @Test
    public void testNextDelayDurationOutOfRange() {
        CustomizedRetryPolicy customizedRetryPolicy = new CustomizedRetryPolicy();
        long actual = customizedRetryPolicy.nextDelayDuration(-1);
        assertThat(actual).isEqualTo(TimeUnit.SECONDS.toMillis(10));
        actual = customizedRetryPolicy.nextDelayDuration(100);
        assertThat(actual).isEqualTo(TimeUnit.HOURS.toMillis(2));
    }
}