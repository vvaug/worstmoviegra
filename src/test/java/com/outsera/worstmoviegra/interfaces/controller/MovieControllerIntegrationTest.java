package com.outsera.worstmoviegra.interfaces.controller;

import com.outsera.worstmoviegra.application.services.CsvReaderService;
import com.outsera.worstmoviegra.interfaces.dtos.AwardInterval;
import com.outsera.worstmoviegra.interfaces.dtos.AwardIntervalResponse;
import com.outsera.worstmoviegra.interfaces.dtos.MovieCSV;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CsvReaderService csvReaderService;

    @Test
    void findMoviesAwardInterval() {
        ResponseEntity<AwardIntervalResponse> response = restTemplate.getForEntity("/movies/award-interval", AwardIntervalResponse.class);
        var csvMovies = csvReaderService.readMovies();
        var responseBody = response.getBody();
        assertNotNull(responseBody);
        responseBody.getMin().forEach(award -> {
            assertTrue(movieExistsOnFile(csvMovies, award));
        });
        responseBody.getMax().forEach(award -> {
            assertTrue(movieExistsOnFile(csvMovies, award));
        });
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
    }

    private boolean movieExistsOnFile(List<MovieCSV> csvMovies, AwardInterval awardInterval){
        return csvMovies.stream().anyMatch(movie -> {
            return ( movie.getYear().equals(awardInterval.getFollowingWin()) ||
                        movie.getYear().equals(awardInterval.getPreviousWin() ) &&
                                movie.getProducers().contains(awardInterval.getProducer()));
        });
    }
}