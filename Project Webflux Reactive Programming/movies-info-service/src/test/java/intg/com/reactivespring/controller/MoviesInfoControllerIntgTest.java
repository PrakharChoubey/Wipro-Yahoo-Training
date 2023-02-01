package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.repository.MovieInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class MoviesInfoControllerIntgTest {
    @Autowired
    MovieInfoRepository movieInfoRepository;

    @Autowired
    WebTestClient webTestClient;

    static String MOVIE_INFO_URL = "/v1/movs";

    @BeforeEach
    void setUp(){
        var movieInfos = List.of(new MovieInfo(null, "Batman Begins",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null, "The Dark Knight",
                        2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight Rises",
                        2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));
        movieInfoRepository.saveAll(movieInfos).blockLast();

    }

    @AfterEach
    void tearDown() {
        movieInfoRepository.deleteAll().block();
    }

    @Test
    void addMovieInfo(){
        var movieInfo = new MovieInfo(null, "BOOTMAN",
                2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));
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
                });
    }

    @Test
    void getAllMovieInfos(){
        webTestClient
                .get()
                .uri(MOVIE_INFO_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(2);
    }

    @Test
    void getAllMovieInfoByYear(){
        var uri = UriComponentsBuilder.fromUriString(MOVIE_INFO_URL)
                .queryParam("year",2005)
                .buildAndExpand()
                .toUri();
        webTestClient
                .get()
                .uri(uri)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(1);
    }

    @Test
    void getMovieInfoById(){
        var movieInfoId = "abc";
        webTestClient
                .get()
                .uri(MOVIE_INFO_URL+"/{id}",movieInfoId)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Dark Knight Rises");
//                .expectBody(MovieInfo.class)
//                .consumeWith(movieInfoEntityExchangeResult -> {
//                    var movieInfo = movieInfoEntityExchangeResult.getResponseBody();
//                    assertNotNull(movieInfo);
//                });
    }

    @Test
    void updateMovieInfo(){
        var movieInfo = new MovieInfo(null, "BOOTMAN",
                2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));

        var movieInfoId = "abc";
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

    @Test
    void updateMovieInfo_notFound(){
        var movieInfoId = "def";
        var movieInfo = new MovieInfo(null, "BOOTMAN",
                2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));

        webTestClient
                .put()
                .uri(MOVIE_INFO_URL+"/{id}",movieInfoId)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
        /*      .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    var updatedMoiveInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assertNotNull(updatedMoiveInfo);
                    assertNotNull(updatedMoiveInfo.getMovieInfoId());
                    assertEquals("BOOTMAN",updatedMoiveInfo.getName());
                });*/
    }

    @Test
    void deleteMovieInfo(){
        var movieInfoId = "abc";
        webTestClient
                .delete()
                .uri(MOVIE_INFO_URL+"/{id}",movieInfoId)
                .exchange()
                .expectStatus().isOk();
//
//                .is2xxSuccessful()
//                .expectBody(MovieInfo.class)
//                .consumeWith(movieInfoEntityExchangeResult -> {
//                    var deletedMovie = movieInfoEntityExchangeResult.getResponseBody();
//                   assertNotNull(deletedMovie);
//                });
    }


}