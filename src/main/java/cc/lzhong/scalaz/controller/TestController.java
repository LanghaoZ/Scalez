package cc.lzhong.scalaz.controller;

import cc.lzhong.scalaz.util.AppMessage;
import cc.lzhong.scalaz.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

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
}
