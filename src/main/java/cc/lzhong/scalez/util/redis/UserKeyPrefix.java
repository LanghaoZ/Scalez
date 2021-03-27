package cc.lzhong.scalez.util.redis;

public class UserKeyPrefix extends GenericKeyPrefix {

    public static final int TOKEN_EXPIRE_TIME = 2000;

    public UserKeyPrefix(int expireTime, String keyPrefix) {
        super(expireTime, keyPrefix);
    }

    public UserKeyPrefix(String keyPrefix) {
        super(keyPrefix);
    }

    public static UserKeyPrefix byId = new UserKeyPrefix("id");
    public static UserKeyPrefix byToken = new UserKeyPrefix(TOKEN_EXPIRE_TIME, "tk");
}
