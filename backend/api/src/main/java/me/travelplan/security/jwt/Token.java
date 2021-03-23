package me.travelplan.security.jwt;

import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Getter
public class Token {
    private String token;
    private LocalDateTime expiredAt;

    public Token(String token, LocalDateTime expiredAt) {
        this.token = token;
        this.expiredAt = expiredAt;
    }

    public static Token create(String token, Date exp) {
        return new Token(token, Instant.ofEpochMilli(exp.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime());
    }
}
