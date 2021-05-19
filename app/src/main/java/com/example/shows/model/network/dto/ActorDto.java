package com.example.shows.model.network.dto;

import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class ActorDto extends AbstractDto{
    private String name;
    private String biography;
    private Set<Integer> performances;
}
