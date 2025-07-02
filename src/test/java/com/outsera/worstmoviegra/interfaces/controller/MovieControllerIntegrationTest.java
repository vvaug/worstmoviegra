package com.outsera.worstmoviegra.interfaces.controller;

import com.outsera.worstmoviegra.infrastructure.repositories.MovieRepository;
import com.outsera.worstmoviegra.interfaces.dtos.AwardIntervalResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MovieRepository movieRepository;

    @Test
    void findMoviesAwardInterval() {
        ResponseEntity<AwardIntervalResponse> response = restTemplate.getForEntity("/movies/award-interval", AwardIntervalResponse.class);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }
}