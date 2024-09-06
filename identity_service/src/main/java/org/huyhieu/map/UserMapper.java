package org.huyhieu.map;

import org.huyhieu.dto.data.IdentityUserDto;
import org.huyhieu.dto.request.UserCreateRequest;
import org.huyhieu.dto.request.UserUpdateRequest;
import org.huyhieu.entity.IdentityRole;
import org.huyhieu.entity.IdentityUser;
import org.huyhieu.enums.RoleEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 *
 * @author donh
 */
@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roles", source = "identityRoles")
    IdentityUserDto toUserDto(IdentityUser identityUser);

    default RoleEnum toRoleEnum(IdentityRole identityRole) {
        return identityRole.getType();
    }

    List<IdentityUserDto> toUserDtos(List<IdentityUser> identityUsers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "identityRoles", ignore = true)
    IdentityUser toUser(UserUpdateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "identityRoles", ignore = true)
    IdentityUser toUser(UserCreateRequest request);
}
