
package org.apache.rocketmq.common.namesrv;

public interface NameServerUpdateCallback {
    String onNameServerAddressChange(String namesrvAddress);
}
