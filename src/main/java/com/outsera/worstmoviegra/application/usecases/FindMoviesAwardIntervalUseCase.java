package com.outsera.worstmoviegra.application.usecases;

import com.outsera.worstmoviegra.domain.Movie;
import com.outsera.worstmoviegra.infrastructure.repositories.MovieRepository;
import com.outsera.worstmoviegra.interfaces.dtos.AwardInterval;
import com.outsera.worstmoviegra.interfaces.dtos.AwardIntervalResponse;
import com.outsera.worstmoviegra.interfaces.dtos.MovieResponse;
import com.outsera.worstmoviegra.interfaces.mappers.MovieMapper;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindMoviesAwardIntervalUseCase {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private Map<String, List<Integer>> awardsByProducer = new HashMap<>();
    private List<AwardInterval> maxIntervalAwards = new ArrayList<>();
    private List<AwardInterval> minIntervalAwards = new ArrayList<>();

    private List<Interval> intervals = new ArrayList<>();

    public AwardIntervalResponse execute(){

        var winnerMovies = getWinnerMovies();
        getAwardsByProducer(winnerMovies);
        orderAwards();
        calculateInterval();

        var maxInterval = intervals.stream().max(Comparator.comparingInt(Interval::getInterval)).get().getInterval();
        var minInterval = intervals.stream().min(Comparator.comparingInt(Interval::getInterval)).get().getInterval();

        getMinAndMaxIntervalAwards(maxInterval, minInterval);

        return AwardIntervalResponse.builder()
                .min(this.minIntervalAwards)
                .max(this.maxIntervalAwards)
                .build();
    }

    private void getMinAndMaxIntervalAwards(int maxInterval, int minInterval) {
        this.intervals.forEach((interval) -> {
                if (interval.getInterval() == maxInterval){
                    this.maxIntervalAwards.add(AwardInterval.builder()
                            .previousWin(interval.getPrevious())
                            .followingWin(interval.getNext())
                            .interval(maxInterval)
                            .producer(interval.getProducer())
                            .build());
                } else if (interval.getInterval() == minInterval){
                    this.minIntervalAwards.add(AwardInterval.builder()
                            .previousWin(interval.getPrevious())
                            .followingWin(interval.getNext())
                            .interval(minInterval)
                            .producer(interval.getProducer())
                            .build());
                }
        });
    }

    private void calculateInterval() {
        this.awardsByProducer.forEach((producer, awards) -> {
            int totalAwards = awards.size();
            for (int i = 1; i < totalAwards; i++){
                if (totalAwards < 2){
                    continue;
                }
                int previous = awards.get(i - 1);
                int next = awards.get(i);
                intervals.add(Interval.builder()
                        .producer(producer)
                        .previous(previous)
                        .next(next)
                        .interval(next - previous)
                        .build());
            }
        });
    }

    private List<MovieResponse> getWinnerMovies() {
        return movieRepository.findAll().stream().map(movieMapper::toMovieResponse).filter(MovieResponse::isWinner).toList();
    }
    private void orderAwards() {
        this.awardsByProducer.forEach((k,v) -> v.sort(Comparator.naturalOrder()));
    }
    private void getAwardsByProducer(List<MovieResponse> winnerMovies) {
        winnerMovies.forEach(movie -> {
            movie.getProducers().forEach(producer -> {
                this.awardsByProducer.computeIfAbsent(producer, k -> new ArrayList<>())
                        .add(movie.getReleaseYear());
            });
        });

    }

    @Data
    @Builder
    static class Interval {
        private String producer;
        private int interval;
        private int previous;
        private int next;
    }
}
