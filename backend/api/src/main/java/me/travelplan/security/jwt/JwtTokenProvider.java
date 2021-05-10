package me.travelplan.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.travelplan.exception.BusinessException;
import me.travelplan.service.user.domain.User;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtTokenProvider {
    private final JwtProps jwtProps;

    public Token generateAccessToken(User user) {
        return generateToken(user, jwtProps.getAccessTokenProps());
    }

    public Token generateRefreshToken(User user) {
        return generateToken(user, jwtProps.getRefreshTokenProps());
    }

    public Long getUserIdFromAccessToken(String token) throws RuntimeException {
        return Long.valueOf(getClaims(token, jwtProps.getAccessTokenProps()).getBody().getSubject());
    }

    public Long getUserIdFromRefreshToken(String token) throws RuntimeException {
        return Long.valueOf(getClaims(token, jwtProps.getRefreshTokenProps()).getBody().getSubject());
    }

    private Token generateToken(User user, JwtProps.TokenProps tokenProps) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenProps.getSecret()));

        Date exp = new Date((new Date()).getTime() + tokenProps.getExpirationTimeMilliSec());
        String token = Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .setAudience(user.getEmail())
                .setExpiration(exp)
                .signWith(key)
                .compact();

        return Token.create(token, exp);
    }

    private Jws<Claims> getClaims(String token, JwtProps.TokenProps tokenProps) throws RuntimeException {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenProps.getSecret()));
        Jws<Claims> claims = null;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException ex) {
            log.error(ex.getMessage());
            throw new BusinessException("다시 로그인 해주세요");
        }

        return claims;
    }
}
