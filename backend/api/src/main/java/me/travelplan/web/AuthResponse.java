package me.travelplan.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.travelplan.security.jwt.Token;
import me.travelplan.service.user.User;

import java.time.LocalDateTime;

public class AuthResponse {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Login {
        private String accessToken;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime accessTokenExpiredAt;
        private String refreshToken;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime refreshTokenExpiredAt;

        public static Login from(Token accessToken, Token refreshToken) {
            return new Login(
                    accessToken.getToken(),
                    accessToken.getExpiredAt(),
                    refreshToken.getToken(),
                    refreshToken.getExpiredAt()
            );
        }
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Me {
        private String email;
        private String name;

        public static Me from(User user) {
            return new Me(user.getEmail(), user.getName());
        }
    }
}
