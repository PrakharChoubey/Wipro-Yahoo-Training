package com.reactivespring.handler;

import com.reactivespring.domain.Review;
import com.reactivespring.exception.ReviewDataException;
import com.reactivespring.repository.ReviewReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.stream.Collectors;


@Component
@Slf4j
public class ReviewHandler {

    @Autowired
    private Validator validator;
    private ReviewReactiveRepository reviewReactiveRepository;

    public ReviewHandler(ReviewReactiveRepository reviewReactiveRepository) {
        this.reviewReactiveRepository = reviewReactiveRepository;
    }

    public Mono<ServerResponse> addReview(ServerRequest request) {

        return request.bodyToMono(Review.class)
                .doOnNext(this::validate)
                .flatMap(review -> reviewReactiveRepository.save(review))
                /*.flatMap(reviewReactiveRepository::save);*/
                .flatMap(savedReview->{
                    return ServerResponse.status(HttpStatus.CREATED)
                            .bodyValue(savedReview);
                });
    }

    private void validate(Review review) {
       var constraintViolation= validator.validate(review);
       log.info("constraintViolation : {}",constraintViolation);
       if (constraintViolation.size()>0){
       var errorMessage = constraintViolation
               .stream()
               .map(ConstraintViolation::getMessage)
               .sorted()
               .collect(Collectors.joining(","));
       throw new ReviewDataException(errorMessage);
       }
    }

    public Mono<ServerResponse> getReviews(ServerRequest request) {

        var movieInfoId = request.queryParam("movieInfoId");
        Logger logg= LoggerFactory.getLogger(ReviewHandler.class);
        logg.trace("hulululu {}",request);

        if(movieInfoId.isPresent()){
            return ServerResponse
                    .status(HttpStatus.FOUND)
                    .body(reviewReactiveRepository.findReviewsByMovieInfoId(Long.valueOf(movieInfoId.get())),Review.class);
        }else {
        return ServerResponse
                .status(HttpStatus.FOUND)
                .body(reviewReactiveRepository.findAll(),Review.class);
        }
    }

    public Mono<ServerResponse> updateReview(ServerRequest request) {

        var reviewId =request.pathVariable("id");
        var existingReview = reviewReactiveRepository.findById(reviewId);
        return existingReview
                .flatMap(review -> request.bodyToMono(Review.class)
                        .map(reqReview->{
                            review.setComment(reqReview.getComment());
                            review.setRating(reqReview.getRating());
                            return review;
                        })
                        .flatMap(reviewReactiveRepository::save)
                        .flatMap(savedReview->ServerResponse.ok().bodyValue(savedReview))
                );
    }

    public Mono<ServerResponse> deleteReview(ServerRequest request) {
        var reviewId=request.pathVariable("id");
        var existingReview=reviewReactiveRepository.findById(reviewId);
        return existingReview
                .flatMap(review -> reviewReactiveRepository.deleteById(reviewId)
                        .then(ServerResponse.noContent().build()));
    }
}
