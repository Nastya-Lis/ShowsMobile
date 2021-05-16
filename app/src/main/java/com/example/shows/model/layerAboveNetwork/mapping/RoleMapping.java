package com.example.shows.model.layerAboveNetwork.mapping;


import com.example.shows.model.database.entity.Role;
import com.example.shows.model.database.entity.User;
import com.example.shows.model.network.dto.RoleDto;
import com.example.shows.model.network.dto.UserDto;

import org.mapstruct.Mapper;

@Mapper
public interface RoleMapping {
    Role dtoToEntity(RoleDto dto);
    RoleDto entityToDto(Role role);
}
