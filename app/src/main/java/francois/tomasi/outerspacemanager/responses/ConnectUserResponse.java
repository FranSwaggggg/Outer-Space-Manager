package francois.tomasi.outerspacemanager.responses;

public class ConnectUserResponse {
    private String token;
    private long expires;

    private String code;
    private String internalCode;
    private String status;
    private String message;

    public ConnectUserResponse(String token, long expires, String code, String internalCode, String status, String message) {
        this.token = token;
        this.expires = expires;
        this.code = code;
        this.internalCode = internalCode;
        this.status = status;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public long getExpires() {
        return expires;
    }

    public String getCode() { return code; }

    public String getInternalCode() { return internalCode; }

    public String getStatus() { return status; }

    public String getMessage() { return message; }
}
