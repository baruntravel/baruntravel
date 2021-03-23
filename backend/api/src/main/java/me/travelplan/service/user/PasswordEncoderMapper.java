package me.travelplan.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PasswordEncoderMapper {
    private final PasswordEncoder passwordEncoder;

    @EncodeMapping
    public String encode(String value) {
        return passwordEncoder.encode(value);
    }
}
