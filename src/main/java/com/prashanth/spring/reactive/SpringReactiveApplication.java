package com.prashanth.spring.reactive;

import com.prashanth.spring.reactive.model.Movie;
import com.prashanth.spring.reactive.model.MovieEvent;
import com.prashanth.spring.reactive.service.MovieService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

@SpringBootApplication
public class SpringReactiveApplication {

    @Bean
    ApplicationRunner sampleData(MovieRepository movieRepository) {
        return args -> {
            movieRepository.deleteAll().thenMany(
                Flux.just("Spring is coming soon", "Spring: The future", "Spring is cool")
                        .map(Movie::new)
                        .flatMap(movieRepository::save))
                    .thenMany(movieRepository.findAll())
                    .subscribe(System.out::println);
        };
    }

    @Bean
    RouterFunction<?> routerFunction(MovieService ms) {
        return route(GET("/movies"),
                        request -> ServerResponse.ok().body(ms.getAllMovies(), Movie.class))
                .andRoute(GET("/movies/{id}"),
                        request -> ServerResponse.ok().body(ms.getMovieById(request.pathVariable("id")), Movie.class))
                .andRoute(GET("/movies/{id}/events"),
                        request -> ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(ms.getEvents(request.pathVariable("id")), MovieEvent.class));
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringReactiveApplication.class, args);
    }
}
