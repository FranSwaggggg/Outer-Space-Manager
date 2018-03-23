package francois.tomasi.outerspacemanager.responses;

public class ConnectUserResponse {
    private String token;
    private long expires;

    public ConnectUserResponse(String token, long expires) {
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
