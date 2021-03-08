package me.travelplan.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.user.AuthService;
import me.travelplan.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping(path = "/auth")
@RestController
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void register(@RequestBody Register request) {
        userService.create(request.getEmail(), request.getPassword(), request.getName());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public AuthService.Login login(@RequestBody Login request) {
        return authService.login(request.getEmail(), request.getPassword());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    public AuthService.Me me(@CurrentUser CustomUserDetails currentUser) {
        return authService.me(currentUser);
    }

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
