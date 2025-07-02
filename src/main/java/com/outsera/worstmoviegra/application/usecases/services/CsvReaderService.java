package com.outsera.worstmoviegra.application.usecases.services;

import com.opencsv.bean.CsvToBeanBuilder;
import com.outsera.worstmoviegra.application.exceptions.ApplicationException;
import com.outsera.worstmoviegra.interfaces.dtos.MovieCSV;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.util.List;

@Service
@Slf4j
public class CsvReaderService {

    private static final String ERROR_OPENING_FILE = "An error occurred while trying to read CSV file: %s";
    public List<MovieCSV> readMovies(){

        ClassPathResource resource = new ClassPathResource("movies/movielist.csv");

        try {
            return new CsvToBeanBuilder<MovieCSV>(
                    new InputStreamReader(resource.getInputStream())
            ).withType(MovieCSV.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(';')
                    .build()
                    .parse();
        } catch (Exception e){
            log.error(String.format(ERROR_OPENING_FILE, e.getMessage()));
            throw new ApplicationException(String.format(ERROR_OPENING_FILE, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR, e.getClass());
        }
    }
}
