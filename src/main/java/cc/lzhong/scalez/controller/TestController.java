package cc.lzhong.scalez.controller;

import cc.lzhong.scalez.domain.User;
import cc.lzhong.scalez.service.UserService;
import cc.lzhong.scalez.util.AppMessage;
import cc.lzhong.scalez.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    private final UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/success")
    @ResponseBody
    public Result<String> returnSuccess() {
        return Result.success("Return with success");
    }

    @GetMapping("/error")
    @ResponseBody
    public Result<String> returnError() {
        return Result.error(AppMessage.INTERNAL_ERROR);
    }

    @GetMapping("/dbconnect")
    @ResponseBody
    public Result<User> returnUserFromDB() {
        User user = userService.getUserById(1);

        return Result.success(user);
    }
}
