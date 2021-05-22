package me.travelplan.service.user;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.file.FileService;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.user.domain.User;
import me.travelplan.service.user.exception.EmailExistedException;
import me.travelplan.service.user.exception.UserNotFoundException;
import me.travelplan.service.user.repository.UserRepository;
import me.travelplan.web.auth.AuthRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User create(AuthRequest.Register request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailExistedException();
        }
        File avatar = fileService.upload(request.getAvatar());
        return userRepository.save(User.create(request.getName(), request.getEmail(), request.getPassword(), avatar, passwordEncoder));
    }

    public User getMe(User user) {
        return userRepository.findByEmail(user.getEmail()).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void update(AuthRequest.Update request, User loginUser) {
        User user = userRepository.findByEmail(loginUser.getEmail()).orElseThrow(UserNotFoundException::new);
        if (request.isAvatarChange()) {
            File file = null;
            if (loginUser.getAvatar() != null) {
                fileService.deleteById(user.getAvatar().getId());
            }
            if (request.getAvatar() != null) {
                file = fileService.upload(request.getAvatar());
            }
            user.updateAvatar(file);
        }
        user.updateName(request.getName());
    }
}