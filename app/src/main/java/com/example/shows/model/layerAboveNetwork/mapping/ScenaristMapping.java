package com.example.shows.model.layerAboveNetwork.mapping;


import com.example.shows.model.database.entity.CommonEntity;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.Scenarist;
import com.example.shows.model.network.dto.ScenaristDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = PerformanceMapping.class)
public interface ScenaristMapping {
    @Mapping(target = "performances",source = "dto.performances",qualifiedByName = "idToEntity")
    Scenarist dtoToEntity(ScenaristDto dto);
    @Mapping(target = "performances",source = "scenarist.performances",qualifiedByName = "entitiesToId")
    ScenaristDto entityToDto(Scenarist scenarist);


    List<Scenarist> dtoesToEntities(List<ScenaristDto> dtoes);

    @Named("entitiesToId")
    default Set<Integer> entitiesToId(Set<Performance> performancesSet){
        return performancesSet.stream().map(CommonEntity::getId).collect(Collectors.toSet());
    }

    @Named("idToEntity")
    default Set<Performance> idToEntity(Set<Integer> setId){
        Set setPerformances = new HashSet();
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
