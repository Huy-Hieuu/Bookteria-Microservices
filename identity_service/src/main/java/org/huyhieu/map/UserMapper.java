package org.huyhieu.map;

import java.util.List;

import org.huyhieu.dto.data.UserDto;
import org.huyhieu.dto.request.UserCreateRequest;
import org.huyhieu.dto.request.UserUpdateRequest;
import org.huyhieu.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author donh
 */
@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toUserDto(User user);

    List<UserDto> toUserDtos(List<User> users);

    User toUser(UserUpdateRequest request);

    User toUser(UserCreateRequest request);
}
