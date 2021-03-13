package me.travelplan.web;

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
