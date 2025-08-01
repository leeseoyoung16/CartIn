package com.shop.CartIn.jwt;

import com.shop.CartIn.config.CustomUserDetails;
import com.shop.CartIn.config.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider
{
    private final CustomUserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secretKeyString;

    private Key key;

    @Value("${jwt.token-validity-in-seconds}")
    private long tokenValidityInSeconds;

    private long tokenValidityInMilliseconds;

    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    @PostConstruct
    public void init() {
        String base64EncodeSecretKey = Base64.getEncoder().encodeToString(secretKeyString.getBytes());
        this.key = Keys.hmacShaKeyFor(base64EncodeSecretKey.getBytes());
        this.tokenValidityInMilliseconds = this.tokenValidityInSeconds * 1000L;
    }

    public String generateToken(String username, String role)
    {
        long now = (new Date()).getTime();
        Date validity = new Date(now + tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(username)
                .claim("auth", "ROLE_" + role)
                .setIssuedAt(new Date(now))
                .setExpiration(validity)
                .signWith(this.key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature.", e);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.", e);
        }
        return false;
    }

    public Authentication getAuthentication(String token)
    {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();

        CustomUserDetails userDetails =
                (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }
}
