package cc.lzhong.scalaz.util;

import cc.lzhong.scalaz.util.AppMessage;

public class Result<T> {

    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    public static <T> Result<T> error(AppMessage message) {
        return new Result<T>(message);
    }

    private Result(T data) {
        this.code = 1200;
        this.message = "Success";
        this.data = data;
    }

    private Result(AppMessage message) {
        if (message != null) {
            this.code = message.getCode();
            this.message = message.getMessage();
        }
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

}
