

package org.apache.rocketmq.proxy.common.utils;

import org.apache.rocketmq.common.filter.FilterAPI;
import org.apache.rocketmq.common.protocol.heartbeat.SubscriptionData;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FilterUtilTest {
    @Test
    public void testTagMatched() throws Exception {
        SubscriptionData subscriptionData = FilterAPI.buildSubscriptionData("", "tagA");
        assertThat(FilterUtils.isTagMatched(subscriptionData.getTagsSet(), "tagA")).isTrue();
    }

    @Test
    public void testTagNotMatched() throws Exception {
        SubscriptionData subscriptionData = FilterAPI.buildSubscriptionData("", "tagA");
        assertThat(FilterUtils.isTagMatched(subscriptionData.getTagsSet(), "tagB")).isFalse();
    }

    @Test
    public void testTagMatchedStar() throws Exception {
        SubscriptionData subscriptionData = FilterAPI.buildSubscriptionData("", "*");
        assertThat(FilterUtils.isTagMatched(subscriptionData.getTagsSet(), "tagA")).isTrue();
    }

    @Test
    public void testTagNotMatchedNull() throws Exception {
        SubscriptionData subscriptionData = FilterAPI.buildSubscriptionData("", "tagA");
        assertThat(FilterUtils.isTagMatched(subscriptionData.getTagsSet(), null)).isFalse();
    }

} 
