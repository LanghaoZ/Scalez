package cc.lzhong.scalez.vo;

import cc.lzhong.scalez.domain.OrderDetail;
import cc.lzhong.scalez.domain.Product;

public class OrderDetailVo {

    private Product product;
    private OrderDetail orderDetail;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }
}
