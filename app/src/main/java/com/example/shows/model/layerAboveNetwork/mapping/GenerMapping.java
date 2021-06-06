package com.example.shows.model.layerAboveNetwork.mapping;


import com.example.shows.model.database.entity.CommonEntity;
import com.example.shows.model.database.entity.Geners;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.network.dto.GenersDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = PerformanceMapping.class)
public interface GenerMapping {
    @Mapping(target = "performances", source = "performancesId", qualifiedByName = "idToEntity")
    Geners dtoToEntity(GenersDto dto);
    @Mapping(target = "performancesId", source = "performances", qualifiedByName = "entityToId")
    GenersDto entityToDto(Geners actor);

    List<Geners> dtoesToEntities(List<GenersDto> dtoes);

    @Named("entityToId")
    default Collection<Integer> entityToId(Collection<Performance> performances){
        return performances.stream().map(CommonEntity::getId).collect(Collectors.toList());
    }

    @Named("idToEntity")
    default Collection<Performance> idToEntity(Collection<Integer> idCollection){
        Collection<Performance> performancesCollection = new ArrayList<>();

        for (Integer id: idCollection) {
            if(id != null){
                Performance performances = new Performance();
                performances.setId(id);
                performancesCollection.add(performances);
            }
        }
        return performancesCollection;
    }

}
