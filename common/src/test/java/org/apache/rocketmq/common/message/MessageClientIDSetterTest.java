

package org.apache.rocketmq.common.message;

import org.apache.rocketmq.common.UtilAll;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageClientIDSetterTest {

    @Test
    public void testGetTimeFromID() {
        long t = System.currentTimeMillis();
        String uniqID = MessageClientIDSetter.createUniqID();
        long t2 = MessageClientIDSetter.getNearlyTimeFromID(uniqID).getTime();
        assertThat(t2 - t < 20).isTrue();
    }

    @Test
    public void testGetCountFromID() {
        String uniqID = MessageClientIDSetter.createUniqID();
        String uniqID2 = MessageClientIDSetter.createUniqID();
        String idHex = uniqID.substring(uniqID.length() - 4);
        String idHex2 = uniqID2.substring(uniqID2.length() - 4);
        int s1 = Integer.parseInt(idHex, 16);
        int s2 = Integer.parseInt(idHex2, 16);
        assertThat(s1 == s2 - 1).isTrue();
    }


    @Test
    public void testGetIPStrFromID() {
        byte[] ip = UtilAll.getIP();
        String ipStr = (4 == ip.length) ? UtilAll.ipToIPv4Str(ip) : UtilAll.ipToIPv6Str(ip);

        String uniqID = MessageClientIDSetter.createUniqID();
        String ipStrFromID = MessageClientIDSetter.getIPStrFromID(uniqID);

        assertThat(ipStr).isEqualTo(ipStrFromID);
    }


    @Test
    public void testGetPidFromID() {
        // Temporary fix on MacOS
        short pid = (short) UtilAll.getPid();

        String uniqID = MessageClientIDSetter.createUniqID();
        short pidFromID = (short) MessageClientIDSetter.getPidFromID(uniqID);

        assertThat(pid).isEqualTo(pidFromID);
    }
}
