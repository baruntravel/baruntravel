package me.travelplan.web.auth;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class AuthRequest {

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Register {
        MultipartFile avatar;
        String email;
        String password;
        String name;
    }

    @Getter
    public static class Login {
        String email;
        String password;
    }
}
