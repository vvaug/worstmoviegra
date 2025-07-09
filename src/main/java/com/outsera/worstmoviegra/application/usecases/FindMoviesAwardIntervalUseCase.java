package com.outsera.worstmoviegra.application.usecases;

import com.outsera.worstmoviegra.infrastructure.repositories.MovieRepository;
import com.outsera.worstmoviegra.interfaces.dtos.AwardInterval;
import com.outsera.worstmoviegra.interfaces.dtos.AwardIntervalResponse;
import com.outsera.worstmoviegra.interfaces.dtos.Interval;
import com.outsera.worstmoviegra.interfaces.dtos.MovieResponse;
import com.outsera.worstmoviegra.interfaces.mappers.MovieMapper;
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

    public AwardIntervalResponse execute(){

        var winnerMovies = getWinnerMovies();
        var awards = getAwardsByProducer(winnerMovies);
        orderAwards(awards);

        var intervals = calculateInterval(awards);

        var maxInterval = intervals.stream().max(Comparator.comparingInt(Interval::getInterval)).get().getInterval();
        var minInterval = intervals.stream().min(Comparator.comparingInt(Interval::getInterval)).get().getInterval();

        return getMinAndMaxIntervalAwards(intervals, maxInterval, minInterval);

    }

    private AwardIntervalResponse getMinAndMaxIntervalAwards(List<Interval> intervals, int maxInterval, int minInterval) {
        List<AwardInterval> maxIntervalAwards = new ArrayList<>();
        List<AwardInterval> minIntervalAwards = new ArrayList<>();
        intervals.forEach((interval) -> {
                if (interval.getInterval() == maxInterval){
                    maxIntervalAwards.add(AwardInterval.builder()
                            .previousWin(interval.getPrevious())
                            .followingWin(interval.getNext())
                            .interval(maxInterval)
                            .producer(interval.getProducer())
                            .build());
                } else if (interval.getInterval() == minInterval){
                    minIntervalAwards.add(AwardInterval.builder()
                            .previousWin(interval.getPrevious())
                            .followingWin(interval.getNext())
                            .interval(minInterval)
                            .producer(interval.getProducer())
                            .build());
                }
        });

        return AwardIntervalResponse.builder()
                .min(minIntervalAwards)
                .max(maxIntervalAwards)
                .build();
    }

    private List<Interval> calculateInterval(Map<String, List<Integer>> awards) {
        List<Interval> intervals = new ArrayList<>();
        awards.forEach((producer, awardYear) -> {
            int totalAwards = awardYear.size();
            for (int i = 1; i < totalAwards; i++){
                if (totalAwards < 2){
                    continue;
                }
                int previous = awardYear.get(i - 1);
                int next = awardYear.get(i);
                intervals.add(Interval.builder()
                        .producer(producer)
                        .previous(previous)
                        .next(next)
                        .interval(next - previous)
                        .build());
            }
        });
        return intervals;
    }

    private List<MovieResponse> getWinnerMovies() {

        return movieRepository.findAll().stream().map(movieMapper::toMovieResponse).filter(MovieResponse::isWinner).toList();
    }
    private void orderAwards(Map<String, List<Integer>> awards) {
        awards.forEach((k,v) -> v.sort(Comparator.naturalOrder()));
    }
    private Map<String, List<Integer>> getAwardsByProducer(List<MovieResponse> winnerMovies) {
        var awardsByProducer = new HashMap<String, List<Integer>>();
        winnerMovies.forEach(movie -> {
            movie.getProducers().forEach(producer -> {
                awardsByProducer.computeIfAbsent(producer, k -> new ArrayList<>())
                        .add(movie.getReleaseYear());
            });
        });
        return awardsByProducer;
    }

}
