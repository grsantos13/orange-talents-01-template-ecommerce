package br.com.zup.ecommerce.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginRequest {

    @NotBlank
    private String login;

    @NotBlank
    @Size(min = 6)
    private String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UsernamePasswordAuthenticationToken toUsernamePasswordAuthenticationToken(){
        return new UsernamePasswordAuthenticationToken(this.login, this.password);
    }
}
