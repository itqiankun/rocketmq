

package org.apache.rocketmq.common.subscription;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupRetryPolicyTest {

    @Test
    public void testGetRetryPolicy() {
        GroupRetryPolicy groupRetryPolicy = new GroupRetryPolicy();
        RetryPolicy retryPolicy = groupRetryPolicy.getRetryPolicy();
        assertThat(retryPolicy).isInstanceOf(CustomizedRetryPolicy.class);
        groupRetryPolicy.setType(GroupRetryPolicyType.EXPONENTIAL);
        retryPolicy = groupRetryPolicy.getRetryPolicy();
        assertThat(retryPolicy).isInstanceOf(CustomizedRetryPolicy.class);

        groupRetryPolicy.setType(GroupRetryPolicyType.CUSTOMIZED);
        groupRetryPolicy.setCustomizedRetryPolicy(new CustomizedRetryPolicy());
        retryPolicy = groupRetryPolicy.getRetryPolicy();
        assertThat(retryPolicy).isInstanceOf(CustomizedRetryPolicy.class);

        groupRetryPolicy.setType(GroupRetryPolicyType.EXPONENTIAL);
        groupRetryPolicy.setExponentialRetryPolicy(new ExponentialRetryPolicy());
        retryPolicy = groupRetryPolicy.getRetryPolicy();
        assertThat(retryPolicy).isInstanceOf(ExponentialRetryPolicy.class);

        groupRetryPolicy.setType(null);
        retryPolicy = groupRetryPolicy.getRetryPolicy();
        assertThat(retryPolicy).isInstanceOf(CustomizedRetryPolicy.class);
    }
}