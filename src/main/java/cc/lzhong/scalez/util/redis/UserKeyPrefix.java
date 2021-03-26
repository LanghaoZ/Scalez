package cc.lzhong.scalez.util.redis;

public class UserKeyPrefix extends GenericKeyPrefix {

    public UserKeyPrefix(int expireTime, String keyPrefix) {
        super(expireTime, keyPrefix);
    }

    public UserKeyPrefix(String keyPrefix) {
        super(keyPrefix);
    }

    public static UserKeyPrefix getPrefixById = new UserKeyPrefix("id");
}
