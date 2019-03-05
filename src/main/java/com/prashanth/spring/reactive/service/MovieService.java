package com.prashanth.spring.reactive.service;

import com.prashanth.spring.reactive.MovieRepository;
import com.prashanth.spring.reactive.model.Movie;
import com.prashanth.spring.reactive.model.MovieEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Flux<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    public Mono<Movie> getMovieById(String movieId) {
        return this.movieRepository.findById(movieId);
    }

    public Flux<MovieEvent> getEvents(String movieId) {
        return Flux.<MovieEvent>generate(sink -> sink.next(new MovieEvent(movieId, new Date())))
                .delayElements(Duration.ofSeconds(1));
    }
}
