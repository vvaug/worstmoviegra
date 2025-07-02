package com.outsera.worstmoviegra.infrastructure.config;

import com.outsera.worstmoviegra.application.usecases.CreateMovieUseCase;
import com.outsera.worstmoviegra.application.usecases.FindMoviesAwardIntervalUseCase;
import com.outsera.worstmoviegra.infrastructure.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfiguration implements CommandLineRunner{

    private final MovieRepository movieRepository;
    private final CreateMovieUseCase createMovieUseCase;

    public void run(String... args) throws Exception {
        createMovieUseCase.execute();
    }
}
