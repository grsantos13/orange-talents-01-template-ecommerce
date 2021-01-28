package br.com.zup.ecommerce.auth;

import br.com.zup.ecommerce.shared.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginRequest request){
        UsernamePasswordAuthenticationToken credentials = request.toUsernamePasswordAuthenticationToken();
        try{
            Authentication authentication = authenticationManager.authenticate(credentials);
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(new TokenResponse(token, "Bearer"));
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }

    }
}
