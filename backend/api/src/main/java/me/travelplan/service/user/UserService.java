package me.travelplan.service.user;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.user.exception.EmailExistedException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User create(User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new EmailExistedException();
        }
        return userRepository.save(user);
    }
}