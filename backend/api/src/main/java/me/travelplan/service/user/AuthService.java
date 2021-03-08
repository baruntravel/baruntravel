package me.travelplan.service.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import me.travelplan.security.userdetails.CustomUserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import me.travelplan.security.jwt.JwtToken;
import me.travelplan.security.jwt.JwtTokenProvider;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public Me me(CustomUserDetails currentUser) {
        return Me.from(currentUser.getUser());
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

    public Login login(String email, String password) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("찾을 수 없는 사용자입니다"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("잘못된 비밀번호입니다");
        }

        return Login.from(tokenProvider.generateAccessToken(user), tokenProvider.generateRefreshToken(user));
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Login {
        private String accessToken;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime accessTokenExpiredAt;
        private String refreshToken;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime refreshTokenExpiredAt;

        public static Login from(JwtToken accessToken, JwtToken refreshToken) {
            return new Login(
                    accessToken.getToken(),
                    accessToken.getExpiredAt(),
                    refreshToken.getToken(),
                    refreshToken.getExpiredAt()
            );
        }
    }
}
