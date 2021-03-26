package cc.lzhong.scalez.service;

import cc.lzhong.scalez.util.redis.RedisKeyPrefix;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    private final JedisPool jedisPool;

    public RedisService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public <T> T get(RedisKeyPrefix prefix, String key, Class<T> valueClass) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String indexKey = prefix.getPrefix() + key;
            String value = jedis.get(indexKey);

            return convertStringToValue(value, valueClass);
        } finally {
            releaseJedisToPool(jedis);
        }
    }

    public <T> Boolean set(RedisKeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String val = convertValueToString(value);
            if (val == null || val.length() <= 0) {
                return false;
            }

            String indexKey = prefix.getPrefix() + key;
            int timeToExpire = prefix.timeUntilExpiration();
            if (timeToExpire <= 0) {
                jedis.set(indexKey, val);
            } else {
                jedis.setex(indexKey, prefix.timeUntilExpiration(), val);
            }

            return true;
        } finally {
            releaseJedisToPool(jedis);
        }
    }

    public <T> Boolean exists(RedisKeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String fullKey = prefix.getPrefix() + key;

            return jedis.exists(fullKey);
        } finally {
            releaseJedisToPool(jedis);
        }
    }

    public <T> Long increment(RedisKeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String fullKey = prefix.getPrefix() + key;

            return jedis.incr(fullKey);
        } finally {
            releaseJedisToPool(jedis);
        }
    }

    public <T> Long decrement(RedisKeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String fullKey = prefix.getPrefix() + key;

            return jedis.decr(fullKey);
        } finally {
            releaseJedisToPool(jedis);
        }
    }

    private <T> T convertStringToValue(String value, Class<T> valueClass) {
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

    private <T> String convertValueToString(T value) {
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

    private void releaseJedisToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}