package cc.lzhong.scalez.service;

import cc.lzhong.scalez.domain.OrderDetail;
import cc.lzhong.scalez.domain.Product;
import cc.lzhong.scalez.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

    private final ProductService productService;
    private final OrderService orderService;

    public SaleService(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @Transactional
    public OrderDetail sellV1(User user, Product product) {
        productService.decrementCount(product);
        return orderService.createOrder(user, product);
    }
}
