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
        private MultipartFile avatar;
        private String email;
        private String password;
        private String name;
    }

    @Getter
    public static class Login {
        private String email;
        private String password;
    }
}
