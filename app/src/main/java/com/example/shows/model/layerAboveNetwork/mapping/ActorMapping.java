package com.example.shows.model.layerAboveNetwork.mapping;

import com.example.shows.model.database.entity.Actor;
import com.example.shows.model.database.entity.CommonEntity;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.network.dto.ActorDto;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = PerformanceMapping.class)
public interface ActorMapping {
    @Mapping(target = "performances",source = "dto.performances",qualifiedByName = "idToEntity")
    Actor dtoToEntity(ActorDto dto);
    @Mapping(target = "performances",source = "actor.performances",qualifiedByName = "entitiesToId")
    ActorDto entityToDto(Actor actor);

    List<Actor> dtoesToEntities(List<ActorDto> dtoes);

    @Named("entitiesToId")
    default Set<Integer> entitiesToId(Set<Performance> performancesSet){
        return performancesSet.stream().map(CommonEntity::getId).collect(Collectors.toSet());
    }

    @Named("idToEntity")
    default Set<Performance> idToEntity(Set<Integer> setId){

        Set<Performance> setPerformances = new HashSet<>();

        for (Integer id: setId) {
            if(id!=null){
                Performance performance= new Performance();
                performance.setId(id);
                setPerformances.add(performance);
            }
        }

        return setPerformances;
    }
}
