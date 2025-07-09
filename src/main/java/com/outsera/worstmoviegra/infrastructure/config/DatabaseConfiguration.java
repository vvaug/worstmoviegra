package com.outsera.worstmoviegra.infrastructure.config;

import com.outsera.worstmoviegra.application.usecases.CreateMovieUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfiguration implements CommandLineRunner{

    private final CreateMovieUseCase createMovieUseCase;

    public void run(String... args) throws Exception {
        createMovieUseCase.execute();
    }
}
