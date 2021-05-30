package me.travelplan.web.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.travelplan.service.file.domain.File;
import me.travelplan.service.user.domain.User;

import java.util.Optional;

public class UserDto {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private final String name;
        private final String email;
        private final String avatarUrl;

        public static Response from(User user) {
            return Response.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .avatarUrl(Optional.ofNullable(user.getAvatar()).orElse(File.createExternalImage("")).getUrl())
                    .build();
        }
    }
}
