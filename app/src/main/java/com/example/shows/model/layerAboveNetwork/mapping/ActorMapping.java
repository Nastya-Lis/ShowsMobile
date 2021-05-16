package com.example.shows.model.layerAboveNetwork.mapping;

import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.network.dto.ActorDto;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = PerformanceMapping.class)
public interface ActorMapping {
    @Mapping(target = "performances",source = "dto.performances")
    Actor dtoToEntity(ActorDto dto);
    @Mapping(target = "performances",source = "actor.performances")
    ActorDto entityToDto(Actor actor);
}
