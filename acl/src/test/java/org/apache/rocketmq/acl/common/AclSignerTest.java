
package org.apache.rocketmq.acl.common;

import org.junit.Assert;
import org.junit.Test;

public class AclSignerTest {

    @Test(expected = Exception.class)
    public void calSignatureExceptionTest(){
        AclSigner.calSignature(new byte[]{},"");
    }

    @Test
    public void calSignatureTest(){
        String expectedSignature = "IUc8rrO/0gDch8CjObLQsW2rsiA=";
        Assert.assertEquals(expectedSignature, AclSigner.calSignature("RocketMQ", "12345678"));
        Assert.assertEquals(expectedSignature, AclSigner.calSignature("RocketMQ".getBytes(), "12345678"));
    }

}
