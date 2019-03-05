package com.prashanth.spring.reactive.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Test
    public void getEventsTake5() {
        String movieId = movieService.getAllMovies().blockFirst().getId();

        StepVerifier.withVirtualTime(() -> movieService.getEvents(movieId).take(5))
                .thenAwait(Duration.ofHours(5))
                .expectNextCount(5)
                .verifyComplete();
    }
}