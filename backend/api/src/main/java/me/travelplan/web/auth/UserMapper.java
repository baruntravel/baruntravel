package me.travelplan.web.auth;

import me.travelplan.service.user.domain.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        uses = {PasswordEncoderMapper.class, AvatarMapper.class},
        imports = {LocalDateTime.class, UUID.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserMapper {
    @Mapping(target = "password", source = "password", qualifiedBy = EncodeMapping.class)
    @Mapping(target = "avatar", source = "avatar", qualifiedBy = AvatarMapping.class)
    @Mapping(target = "refreshToken", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "refreshTokenExpiredAt", expression = "java(LocalDateTime.now())")
    User toEntity(AuthRequest.Register request);

    @Mapping(target = "email",source = "email")
    @Mapping(target = "name",source = "name")
    @Mapping(target = "avatar",source = "user.avatar.url")
    AuthResponse.Me toMe(User user);
}
