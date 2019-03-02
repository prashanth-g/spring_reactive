package com.prashanth.spring.reactive;

import com.prashanth.spring.reactive.model.Movie;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringReactiveApplication {

    @Bean
    ApplicationRunner sampleData(MovieRepository movieRepository) {
        return args -> {
            movieRepository.deleteAll().thenMany(
                Flux.just("Spring is coming soon", "Spring: The future", "Spring is cool")
                        .map(Movie::new)
                        .flatMap(movieRepository::save))
                    .subscribe(System.out::println);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringReactiveApplication.class, args);
    }
}
