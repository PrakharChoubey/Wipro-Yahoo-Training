package com.reactivespring.routes;

import com.reactivespring.domain.Review;
import com.reactivespring.exceptionHandler.GlobalErrorHandler;
import com.reactivespring.handler.ReviewHandler;
import com.reactivespring.repository.ReviewReactiveRepository;
import com.reactivespring.router.ReviewRouter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@WebFluxTest
@ContextConfiguration(classes = {ReviewRouter.class, ReviewHandler.class, GlobalErrorHandler.class})
@AutoConfigureWebTestClient
public class ReviewsUnitTest {

    @MockBean
    private ReviewReactiveRepository reviewReactiveRepository;

    @Autowired
    private WebTestClient webTestClient;

    static String REVIEW_URL="/v1/reviews";

    @Test
    void addReview(){
        var review=new Review(null,1L,"Awesome movie",9.0);

        when(reviewReactiveRepository.save(isA(Review.class)))
                .thenReturn(Mono.just(new Review("abc",1L,"Awesome movie",8.0)));

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
                    assert savedReview!=null;
                });
    }

    @Test
    void addReview_validation(){
        var review=new Review(null,null,"Awesome movie",-9.0);

        when(reviewReactiveRepository.save(isA(Review.class)))
                .thenReturn(Mono.just(new Review("abc",1L,"Awesome movie",8.0)));

        webTestClient
                .post()
                .uri(REVIEW_URL)
                .bodyValue(review)
                .exchange()
                .expectStatus()
                .isBadRequest().expectBody(String.class)
                .isEqualTo("rating.negative : please pass a non-negative value,review.movieInfoId : must not be null");
    }
}
