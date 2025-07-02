package com.outsera.worstmoviegra.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "Movies")
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Integer releaseYear;
    private String title;
    private String studios;
    private String producers;
    private boolean winner;

}
