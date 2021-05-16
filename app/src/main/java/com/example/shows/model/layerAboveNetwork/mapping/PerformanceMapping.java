package com.example.shows.model.layerAboveNetwork.mapping;

import com.example.shows.model.database.entity.Booking;
import com.example.shows.model.database.entity.CommonEntity;
import com.example.shows.model.database.entity.Performance;
import com.example.shows.model.network.dto.ActorDto;
import com.example.shows.model.network.dto.GenersDto;
import com.example.shows.model.network.dto.PerformanceDto;
import com.example.shows.model.network.dto.ScenaristDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Mapper(uses = {ActorMapping.class, ScenaristMapping.class, GenerMapping.class,BookingMapping.class})
public interface PerformanceMapping {
    @Mappings({
            @Mapping(target = "bookingsId",source = "bookings",qualifiedByName = "eToIdBooking"),
            @Mapping(target = "entity.genre",source = "genre")})
    PerformanceDto entityToDto(Performance entity);
    @Mappings({
            @Mapping(target = "bookings",source = "bookingsId",qualifiedByName = "idToEBooking"),
            @Mapping(target = "dto.genre",source = "genre")})
    Performance dtoToEntity(PerformanceDto dto);

    @Named("idToEBooking")
    default Collection<Booking> idToEBooking(Collection<Integer> idCollection){
        Collection<Booking> bookingCollection = new ArrayList<>();

        for (Integer id: idCollection) {
            Booking booking = new Booking();
            booking.setId(id);
            bookingCollection.add(booking);
        }
        return bookingCollection;
    }

    @Named("eToIdBooking")
    default Collection<Integer> eToIdBooking(Collection<Booking> bookingCollection){
       return bookingCollection.stream().map(CommonEntity::getId).collect(Collectors.toList());
    }

}
