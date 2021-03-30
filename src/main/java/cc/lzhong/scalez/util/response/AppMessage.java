package cc.lzhong.scalez.util.response;

public class AppMessage {

    private int code;
    private String message;

    public static AppMessage SUCCESS = new AppMessage(1200, "success");

    public static AppMessage BIND_ERROR = new AppMessage(1300, "Validation binding error: %s");
    public static AppMessage INVALID_PHONE_NUMBER = new AppMessage(1310, "Please enter a valid phone number");
    public static AppMessage INVALID_PASSWORD = new AppMessage(1320, "Password entered does not match");
    public static AppMessage INVALID_USER = new AppMessage(1330, "User does not exist");
    public static AppMessage SESSION_OVER = new AppMessage(1340, "No valid session. Please login before proceeding");

    public static AppMessage INSUFFICIENT_QUANTITY = new AppMessage(1410, "This product has sold all its quantity");
    public static AppMessage ONLY_ONE_PER_USER = new AppMessage(1420, "Currently, each customer can only purchase 1 product");

    public static AppMessage INTERNAL_ERROR = new AppMessage(1500, "Sever Error");

    private AppMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public AppMessage fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.message, args);

        return new AppMessage(code, message);
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
