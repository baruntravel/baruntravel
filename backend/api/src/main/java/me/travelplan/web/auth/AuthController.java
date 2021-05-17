package me.travelplan.web.auth;

import lombok.RequiredArgsConstructor;
import me.travelplan.security.userdetails.CurrentUser;
import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.user.AuthService;
import me.travelplan.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void register(AuthRequest.Register request) {
        userService.create(request);
    }

    @PostMapping("/login")
    public AuthResponse.Login login(@RequestBody AuthRequest.Login request) {
        return authService.login(request.getEmail(), request.getPassword());
    }

    @GetMapping("/me")
    public AuthResponse.Me me(@CurrentUser CustomUserDetails currentUser) {
        return userMapper.toMe(userService.getMe(currentUser.getUser()));
    }

    @PostMapping("/update")
    public void update(AuthRequest.Update request, @CurrentUser CustomUserDetails customUserDetails) {
        userService.update(request, customUserDetails.getUser());
    }
}
