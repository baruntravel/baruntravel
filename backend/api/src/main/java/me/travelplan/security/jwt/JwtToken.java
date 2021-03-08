package me.travelplan.security.jwt;

import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
public class JwtToken {
    private String token;
    private LocalDateTime expiredAt;

    private JwtToken(String token, LocalDateTime expiredAt) {
        this.token = token;
        this.expiredAt = expiredAt;
    }

    public static JwtToken create(String token, Date exp) {
        return new JwtToken(token, Instant.ofEpochMilli(exp.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
}
