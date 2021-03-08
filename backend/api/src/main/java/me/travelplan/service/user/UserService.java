package me.travelplan.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User create(String email, String password, String name) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new RuntimeException("중복된 이메일입니다");
        }


        return userRepository.save(User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build()
        );
    }
}