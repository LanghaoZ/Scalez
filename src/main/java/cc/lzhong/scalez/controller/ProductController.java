package cc.lzhong.scalez.controller;

import cc.lzhong.scalez.domain.Product;
import cc.lzhong.scalez.domain.User;
import cc.lzhong.scalez.service.*;
import cc.lzhong.scalez.util.redis.ProductKeyPrefix;
import cc.lzhong.scalez.util.response.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private static final String indexViewName = "index";
    private static final String detailViewName = "detail-";

    private final RedisService redisService;
    private final ProductService productService;

    private final ThymeleafViewResolver thymeleafViewResolver;

    public ProductController(RedisService redisService, ProductService productService,
                 ThymeleafViewResolver thymeleafViewResolver) {
        this.redisService = redisService;
        this.productService = productService;
        this.thymeleafViewResolver = thymeleafViewResolver;
    }

    @GetMapping(value = "/index/deprecated", produces = "text/html")
    public String deprecatedProductIndexView(Model model, User user) {

        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        return "product/index";
    }

    @GetMapping(value = "/index/", produces = "text/html")
    @ResponseBody
    public String renderProductIndexView(HttpServletRequest request, HttpServletResponse response, Model model, User user) {

        String html = redisService.get(ProductKeyPrefix.webView, indexViewName, String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("product/index", webContext);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(ProductKeyPrefix.webView, indexViewName, html);
        }

        return html;
    }

    @GetMapping(value = "/detail/{id}", produces = "text/html")
    @ResponseBody
    public String renderProductDetailV1(HttpServletRequest request, HttpServletResponse response,
                              @PathVariable("id") Long id, Model model, User user) {

        String html = redisService.get(ProductKeyPrefix.webView, detailViewName + id.toString(), String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        Product product = productService.getProductById(id);
        model.addAttribute("user", user);
        model.addAttribute("product", product);

        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("product/detail", webContext);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(ProductKeyPrefix.webView, detailViewName + id.toString(), html);
        }

        return html;
    }

    @GetMapping(value = "/detail/data/{id}")
    @ResponseBody
    public Result<Product> getProductDetailData(@PathVariable("id") Long id, Model model) {

        Product product = productService.getProductById(id);
        model.addAttribute("product", product);

        return Result.success(product);
    }

}
