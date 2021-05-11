package me.travelplan.service.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.travelplan.config.jpa.BaseEntity;
import me.travelplan.service.file.domain.File;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    public User(File avatar, String email, String password, String name, String refreshToken, LocalDateTime refreshTokenExpiredAt) {
        this.avatar = avatar;
        this.email = email;
        this.password = password;
        this.name = name;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiredAt = refreshTokenExpiredAt;
    }
}
