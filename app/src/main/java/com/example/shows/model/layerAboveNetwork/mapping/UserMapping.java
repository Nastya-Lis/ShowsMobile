package com.example.shows.model.layerAboveNetwork.mapping;



import com.example.shows.model.database.contract.RoleType;
import com.example.shows.model.database.dao.RoleDao;
import com.example.shows.model.database.entity.Role;
import com.example.shows.model.database.entity.User;
import com.example.shows.model.layerAboveNetwork.service.RoleServiceForMapping;
import com.example.shows.model.network.dto.RoleDto;
import com.example.shows.model.network.dto.UserDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;


//ВЕРНИСЬ СЮДА ИСПРАВЬ МЕНЯ
@Mapper(uses = {BookingMapping.class,RoleMapping.class})
public interface UserMapping {

    @Mapping(target = "role", source = "roleId",qualifiedByName = "idToEntity")
    User dtoToEntity(UserDto dto);

    @Mapping(target = "roleId", source = "role", qualifiedByName = "entityToId")
    UserDto entityToDto(User user);

    List<User> dtoesToEntities(List<UserDto> dtoes);

    @Named("idToEntity")
    default Role idToEntity(Integer id){
      /*  for (RoleType roleType: RoleType.values()) {
            if(roleType.getId() == id){

            }
        }*/
        Role role = new Role();
        role.setId(id);
        return role;
    }

   /* @Named("entityToName")
    default String entityToName(Role role){
        return role.getName();
    }*/

    @Named("entityToId")
    default Integer entityToId(Role role){
        return 2;//role.getId();
    }
}
