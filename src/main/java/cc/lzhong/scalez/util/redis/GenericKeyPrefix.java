package cc.lzhong.scalez.util.redis;

public abstract class GenericKeyPrefix implements RedisKeyPrefix {

    private int expireTime;
    private String keyPrefix;

    public GenericKeyPrefix(int expireTime, String keyPrefix) {
        this.expireTime = expireTime;
        this.keyPrefix = keyPrefix;
    }

    public GenericKeyPrefix(String keyPrefix) {
        this(0, keyPrefix);
    }

    public int timeUntilExpiration() {
        return expireTime;
    }

    public String getPrefix() {
        String classPrefix = getClass().getSimpleName();
        return classPrefix + "-" + keyPrefix;
    }

}
