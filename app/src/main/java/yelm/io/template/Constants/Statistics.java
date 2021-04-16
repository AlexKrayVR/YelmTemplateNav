package yelm.io.template.Constants;

public enum Statistics {

    OPEN_APP("open_app"),
    EXIT_APP("Exit Application"),
    GET_TOKEN("Get Token Application"),
    SUPPORT_LETTER("Support letter"),
    WRITE_MANAGER("Write manager"),
    CHANGE_NICKNAME("Change nickname");

    private final String reason;

    Statistics(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
//Statistics.OPEN_APP.getReason()
}
