package cc.lzhong.scalez.util.queue.message;

import cc.lzhong.scalez.domain.User;

public class SaleMessage {

    private User user;
    private Long productId;
    private String uuid;

    public SaleMessage() {}

    public SaleMessage(User user, Long productId, String uuid) {
        this.user = user;
        this.productId = productId;
        this.uuid = uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
