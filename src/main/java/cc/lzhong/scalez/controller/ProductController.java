package cc.lzhong.scalez.controller;

import cc.lzhong.scalez.domain.Product;
import cc.lzhong.scalez.domain.User;
import cc.lzhong.scalez.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final RedisService redisService;
    private final ProductService productService;

    public ProductController(RedisService redisService, ProductService productService) {
        this.redisService = redisService;
        this.productService = productService;
    }

    @GetMapping("/index")
    public String renderProductIndexView(Model model, User user) {

        List<Product> products = productService.getAllProducts();

        model.addAttribute("products", products);

        return "product/index";
    }

    @GetMapping("/detail/{id}")
    public String renderProductDetail(@PathVariable("id") Long id, Model model, User user) {

        Product product = productService.getProductById(id);

        model.addAttribute("user", user);
        model.addAttribute("product", product);

        return "product/detail";
    }

}
