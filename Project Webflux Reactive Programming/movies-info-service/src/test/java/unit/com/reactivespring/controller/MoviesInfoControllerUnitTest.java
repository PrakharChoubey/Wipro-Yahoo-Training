package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.service.MovieInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = MoviesInfoController.class)
@AutoConfigureWebTestClient
public class MoviesInfoControllerUnitTest  {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MovieInfoService movieInfoServiceMock;
    static String MOVIE_INFO_URL = "/v1/movs";


    @Test
    void getAllMoviesInfo(){
        var movieInfos = List.of(new MovieInfo(null, "Batman Begins",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null, "The Dark Knight",
                        2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight Rises",
                        2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

        when(movieInfoServiceMock.getAllMovieInfos()).thenReturn(Flux.fromIterable(movieInfos));
        webTestClient
                .get()
                .uri(MOVIE_INFO_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(3);
    }

    void addMovieInfo(){
        var movieInfo = new MovieInfo(null, "BOOTMAN",
                2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));

        when(movieInfoServiceMock.addMovieInfo(isA(MovieInfo.class))).thenReturn(Mono.just(
                new MovieInfo("mockId", "BOOTMAN",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"))
        ));

        webTestClient
                .post()
                .uri(MOVIE_INFO_URL)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    var savedMovie= movieInfoEntityExchangeResult.getResponseBody();
                    assert savedMovie!=null;
                    assert savedMovie.getMovieInfoId()!=null;
                    assertEquals("mockId",savedMovie.getMovieInfoId());
                });
    }

    @Test
    void updateMovieInfo(){
        var movieInfoId = "abc";
        var movieInfo = new MovieInfo(null, "BOOTMAN",
                2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));

        when(movieInfoServiceMock.updateMovieInfo(isA(MovieInfo.class),isA(String.class))).thenReturn(Mono.just(
                new MovieInfo(movieInfoId, "BOOTMAN",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"))
        ));

        webTestClient
                .put()
                .uri(MOVIE_INFO_URL+"/{id}",movieInfoId)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    var updatedMoiveInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assertNotNull(updatedMoiveInfo);
                    assertNotNull(updatedMoiveInfo.getMovieInfoId());
                    assertEquals("BOOTMAN",updatedMoiveInfo.getName());
                });
    }

}
