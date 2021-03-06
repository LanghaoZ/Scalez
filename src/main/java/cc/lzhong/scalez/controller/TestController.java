package cc.lzhong.scalez.controller;

import cc.lzhong.scalez.domain.User;
import cc.lzhong.scalez.service.RedisService;
import cc.lzhong.scalez.service.UserService;
import cc.lzhong.scalez.util.queue.Sender;
import cc.lzhong.scalez.util.response.AppMessage;
import cc.lzhong.scalez.util.response.Result;
import cc.lzhong.scalez.util.redis.UserKeyPrefix;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    private final UserService userService;
    private final RedisService redisService;
    private final Sender sender;

    public TestController(UserService userService, RedisService redisService, Sender sender) {
        this.userService = userService;
        this.redisService = redisService;
        this.sender = sender;
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
        User user = userService.getUserById(1L);

        return Result.success(user);
    }

    @GetMapping("/redis/get")
    @ResponseBody
    public Result<User> returnRedisGet() {
        User user = redisService.get(UserKeyPrefix.byId, "" + 2, User.class);
        return Result.success(user);
    }

    @GetMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> returnRedisSet() {
        User user = new User();
        user.setId(1L);
        user.setName("redis");
        user.setPassword("password");
        user.setSalt("salt");
        Boolean result = redisService.set(UserKeyPrefix.byId, "" + 2, user);
        return Result.success(result);
    }

    @GetMapping("/queue/send")
    @ResponseBody
    public Result<String> sendMessage() {
        sender.sendMessage("graduating in S21.");
        return Result.success("Validating Queuing");
    }
}
