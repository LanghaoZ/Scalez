package cc.lzhong.scalez.controller;

import cc.lzhong.scalez.domain.Product;
import cc.lzhong.scalez.domain.User;
import cc.lzhong.scalez.service.ProductService;
import cc.lzhong.scalez.service.RedisService;
import cc.lzhong.scalez.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final UserService userService;
    private final RedisService redisService;
    private final ProductService productService;

    public ProductController(UserService userService, RedisService redisService, ProductService productService) {
        this.userService = userService;
        this.redisService = redisService;
        this.productService = productService;
    }

    @GetMapping("/index")
    public String renderProductIndexView(Model model, User user) {

        if (user == null) {
            return "/auth/login";
        }

        List<Product> products = productService.getAllProducts();

        model.addAttribute("user", user);
        model.addAttribute("products", products);

        return "product/index";
    }

    @GetMapping("/detail/{id}")
    public String renderProductDetail(@PathVariable("id") Long id, Model model, User user) {

        if (user == null) {
            return "/auth/login";
        }

        Product product = productService.getProductById(id);

        model.addAttribute("user", user);
        model.addAttribute("product", product);

        return "product/detail";
    }
}
