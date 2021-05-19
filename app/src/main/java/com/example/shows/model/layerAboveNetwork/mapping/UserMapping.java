package com.example.shows.model.layerAboveNetwork.mapping;



import com.example.shows.model.database.entity.Role;
import com.example.shows.model.database.entity.User;
import com.example.shows.model.network.dto.RoleDto;
import com.example.shows.model.network.dto.UserDto;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = RoleMapping.class)
public interface UserMapping {
    User dtoToEntity(UserDto dto);
    UserDto entityToDto(User user);
    List<User> dtoesToEntities(List<UserDto> dtoes);
}
