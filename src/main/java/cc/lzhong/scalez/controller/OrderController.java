package cc.lzhong.scalez.controller;

import cc.lzhong.scalez.domain.OrderDetail;
import cc.lzhong.scalez.domain.Product;
import cc.lzhong.scalez.domain.User;
import cc.lzhong.scalez.service.OrderService;
import cc.lzhong.scalez.service.ProductService;
import cc.lzhong.scalez.service.RedisService;
import cc.lzhong.scalez.util.response.AppMessage;
import cc.lzhong.scalez.util.response.Result;
import cc.lzhong.scalez.vo.OrderDetailVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;
    private final RedisService redisService;
    private final ProductService productService;

    public OrderController(OrderService orderService, RedisService redisService, ProductService productService) {
        this.orderService = orderService;
        this.redisService = redisService;
        this.productService = productService;
    }

    @GetMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> getOrderDetail(@RequestParam("orderId") Long orderId, User user, Model model) {
        if (user == null) {
            return Result.error(AppMessage.SESSION_OVER);
        }

        OrderDetail orderDetail = orderService.getOrderById(orderId);
        if (orderDetail == null) {
            return Result.error(AppMessage.INVALID_ORDER);
        }

        Long productId = orderDetail.getProductId();
        Product product = productService.getProductById(productId);
        if (product == null) {
            return Result.error(AppMessage.PRODUCT_INVALIDATED);
        }

        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrderDetail(orderDetail);
        vo.setProduct(product);

        return Result.success(vo);
    }
}
