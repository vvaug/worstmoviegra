package com.outsera.worstmoviegra.interfaces.controller;

import com.outsera.worstmoviegra.application.usecases.FindMoviesAwardIntervalUseCase;
import com.outsera.worstmoviegra.interfaces.dtos.AwardIntervalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final FindMoviesAwardIntervalUseCase findMoviesAwardIntervalUseCase;

    @GetMapping("/award-interval")
    public AwardIntervalResponse findMoviesAwardInterval(){
        return findMoviesAwardIntervalUseCase.execute();
    }
}
