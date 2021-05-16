package com.example.shows.model.network.dto;

import java.util.List;

import lombok.Data;

@Data
public class ActorDto {
    private String name;
    private String biography;

    private List<Integer> performances;

}
