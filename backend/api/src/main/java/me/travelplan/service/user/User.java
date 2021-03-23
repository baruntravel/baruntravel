package me.travelplan.service.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import me.travelplan.config.jpa.BaseEntity;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;
    private String refreshToken;

    @Builder
    public User(String email, String password, String name, String refreshToken) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.refreshToken = refreshToken;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
