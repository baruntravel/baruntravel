package me.travelplan.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.travelplan.security.jwt.JwtTokenProvider;
import me.travelplan.security.jwt.Token;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.user.domain.User;
import me.travelplan.service.user.exception.PasswordWrongException;
import me.travelplan.service.user.repository.UserRepository;
import me.travelplan.web.auth.AuthResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse.Me me(CustomUserDetails currentUser) {
        return AuthResponse.Me.from(currentUser.getUser());
    }

    @Transactional
    public AuthResponse.Login login(String email, String password) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("찾을 수 없는 사용자입니다"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordWrongException();
        }

        // TODO RefreshToken 처리 필요

        return AuthResponse.Login.from(tokenProvider.generateAccessToken(user), new Token(user.getRefreshToken(), user.getRefreshTokenExpiredAt()));
    }
}
