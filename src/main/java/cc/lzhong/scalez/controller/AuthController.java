package cc.lzhong.scalez.controller;

import cc.lzhong.scalez.service.UserService;
import cc.lzhong.scalez.util.response.Result;
import cc.lzhong.scalez.vo.AuthVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String renderLoginView() {
        return "auth/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public Result<Boolean> processLogin(HttpServletResponse response, @Valid AuthVo authVo) {
        logger.info(authVo.toString());
        userService.login(response, authVo);

        return Result.success(true);
    }
}
