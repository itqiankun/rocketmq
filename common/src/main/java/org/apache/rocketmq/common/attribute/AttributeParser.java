
package org.apache.rocketmq.common.attribute;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeParser {
    public static Map<String, String> parseToMap(String attributesModification) {
        if (Strings.isNullOrEmpty(attributesModification)) {
            return new HashMap<>();
        }

        // format: +key1=value1,+key2=value2,-key3,+key4=value4
        Map<String, String> attributes = new HashMap<>();
        String arraySeparator = ",";
        String kvSeparator = "=";
        String[] kvs = attributesModification.split(arraySeparator);
        for (String kv : kvs) {
            String key;
            String value;
            if (kv.contains(kvSeparator)) {
                key = kv.split(kvSeparator)[0];
                value = kv.split(kvSeparator)[1];
                if (!key.contains("+")) {
                    throw new RuntimeException("add/alter attribute format is wrong: " + key);
                }
            } else {
                key = kv;
                value = "";
                if (!key.contains("-")) {
                    throw new RuntimeException("delete attribute format is wrong: " + key);
                }
            }
            String old = attributes.put(key, value);
            if (old != null) {
                throw new RuntimeException("key duplication: " + key);
            }
        }
        return attributes;
    }

    public static String parseToString(Map<String, String> attributes) {
        if (attributes == null || attributes.size() == 0) {
            return "";
        }

        List<String> kvs = new ArrayList<>();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {

            String value = entry.getValue();
            if (Strings.isNullOrEmpty(value)) {
                kvs.add(entry.getKey());
            } else {
                kvs.add(entry.getKey() + "=" + entry.getValue());
            }
        }
        return Joiner.on(",").join(kvs);
    }
}
