package me.travelplan;

import me.travelplan.security.userdetails.CustomUserDetails;
import me.travelplan.service.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = User
                .builder()
                .email("labyu2020@naver.com")
                .password(passwordEncoder.encode("test1234"))
                .name("testname")
                .build();

        CustomUserDetails customUserDetails = CustomUserDetails.create(user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        context.setAuthentication(token);
        return context;
    }
}