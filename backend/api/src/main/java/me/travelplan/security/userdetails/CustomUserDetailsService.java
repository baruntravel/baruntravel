package me.travelplan.security.userdetails;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.user.domain.User;
import me.travelplan.service.user.exception.UserNotFoundException;
import me.travelplan.service.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return CustomUserDetails.create(user);
    }

    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return CustomUserDetails.create(user);
    }
}
