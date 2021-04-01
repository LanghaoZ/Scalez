package cc.lzhong.scalez.controller;

import cc.lzhong.scalez.domain.Order;
import cc.lzhong.scalez.domain.OrderDetail;
import cc.lzhong.scalez.domain.Product;
import cc.lzhong.scalez.domain.User;
import cc.lzhong.scalez.service.*;
import cc.lzhong.scalez.util.queue.Sender;
import cc.lzhong.scalez.util.queue.message.SaleMessage;
import cc.lzhong.scalez.util.redis.OrderKeyPrefix;
import cc.lzhong.scalez.util.redis.ProductKeyPrefix;
import cc.lzhong.scalez.util.response.AppMessage;
import cc.lzhong.scalez.util.response.Result;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/sell")
public class SaleController implements InitializingBean {

    private final RedisService redisService;
    private final ProductService productService;
    private final OrderService orderService;
    private final SaleService saleService;
    private final Sender sender;
    private Map<Long, Boolean> localCache = new HashMap<>();

    public SaleController(RedisService redisService, ProductService productService,
                          OrderService orderService, SaleService saleService, Sender sender) {
        this.redisService = redisService;
        this.productService = productService;
        this.orderService = orderService;
        this.saleService = saleService;
        this.sender = sender;
    }

    public void afterPropertiesSet() throws Exception {
        List<Product> products =  productService.getAllProducts();
        if (products == null || products.size() <= 0) {
            return ;
        }

        for (Product product : products) {
            redisService.set(ProductKeyPrefix.quantity, product.getId().toString(), product.getCount());
            localCache.put(product.getId(), false);
        }
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

        OrderDetail orderDetail = saleService.sellV1(user, product);
        model.addAttribute("orderDetail", orderDetail);
        model.addAttribute("product", product);

        return "order/detail";
    }

    @PostMapping("/v2")
    @ResponseBody
    public Result<OrderDetail> sellProductV2(Model model, User user, @RequestParam("productId") Long productId) {
        if (user == null) {
            return Result.error(AppMessage.SESSION_OVER);
        }

        Product product = productService.getProductById(productId);
        int count = product.getCount();
        if (count <= 0) {
            return Result.error(AppMessage.INSUFFICIENT_QUANTITY);
        }

        OrderDetail orderDetail = saleService.sellV1(user, product);

        return Result.success(orderDetail);
    }

    @PostMapping("/v3")
    @ResponseBody
    public Result<String> sellProductV3(User user, @RequestParam("productId") Long productId) {
        if (user == null) {
            return Result.error(AppMessage.SESSION_OVER);
        }

        boolean isSoldOut = localCache.get(productId);
        if (isSoldOut) {
            return Result.error(AppMessage.INSUFFICIENT_QUANTITY);
        }

        Long quantityLeft = redisService.decrement(ProductKeyPrefix.quantity, productId.toString());
        if (quantityLeft < 0) {
            localCache.put(productId, true);
            return Result.error(AppMessage.INSUFFICIENT_QUANTITY);
        }

        String uuid = UUID.randomUUID().toString();
        SaleMessage message = new SaleMessage(user, productId, uuid);
        sender.sendSaleMessage(message);

        return Result.success(uuid);
    }

    @GetMapping("/result")
    @ResponseBody
    public Result<OrderDetail> deliverOrderResult(User user, @RequestParam("id") String id) {
        if (user == null) {
            return Result.error(AppMessage.SESSION_OVER);
        }

        OrderDetail orderDetail = redisService.get(OrderKeyPrefix.uuid, id, OrderDetail.class);
        if (orderDetail == null) {
            return Result.error(AppMessage.ORDER_PENDING);
        } else if (StringUtils.isEmptyOrWhitespace(orderDetail.getProductName())) {
            return Result.error(AppMessage.ORDER_UNSUCCESSFUL);
        }

        return Result.success(orderDetail);
    }
}
