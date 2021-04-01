package cc.lzhong.scalez.util.redis;

public class OrderKeyPrefix extends GenericKeyPrefix {

    public OrderKeyPrefix(int expireTime, String keyPrefix) {
        super(expireTime, keyPrefix);
    }

    public OrderKeyPrefix(String keyPrefix) {
        super(keyPrefix);
    }

    public static OrderKeyPrefix uuid = new OrderKeyPrefix(180, "uuid");

}
