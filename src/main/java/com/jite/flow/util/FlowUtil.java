package com.jite.flow.util;

import com.alibaba.fastjson.JSON;
import com.esotericsoftware.kryo.Kryo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * @author Lux Sun
 * @date 2021/10/12
 */
public class FlowUtil {

    public static class UUIDUtil {
        public static String randomUUID() {
            return UUID.randomUUID().toString().replace("-", "");
        }
    }

    public static class StringUtil {

        public static boolean isEmpty(String str) {
            return str == null || str.length() == 0;
        }

        public static boolean isNotEmpty(String str) {
            return !isEmpty(str);
        }
    }

    public static class DateTimeUtil {

        public enum TimeUnitEnum {
            MILLISECOND,
            SECOND,
            MINUTE,
            HOUR,
            DAY
        }

        public static Long toMillisecond(Integer time, Enum timeUnit) {
            if (TimeUnitEnum.MILLISECOND == timeUnit) {
                return Long.valueOf(time);
            } else if (TimeUnitEnum.SECOND == timeUnit) {
                return time * 1000L;
            } else if (TimeUnitEnum.MINUTE == timeUnit) {
                return time * 60 * 1000L;
            } else if (TimeUnitEnum.HOUR == timeUnit) {
                return time * 60 * 60 * 1000L;
            } else if (TimeUnitEnum.DAY == timeUnit) {
                return time * 24 * 60 * 60 * 1000L;
            }
            return -1L;
        }

        public static String now() {
            return LocalDateTime.now().toString().replace("T", " ");
        }
    }

    public static class CollectionUtil {
        public static boolean isNotEmpty(Collection<?> collection) {
            return !isEmpty(collection);
        }

        public static boolean isEmpty(Collection<?> collection) {
            return collection == null || collection.isEmpty();
        }

        public static <K, V> boolean isNotEmpty(Map<K, V> map) {
            return !isEmpty(map);
        }

        public static <K, V> boolean isEmpty(Map<K, V> map) {
            return map == null || map.isEmpty();
        }
    }

    public static class BooleanUtil {
        public static boolean isTrue(Boolean bool) {
            return null == bool ? false : true == bool;
        }

        public static boolean isFalse(Boolean bool) {
            return !isTrue(bool);
        }
    }

    public static class ObjectUtil {
        public static boolean isNull(Object object) {
            return null == object;
        }

        public static boolean nonNull(Object object) {
            return !isNull(object);
        }
    }

    public static class FastjsonUtil {
        public static <T> T clone(T source) {
            String jsonString = JSON.toJSONString(source);
            return (T) JSON.parseObject(jsonString, source.getClass());
        }
    }

    public static class KryoUtil {
        public static final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<>();

        public static <T> T clone(T source) {
            try {
                Kryo kryo = kryoThreadLocal.get();
                if (null == kryo) {
                    kryo = new Kryo();
                    kryo.setRegistrationRequired(false);
                    kryoThreadLocal.set(kryo);
                }
                T target = kryo.copy(source);
                return target;
            } finally {
                kryoThreadLocal.remove();
            }
        }
    }
}