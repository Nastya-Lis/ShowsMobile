package com.example.shows.model.network.dto;

import java.util.Collection;
import java.util.List;

import lombok.Data;


@Data
public class GenersDto extends AbstractDto{
    private String nameType;
    private Collection<Integer> performancesId;

}
