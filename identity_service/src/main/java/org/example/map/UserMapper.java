package org.example.map;

import java.util.List;

import org.example.dto.UserDto;
import org.example.dto.request.UserCreateRequest;
import org.example.dto.request.UserUpdateRequest;
import org.example.entity.User;
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
