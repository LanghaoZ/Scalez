package cc.lzhong.scalez.util.redis;

public class ProductKeyPrefix extends GenericKeyPrefix {

    public ProductKeyPrefix(int expireTime, String keyPrefix) {
        super(expireTime, keyPrefix);
    }

    public ProductKeyPrefix(String keyPrefix) {
        super(keyPrefix);
    }

    public static ProductKeyPrefix webView = new ProductKeyPrefix(60, "vi");
    public static ProductKeyPrefix quantity = new ProductKeyPrefix(0, "cnt");
}
