package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.repository.MovieInfoRepository;
import com.reactivespring.service.MovieInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
public class MoviesInfoController {

//    @Autowired
    private MovieInfoService movieInfoService;

//    @Autowired
    private MovieInfoRepository movieInfoRepository;

    //or use @Autowired
    public MoviesInfoController(MovieInfoService movieInfoService,
                                MovieInfoRepository movieInfoRepository) {
        this.movieInfoService = movieInfoService;
        this.movieInfoRepository = movieInfoRepository;
    }

    public MoviesInfoController(MovieInfoRepository movieInfoRepository) {
        this.movieInfoRepository = movieInfoRepository;
    }

    public MoviesInfoController(MovieInfoService movieInfoService) {
        this.movieInfoService = movieInfoService;
    }

    @PostMapping("/movs")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovie(@RequestBody @Valid MovieInfo movieInfo){
        return movieInfoService.addMovieInfo(movieInfo).log();
    }

    @GetMapping("/movs")
    public Flux<MovieInfo> getMoviesInfos(){
        return movieInfoService.getAllMovieInfos().log();
    }

    @GetMapping("/movs/{id}")
    public Mono<MovieInfo> getMovieInfoById(@PathVariable String id){

        return movieInfoService.getMovieInfoById(id);
    }

    @PutMapping("/movs/{id}")
    public Mono<ResponseEntity<MovieInfo>> updateMovieInfo(@RequestBody MovieInfo updatedMovieInfo, @PathVariable String id){
        return movieInfoService.updateMovieInfo(updatedMovieInfo,id)
                .map(ResponseEntity.ok()::body)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();
    }

    @DeleteMapping("/mov/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieInfo(@PathVariable String id){
        return movieInfoService.deleteMovieInfo(id);
    }

}
