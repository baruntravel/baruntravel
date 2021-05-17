package me.travelplan.service.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.file.domain.File;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "avatar_file_id")
    @OneToOne(fetch = FetchType.LAZY)
    private File avatar;

    private String email;
    private String password;
    private String name;
    private String refreshToken;
    private LocalDateTime refreshTokenExpiredAt;

    @Builder
    public User(Long id, File avatar, String email, String password, String name, String refreshToken, LocalDateTime refreshTokenExpiredAt) {
        this.id = id;
        this.avatar = avatar;
        this.email = email;
        this.password = password;
        this.name = name;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiredAt = refreshTokenExpiredAt;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateAvatar(File updateAvatar) {
        this.avatar = updateAvatar;
    }

    public static User create(String name, String email, String password, File avatar, PasswordEncoder passwordEncoder) {
        return User.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .avatar(avatar)
                .refreshToken(UUID.randomUUID().toString())
                .refreshTokenExpiredAt(LocalDateTime.now())
                .build();
    }
}
