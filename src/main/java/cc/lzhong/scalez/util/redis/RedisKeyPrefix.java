package cc.lzhong.scalez.util.redis;

public interface RedisKeyPrefix {

    public int timeUntilExpiration();
    public String getPrefix();
}
