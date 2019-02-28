package com.prashanth.spring.reactive;

import com.prashanth.spring.reactive.model.Movie;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MovieRepository extends ReactiveCrudRepository<Movie, String> {

    Flux<Movie> findByTitle(String title);

}
