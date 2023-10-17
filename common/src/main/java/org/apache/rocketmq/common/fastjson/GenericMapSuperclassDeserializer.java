

package org.apache.rocketmq.common.fastjson;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.MapDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * workaround https://github.com/alibaba/fastjson/issues/3730
 */
public class GenericMapSuperclassDeserializer implements ObjectDeserializer {
    public static final GenericMapSuperclassDeserializer INSTANCE = new GenericMapSuperclassDeserializer();

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Class<?> clz = (Class<?>) type;
        Type genericSuperclass = clz.getGenericSuperclass();
        Map map;
        try {
            map = (Map) clz.newInstance();
        } catch (Exception e) {
            throw new JSONException("unsupport type " + type, e);
        }
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type keyType = parameterizedType.getActualTypeArguments()[0];
        Type valueType = parameterizedType.getActualTypeArguments()[1];
        if (String.class == keyType) {
            return (T) MapDeserializer.parseMap(parser, (Map<String, Object>) map, valueType, fieldName);
        } else {
            return (T) MapDeserializer.parseMap(parser, map, keyType, valueType, fieldName);
        }
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.LBRACE;
    }
}
