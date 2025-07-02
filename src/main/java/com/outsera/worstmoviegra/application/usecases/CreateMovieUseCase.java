package com.outsera.worstmoviegra.application.usecases;

import com.outsera.worstmoviegra.application.usecases.services.CsvReaderService;
import com.outsera.worstmoviegra.infrastructure.repositories.MovieRepository;
import com.outsera.worstmoviegra.interfaces.mappers.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateMovieUseCase {

    private final CsvReaderService csvReaderService;
    private final MovieMapper movieMapper;
    private final MovieRepository movieRepository;

    public void execute(){
      csvReaderService.readMovies()
               .stream().map(movieMapper::toMovie)
               .forEach(movieRepository::save);
    }
}
