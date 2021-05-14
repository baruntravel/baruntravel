package me.travelplan.service.user;

import lombok.RequiredArgsConstructor;
import me.travelplan.service.file.FileService;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.user.domain.User;
import me.travelplan.service.user.exception.EmailExistedException;
import me.travelplan.service.user.exception.UserNotFoundException;
import me.travelplan.service.user.repository.UserRepository;
import me.travelplan.web.auth.AuthRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FileService fileService;

    @Transactional
    public User create(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailExistedException();
        }
        return userRepository.save(user);
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