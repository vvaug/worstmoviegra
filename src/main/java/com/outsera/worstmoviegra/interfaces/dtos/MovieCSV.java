package com.outsera.worstmoviegra.interfaces.dtos;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class MovieCSV {

    @CsvBindByName
    private Integer year;
    @CsvBindByName
    private String title;
    @CsvBindByName
    private String studios;
    @CsvBindByName
    private String producers;
    @CsvBindByName
    private boolean winner;
}
