package cc.lzhong.scalez.util.common;

import com.alibaba.fastjson.JSON;

public class StringValueConverter {

    public static <T> T convertStringToValue(String value, Class<T> valueClass) {
        if (value == null || value.length() <= 0 ||
                valueClass == null) {
            return null;
        } else if (valueClass == int.class || valueClass == Integer.class) {
            return (T)Integer.valueOf(value);
        } else if (valueClass == long.class || valueClass == Long.class) {
            return (T)Long.valueOf(value);
        } else if (valueClass == String.class) {
            return (T)value;
        } else {
            return JSON.toJavaObject(JSON.parseObject(value), valueClass);
        }
    }

    public static <T> String convertValueToString(T value) {
        if (value == null) {
            return null;
        }

        Class<?> valueClass = value.getClass();
        if (valueClass == int.class || valueClass == Integer.class) {
            return value.toString();
        } else if (valueClass == long.class || valueClass == Long.class) {
            return value.toString();
        } else if (valueClass == String.class) {
            return (String)value;
        } else {
            return JSON.toJSONString(value);
        }
    }
}
