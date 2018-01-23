package francois.tomasi.outerspacemanager;

public class CreateUserResponse {
    private String token;
    private int expires;

    public CreateUserResponse(String token, int expires) {
        this.token = token;
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

}
