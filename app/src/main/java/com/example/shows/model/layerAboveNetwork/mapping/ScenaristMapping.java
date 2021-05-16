package com.example.shows.model.layerAboveNetwork.mapping;


import com.example.shows.model.database.entity.Scenarist;
import com.example.shows.model.network.dto.ScenaristDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = PerformanceMapping.class)
public interface ScenaristMapping {
    @Mapping(target = "performances",source = "dto.performances")
    Scenarist dtoToEntity(ScenaristDto dto);
    @Mapping(target = "performances",source = "scenarist.performances")
    ScenaristDto entityToDto(Scenarist scenarist);
}
