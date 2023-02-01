package com.reactivespring.routes;

import com.reactivespring.domain.Review;
import com.reactivespring.repository.ReviewReactiveRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class ReviewsIntgTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ReviewReactiveRepository reviewReactiveRepository;

    static String REVIEW_URL="/v1/reviews";
    @BeforeEach
    void setup(){
        var reviewList= List.of(
                new Review(null,1L,"Awesome movie",9.0),
                new Review(null,1L,"Awesome Movie2",8.0),
                new Review(null,2L,"Okay Movie",7.0)
        );
        reviewReactiveRepository.saveAll(reviewList)
                .blockLast();
    }
    @AfterEach
    void tearDown(){
        reviewReactiveRepository.deleteAll().block();
    }

    @Test
    void addReview(){
         var review =  new Review(null,1L,"Awesome Movie2",8.0);

         webTestClient
                 .post()
                 .uri(REVIEW_URL)
                 .bodyValue(review)
                 .exchange()
                 .expectStatus()
                 .isCreated()
                 .expectBody(Review.class)
                 .consumeWith(reviewEntityExchangeResult -> {
                    var savedReview=reviewEntityExchangeResult.getResponseBody();
                    assert savedReview.getReviewId()!=null;
                    assert savedReview.getMovieInfoId()!=null;

                 });
    }
}
