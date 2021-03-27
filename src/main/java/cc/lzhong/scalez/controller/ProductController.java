package cc.lzhong.scalez.controller;

import cc.lzhong.scalez.domain.User;
import cc.lzhong.scalez.service.RedisService;
import cc.lzhong.scalez.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final UserService userService;
    private final RedisService redisService;

    public ProductController(UserService userService, RedisService redisService) {
        this.userService = userService;
        this.redisService = redisService;
    }

    @GetMapping("/index")
    public String renderProductIndexView(Model model, User user) {

        if (user == null) {
            return "/auth/login";
        }

        model.addAttribute("user", user);

        return "product/index";
    }
}
