package me.travelplan.service.user;

import com.querydsl.core.types.Order;
import me.travelplan.web.AuthRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User requestToEntity(AuthRequest.Register request);
}
