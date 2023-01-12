package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class FluxAndMonoGeneratorServiceTest {
    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFluxTest() {
       var namesFlux = fluxAndMonoGeneratorService.namesFlux();
        StepVerifier.create(namesFlux)
                .expectNext("alex","pavan","ben","cloe")
                .verifyComplete();
    }

    @Test
    void namesFlux_filter() {
        var nameFlx = fluxAndMonoGeneratorService.namesFlux_filter(3);
        StepVerifier.create(nameFlx)
                .expectNext("4-paan","4-cloe")
                .verifyComplete();
    }

    @Test
    void namesFlux_Immutability() {

        //given

        //when
        var stringFlux = fluxAndMonoGeneratorService.namesFlux_immutablity()
                .log();

        //then
        StepVerifier.create(stringFlux)
                //.expectNext("ALEX", "BEN", "CHLOE")
                .expectNextCount(3)
                .verifyComplete();


    }


    @Test
    void namesFlux_map() {

        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_map(stringLength).log();

        //then
        StepVerifier.create(namesFlux)
                //.expectNext("ALEX", "BEN", "CHLOE")
                .expectNext("4-ALEX", "5-CHLOE")
                .verifyComplete();

    }

    @Test
    void namesFlux_flatmap() {
        var nameFlx = fluxAndMonoGeneratorService.namesFlux_flatmap(3);
        StepVerifier.create(nameFlx)
                .expectNext("P","A","A","N","C","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFlux_transf() {
        var nameFlx = fluxAndMonoGeneratorService.namesFlux_transform(3);
        StepVerifier.create(nameFlx)
                .expectNext("P","A","A","N","C","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFlux_transfo_1() {
        var nameFlx = fluxAndMonoGeneratorService.namesFlux_transform(6);
        StepVerifier.create(nameFlx)
                //.expectNext("P","A","A","N","C","L","O","E")
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void explore_concat() {
        var abcdefFlux = fluxAndMonoGeneratorService.explore_concat();
        StepVerifier.create(abcdefFlux)
                .expectNext("A","B","C","D","E","F").verifyComplete();
    }

    @Test
    void namesFlux_flatmap_async() {

        //given
        int stringLength = 3;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_flatmap_async(stringLength).log();

        //then
        StepVerifier.create(namesFlux)
                /*.expectNext("0-A", "1-L", "2-E", "3-X")
                .expectNextCount(5)*/
                .expectNextCount(9)
                .verifyComplete();

    }

    @Test
    void namesFlux_transform_switchIfEmpty() {

        //given
        int stringLength = 6;

        //when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux_transform_switchIfEmpty(stringLength).log();

        //then
        StepVerifier.create(namesFlux)
                .expectNext("D", "E", "F", "A", "U", "L", "T")
                //.expectNextCount(5)
                .verifyComplete();

    }
    
    @Test
    void explore_merge() {
        var abcdefFlux = fluxAndMonoGeneratorService.explore_merge();
        StepVerifier.create(abcdefFlux)
                .expectNext("A","D","B","E","C","F").verifyComplete();
    }

    @Test
    void explore_zip() {
        var abcdefFlux = fluxAndMonoGeneratorService.explore_zip();
        StepVerifier.create(abcdefFlux).expectNext("AD","BE","CF").verifyComplete();
    }
}