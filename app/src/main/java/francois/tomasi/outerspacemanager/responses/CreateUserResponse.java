package francois.tomasi.outerspacemanager.responses;

public class CreateUserResponse {
    private String token;
    private long expires;

    private String code;
    private String internalCode;
    private String status;
    private String message;

    public CreateUserResponse(String token, long expires) {
        this.token = token;
        this.expires = expires;
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
