package com.example.shows.model.layerAboveNetwork.mapping;


import com.example.shows.model.database.entity.Booking;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.database.entity.User;
import com.example.shows.model.network.dto.BookingDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(uses = {PerformanceMapping.class,UserMapping.class})
public interface BookingMapping {
    @Mappings({
            @Mapping(target = "performance", source = "performanceId",qualifiedByName = "idToEPerform"),
            @Mapping(target = "user", source = "userId",qualifiedByName = "idToEUser")
    })
    Booking dtoToEntity(BookingDto dto);

    @Mappings({
            @Mapping(target = "performanceId", source = "performance", qualifiedByName = "eToIdPerform"),
            @Mapping(target = "userId", source = "user",  qualifiedByName = "eToIdUser" )
    })
    BookingDto entityToDto(Booking booking);


    @Named("idToEPerform")
    default Performance idToEPerform(Integer id){
        Performance performance = new Performance();
        performance.setId(id);
        return performance;
    }

    @Named("eToIdPerform")
    default Integer eToIdPerform(Performance performance){
        return performance.getId();
    }

    @Named("idToEUser")
    default User idToEUser(Integer id){
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("eToIdUser")
    default Integer eToIdUser(User user){
        return user.getId();
    }


}
