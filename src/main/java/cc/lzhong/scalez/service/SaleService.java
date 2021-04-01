package cc.lzhong.scalez.service;

import cc.lzhong.scalez.domain.OrderDetail;
import cc.lzhong.scalez.domain.Product;
import cc.lzhong.scalez.domain.User;
import cc.lzhong.scalez.util.redis.OrderKeyPrefix;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

    private final ProductService productService;
    private final OrderService orderService;
    private final RedisService redisService;

    public SaleService(ProductService productService, OrderService orderService, RedisService redisService) {
        this.productService = productService;
        this.orderService = orderService;
        this.redisService = redisService;
    }

    @Transactional
    public OrderDetail sellV1(User user, Product product) {
        productService.decrementCount(product);
        return orderService.createOrder(user, product);
    }

    @Transactional
    public OrderDetail sellV2Safe(User user, Product product) {
        boolean success = productService.decrementCountSafe(product);
        if (!success) {
            return null;
        }
        return orderService.createOrder(user, product);
    }

    @Transactional
    public OrderDetail sellV3Safe(User user, Product product, String uuid) {
        boolean success = productService.decrementCountSafe(product);
        if (!success) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(0L);
            orderDetail.setProductName("");
            redisService.set(OrderKeyPrefix.uuid, uuid, orderDetail);
            return null;
        }
        return orderService.createOrderV3(user, product, uuid);
    }
}
