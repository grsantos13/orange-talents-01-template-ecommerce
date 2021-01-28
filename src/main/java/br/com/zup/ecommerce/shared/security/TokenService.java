package br.com.zup.ecommerce.shared.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private String expiration;

    public String generateToken(Authentication authentication) {
        Date expiration = new Date(new Date().getTime() + Long.parseLong(this.expiration));
        ActiveUser user = (ActiveUser) authentication.getPrincipal();
        return Jwts.builder()
                   .setIssuer("API Mercado Livre")
                   .setSubject(user.getUsername())
                   .setIssuedAt(new Date())
                   .setExpiration(expiration)
                   .signWith(SignatureAlgorithm.HS256, secret)
                   .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public String getUserLogin(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(this.secret)
                .parseClaimsJws(token).getBody();

        return claims.getSubject();
    }
}
