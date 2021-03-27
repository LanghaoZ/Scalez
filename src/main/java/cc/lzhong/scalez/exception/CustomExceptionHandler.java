package cc.lzhong.scalez.exception;

import cc.lzhong.scalez.exception.type.GeneralException;
import cc.lzhong.scalez.util.response.AppMessage;
import cc.lzhong.scalez.util.response.Result;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindException;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class CustomExceptionHandler {

    @ExceptionHandler(value=Exception.class)
    public Result<String> handleException(HttpServletRequest request, Exception exp) {
        exp.printStackTrace();
        if (exp instanceof GeneralException) {
            GeneralException globalException = (GeneralException)exp;
            return Result.error(globalException.getAppMessage());
        } else if (exp instanceof BindException) {
            BindException bindException = (BindException)exp;
            List<ObjectError> errors = bindException.getAllErrors();
            ObjectError error = errors.get(0);

            String message = error.getDefaultMessage();
            return Result.error(AppMessage.BIND_ERROR.fillArgs(message));
        } else {
            return Result.error(AppMessage.INTERNAL_ERROR);
        }
    }
}
