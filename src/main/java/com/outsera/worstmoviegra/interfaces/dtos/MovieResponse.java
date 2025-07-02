package com.outsera.worstmoviegra.interfaces.dtos;

import lombok.Data;

import java.util.List;

@Data
public class MovieResponse {

    private Integer releaseYear;
    private String title;
    private List<String> producers;
    private boolean winner;
}
