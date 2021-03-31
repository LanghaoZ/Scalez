package cc.lzhong.scalez.service;

import cc.lzhong.scalez.util.common.StringValueConverter;
import cc.lzhong.scalez.util.redis.RedisKeyPrefix;
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

            return StringValueConverter.convertStringToValue(value, valueClass);
        } finally {
            releaseJedisToPool(jedis);
        }
    }

    public <T> boolean set(RedisKeyPrefix prefix, String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String val = StringValueConverter.convertValueToString(value);
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

    public boolean delete(RedisKeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String indexKey = prefix.getPrefix() + key;
            long ret = jedis.del(indexKey);

            return ret > 0;
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

    private void releaseJedisToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

}