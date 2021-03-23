package me.travelplan.service.user;

import me.travelplan.web.AuthRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = {PasswordEncoderMapper.class}, imports = {UUID.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    @Mapping(target = "password", qualifiedBy = EncodeMapping.class)
    @Mapping(target = "refreshToken", expression = "java(UUID.randomUUID().toString())")
    User toEntity(AuthRequest.Register request);
}
