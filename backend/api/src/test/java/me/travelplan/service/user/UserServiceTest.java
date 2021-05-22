package me.travelplan.service.user;

import me.travelplan.service.file.FileService;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.user.domain.User;
import me.travelplan.service.user.exception.EmailExistedException;
import me.travelplan.service.user.repository.UserRepository;
import me.travelplan.web.auth.AuthRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private FileService fileService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공")
    public void create() throws Exception {
        AuthRequest.Register request = registerRequest();
        User user = createUser();

        given(userRepository.save(any())).willReturn(user);
        given(fileService.upload(any())).willReturn(File.builder().name("files").build());

        User createdUser = userService.create(request);

        assertEquals(request.getName(), createdUser.getName());
        assertEquals(request.getEmail(), createdUser.getEmail());
        assertEquals(request.getPassword(), createdUser.getPassword());
        assertEquals(request.getAvatar().getName(), createdUser.getAvatar().getName());
    }

    @Test
    @DisplayName("예외테스트: 회원가입시 이메일이 중복되었을 경우 예외 발생")
    public void ExistedEmail() throws Exception {
        AuthRequest.Register request = registerRequest();
        User user = createUser();

        given(userRepository.findByEmail(request.getEmail())).willReturn(Optional.ofNullable(user));

        assertThrows(EmailExistedException.class, () -> userService.create(request));
    }

    @Test
    @DisplayName("개인정보 수정 성공")
    public void update() throws Exception {
        InputStream is = new ClassPathResource("mock/images/enjoy2.png").getInputStream();
        MockMultipartFile mockFile = new MockMultipartFile("updateFile", "mock_file1.jpg", "image/jpg", is.readAllBytes());
        AuthRequest.Update request = AuthRequest.Update.builder()
                .name("수정된 이름")
                .avatar(mockFile)
                .avatarChange(true)
                .build();
        User user = createUser();

        given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(user));
        given(fileService.upload(any())).willReturn(File.builder().name("updateFile").build());

        userService.update(request, user);

        assertEquals(request.getName(), user.getName());
        assertEquals(request.getAvatar().getName(), user.getAvatar().getName());
    }

    @Test
    @DisplayName("개인정보 수정 아바타 삭제 성공")
    public void updateAvatarNull() {
        AuthRequest.Update request = AuthRequest.Update.builder()
                .name("수정된 이름")
                .avatar(null)
                .avatarChange(true)
                .build();
        User user = createUser();

        given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(user));

        userService.update(request, user);

        assertEquals(request.getName(), user.getName());
        assertNull(user.getAvatar());
    }

    private AuthRequest.Register registerRequest() throws IOException {
        InputStream is = new ClassPathResource("mock/images/enjoy.png").getInputStream();
        MockMultipartFile mockFile = new MockMultipartFile("files", "mock_file1.jpg", "image/jpg", is.readAllBytes());
        return AuthRequest.Register.builder()
                .name("테스터")
                .email("test@test.com")
                .password("123456")
                .avatar(mockFile)
                .build();
    }

    private User createUser() {
        return User.builder()
                .name("테스터")
                .email("test@test.com")
                .password("123456")
                .avatar(File.builder().name("files").build())
                .build();
    }
}