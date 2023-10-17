

package org.apache.rocketmq.common.subscription;

import java.util.concurrent.TimeUnit;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExponentialRetryPolicyTest {

    @Test
    public void testNextDelayDuration() {
        ExponentialRetryPolicy exponentialRetryPolicy = new ExponentialRetryPolicy();
        long actual = exponentialRetryPolicy.nextDelayDuration(0);
        assertThat(actual).isEqualTo(TimeUnit.SECONDS.toMillis(5));
        actual = exponentialRetryPolicy.nextDelayDuration(10);
        assertThat(actual).isEqualTo(TimeUnit.SECONDS.toMillis(1024 * 5));
    }

    @Test
    public void testNextDelayDurationOutOfRange() {
        ExponentialRetryPolicy exponentialRetryPolicy = new ExponentialRetryPolicy();
        long actual = exponentialRetryPolicy.nextDelayDuration(-1);
        assertThat(actual).isEqualTo(TimeUnit.SECONDS.toMillis(5));
        actual = exponentialRetryPolicy.nextDelayDuration(100);
        assertThat(actual).isEqualTo(TimeUnit.HOURS.toMillis(2));
    }
}