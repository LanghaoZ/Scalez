package cc.lzhong.scalez.exception.type;

import cc.lzhong.scalez.util.response.AppMessage;

public class GeneralException extends RuntimeException {

    private static final long serialVersionUId = 1L;
    private AppMessage appMessage;

    public GeneralException(AppMessage appMessage) {
        super(appMessage.toString());
        this.appMessage = appMessage;
    }

    public AppMessage getAppMessage() {
        return this.appMessage;
    }

}
