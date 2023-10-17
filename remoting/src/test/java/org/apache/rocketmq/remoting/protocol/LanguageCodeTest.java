
package org.apache.rocketmq.remoting.protocol;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LanguageCodeTest {

    @Test
    public void testLanguageCodeRust() {
        LanguageCode code = LanguageCode.valueOf((byte) 12);
        assertThat(code).isEqualTo(LanguageCode.RUST);

        code = LanguageCode.valueOf("RUST");
        assertThat(code).isEqualTo(LanguageCode.RUST);
    }
    
}
