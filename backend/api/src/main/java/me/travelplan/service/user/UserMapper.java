package me.travelplan.service.user;

import me.travelplan.web.auth.AuthRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        uses = {PasswordEncoderMapper.class},
        imports = {LocalDateTime.class, UUID.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserMapper {

    @Mapping(target = "password", qualifiedBy = EncodeMapping.class)
    @Mapping(target = "refreshToken", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "refreshTokenExpiredAt", expression = "java(LocalDateTime.now())")
    User toEntity(AuthRequest.Register request);
}
