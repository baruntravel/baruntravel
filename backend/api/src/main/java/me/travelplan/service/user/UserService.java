package me.travelplan.service.user;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.user.domain.User;
import me.travelplan.service.user.exception.EmailExistedException;
import me.travelplan.service.user.exception.UserNotFoundException;
import me.travelplan.service.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service

public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User create(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailExistedException();
        }
        return userRepository.save(user);
    }

    public User getMe(User user) {
        return userRepository.findByEmailWithAvatar(user.getEmail()).orElseThrow(UserNotFoundException::new);
    }
}