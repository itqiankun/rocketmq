
package org.apache.rocketmq.proxy.common;

import com.google.common.net.HostAndPort;
import java.util.Objects;

public class Address {

    public enum AddressScheme {
        IPv4,
        IPv6,
        DOMAIN_NAME,
        UNRECOGNIZED
    }

    private AddressScheme addressScheme;
    private HostAndPort hostAndPort;

    public Address(AddressScheme addressScheme, HostAndPort hostAndPort) {
        this.addressScheme = addressScheme;
        this.hostAndPort = hostAndPort;
    }

    public AddressScheme getAddressScheme() {
        return addressScheme;
    }

    public void setAddressScheme(AddressScheme addressScheme) {
        this.addressScheme = addressScheme;
    }

    public HostAndPort getHostAndPort() {
        return hostAndPort;
    }

    public void setHostAndPort(HostAndPort hostAndPort) {
        this.hostAndPort = hostAndPort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Address address = (Address) o;
        return addressScheme == address.addressScheme && Objects.equals(hostAndPort, address.hostAndPort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressScheme, hostAndPort);
    }
}
