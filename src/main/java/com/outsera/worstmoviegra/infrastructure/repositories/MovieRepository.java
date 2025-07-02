package com.outsera.worstmoviegra.infrastructure.repositories;

import com.outsera.worstmoviegra.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
}
