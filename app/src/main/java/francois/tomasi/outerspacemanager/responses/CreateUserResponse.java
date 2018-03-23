package francois.tomasi.outerspacemanager.responses;

public class CreateUserResponse {
    private String token;
    private long expires;

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
}
