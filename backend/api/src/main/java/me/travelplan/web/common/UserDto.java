package me.travelplan.web.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import me.travelplan.service.user.domain.User;

public class UserDto {
    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private final String name;
        private final String email;
        private final String avatarUrl;

        public static Response from(User user) {
            ResponseBuilder builder = Response.builder()
                    .name(user.getName())
                    .email(user.getEmail());
            if (user.getAvatar() != null) {
                builder.avatarUrl(user.getAvatar().getUrl());
            }
            return builder.build();
        }
    }
}
