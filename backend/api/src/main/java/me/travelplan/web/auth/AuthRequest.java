package me.travelplan.web.auth;

import lombok.Getter;

public class AuthRequest {
    @Getter
    public static class Register {
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
