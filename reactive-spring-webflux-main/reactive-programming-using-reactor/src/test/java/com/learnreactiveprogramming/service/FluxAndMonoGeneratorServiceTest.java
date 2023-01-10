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