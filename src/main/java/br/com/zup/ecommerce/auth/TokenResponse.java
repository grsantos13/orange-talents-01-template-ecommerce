package br.com.zup.ecommerce.auth;

public class TokenResponse {
    private String token;
    private String type;

    public TokenResponse(String token, String type) {
        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }
}
