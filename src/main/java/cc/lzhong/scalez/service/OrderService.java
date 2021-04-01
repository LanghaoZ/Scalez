package cc.lzhong.scalez.service;

import cc.lzhong.scalez.dao.OrderDao;
import cc.lzhong.scalez.domain.Order;
import cc.lzhong.scalez.domain.OrderDetail;
import cc.lzhong.scalez.domain.Product;
import cc.lzhong.scalez.domain.User;
import cc.lzhong.scalez.util.redis.OrderKeyPrefix;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {

    private final OrderDao orderDao;

    private final RedisService redisService;

    public OrderService(OrderDao orderDao, RedisService redisService) {
        this.orderDao = orderDao;
        this.redisService = redisService;
    }

    public Order getOrderByUserAndProduct(Long userId, Long productId) {
        return orderDao.getOrderByUserAndProduct(userId, productId);
    }

    public OrderDetail getOrderById(Long orderId) {
        return orderDao.getOrderById(orderId);
    }

    @Transactional
    public OrderDetail createOrder(User user, Product product) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setUserId(user.getId());
        orderDetail.setProductId(product.getId());
        orderDetail.setProductName(product.getName());
        orderDetail.setStatus(0);
        orderDetail.setCreateTime(new Date());
        orderDao.insertOrderDetail(orderDetail);

        Order order = new Order();
        order.setOrderId(orderDetail.getId());
        order.setUserId(user.getId());
        order.setProductId(product.getId());
        orderDao.insertOrder(order);

        return orderDetail;
    }

    public OrderDetail createOrderV3(User user, Product product, String uuid) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setUserId(user.getId());
        orderDetail.setProductId(product.getId());
        orderDetail.setProductName(product.getName());
        orderDetail.setStatus(0);
        orderDetail.setCreateTime(new Date());
        orderDao.insertOrderDetail(orderDetail);

        Order order = new Order();
        order.setOrderId(orderDetail.getId());
        order.setUserId(user.getId());
        order.setProductId(product.getId());
        orderDao.insertOrder(order);

        redisService.set(OrderKeyPrefix.uuid, uuid, orderDetail);

        return orderDetail;
    }


}
