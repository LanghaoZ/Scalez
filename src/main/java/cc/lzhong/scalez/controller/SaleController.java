package cc.lzhong.scalez.controller;

import cc.lzhong.scalez.domain.Order;
import cc.lzhong.scalez.domain.OrderDetail;
import cc.lzhong.scalez.domain.Product;
import cc.lzhong.scalez.domain.User;
import cc.lzhong.scalez.service.*;
import cc.lzhong.scalez.util.response.AppMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/sell")
public class SaleController {

    private final RedisService redisService;
    private final ProductService productService;
    private final OrderService orderService;
    private final SaleService saleService;

    public SaleController(RedisService redisService, ProductService productService, OrderService orderService, SaleService saleService) {
        this.redisService = redisService;
        this.productService = productService;
        this.orderService = orderService;
        this.saleService = saleService;
    }

    @PostMapping("/v1")
    public String sellProductV1(Model model, User user, @RequestParam("productId") Long productId) {
        if (user == null) {
            return "auth/login";
        }

        Product product = productService.getProductById(productId);
        int count = product.getCount();
        if (count <= 0) {
            model.addAttribute("message", AppMessage.INSUFFICIENT_QUANTITY.getMessage());
            return "order/failure";
        }

//        Order order = orderService.getOrderByUserAndProduct(user.getId(), productId);
//        if (order != null) {
//            model.addAttribute("message", AppMessage.ONLY_ONE_PER_USER.getMessage());
//            return "order/failure";
//        }

        OrderDetail orderDetail = saleService.sellV1(user, product);
        model.addAttribute("orderDetail", orderDetail);
        model.addAttribute("product", product);

        return "order/detail";
    }
}
