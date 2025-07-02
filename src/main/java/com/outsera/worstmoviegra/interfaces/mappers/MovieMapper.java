package com.outsera.worstmoviegra.interfaces.mappers;

import com.outsera.worstmoviegra.domain.Movie;
import com.outsera.worstmoviegra.interfaces.dtos.MovieCSV;
import com.outsera.worstmoviegra.interfaces.dtos.MovieResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface MovieMapper {

    @Mapping(source = "year", target = "releaseYear")
    Movie toMovie (MovieCSV movieCSV);

    @Mapping(source = "producers", target = "producers", qualifiedByName = "producersStrToList")
    MovieResponse toMovieResponse(Movie movie);

    @Named("producersStrToList")
    default List<String> producersStrToList(String producers){
        return List.of(producers.split("\\s*(,|\\band\\b)\\s*"))
                .stream().filter(str -> ! str.isBlank()).toList();
    }
}
