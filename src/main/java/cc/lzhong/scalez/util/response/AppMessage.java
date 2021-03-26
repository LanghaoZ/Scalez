package cc.lzhong.scalez.util.response;

public class AppMessage {

    private int code;
    private String message;

    public static AppMessage SUCCESS = new AppMessage(1200, "success");
    public static AppMessage INTERNAL_ERROR = new AppMessage(1500, "Sever Error");

    private AppMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
