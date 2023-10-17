

package org.apache.rocketmq.common.utils;

import java.util.concurrent.ConcurrentHashMap;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConcurrentHashMapUtilsTest {

    @Test
    public void computeIfAbsent() {

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("123", "1111");
        String value = ConcurrentHashMapUtils.computeIfAbsent(map, "123", k -> "234");
        assertEquals("1111", value);
        String value1 = ConcurrentHashMapUtils.computeIfAbsent(map, "1232", k -> "2342");
        assertEquals("2342", value1);
        String value2 = ConcurrentHashMapUtils.computeIfAbsent(map, "123", k -> "2342");
        assertEquals("1111", value2);
    }
}