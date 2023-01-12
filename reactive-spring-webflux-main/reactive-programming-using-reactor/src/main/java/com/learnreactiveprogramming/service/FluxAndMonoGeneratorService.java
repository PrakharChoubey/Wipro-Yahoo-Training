package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux(){
        return Flux.fromIterable(List.of("alex","pavan","ben","cloe"));
    }

    public Mono<String> nameMono(){
        return Mono.just("alex");
    }

    public Flux<String> namesFlux_map(){
        return Flux.fromIterable(List.of("alex","pavan","ben","cloe"))
                 .map(String::toUpperCase);
               // .map(s->s.toUpperCase());
    }

    public Flux<String> namesFlux_immutablity() {
        var namesList = List.of("alex", "ben", "chloe");
        //return Flux.just("alex", "ben", "chloe");

        var namesFlux = Flux.fromIterable(namesList);
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }

    public Flux<String> namesFlux_filter(int stringLenth){
        return Flux.fromIterable(List.of("alx","paan","ben","cloe","om"))
                .filter(s->s.length()>stringLenth)
                .map(s->s.length()+"-"+s)
                .log();
        // .map(s->s.toUpperCase());
    }

    public Flux<String> namesFlux_flatmap(int stringLenth){
        return Flux.fromIterable(List.of("alx","paan","ben","cloe","om"))
                .map(String::toUpperCase)
                .filter(s->s.length()>stringLenth)
                //.flatMap(s->splitString(s))
                .log();
        // .map(s->s.toUpperCase());
    }

    public Flux<String> namesFlux_transform(int stringLenth){
        Function<Flux<String>,Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s->s.length()>stringLenth)
                .flatMap(s->splitString(s));
        return Flux.fromIterable(List.of("alx","paan","ben","cloe","om"))
                .transform(filterMap)
                .defaultIfEmpty("default")
                .log();
        // .map(s->s.toUpperCase());
    }


    public Flux<String> namesFlux_flatmap_async(int stringLength) {
        var namesList = List.of("alex", "ben", "chloe"); // a, l, e , x
        return Flux.fromIterable(namesList)
                //.map(s -> s.toUpperCase())
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitString_withDelay);


    }

    public Flux<String> namesFlux_concatmap(int stringLength) {
        var namesList = List.of("alex", "ben", "chloe"); // a, l, e , x
        return Flux.fromIterable(namesList)
                //.map(s -> s.toUpperCase())
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                //.flatMap((name)-> splitString(name));
                .concatMap(this::splitString_withDelay);

    }

    public Mono<List<String>> namesMono_flatmap(int stringLength) {
        return Mono.just("alex")
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitStringMono); //Mono<List of A, L, E  X>
    }

    public Flux<String> explore_concat(){
       var abcFlux = Flux.just("A","B","C");
       var defFlux = Flux.just("D","E","F");
       return Flux.concat(abcFlux,defFlux).log();
    }

    // "A", "B", "C", "D", "E", "F"
    public Flux<String> explore_concatWith() {

        var abcFlux = Flux.just("A", "B", "C");

        var defFlux = Flux.just("D", "E", "F");

        return abcFlux.concatWith(defFlux).log();


    }

    public Flux<String> explore_concatWith_mono() {

        var aMono = Mono.just("A");

        var bMono = Flux.just("B");

        return aMono.concatWith(bMono);

    }

    public Flux<String> explore_merge(){
       var abcFlux = Flux.just("A","B","C")
               .delayElements(Duration.ofMillis(100));
       var defFlux = Flux.just("D","E","F")
               .delayElements(Duration.ofMillis(130));
       return Flux.merge(abcFlux,defFlux).log();
    }

    public Flux<String> explore_zip(){
        var abcFlux = Flux.just("A","B","C");
        var defFlux = Flux.just("D","E","F");
        return Flux.zip(abcFlux,defFlux,(first,second)->first+second).log();
    }

    public Flux<String> namesFlux_transform_switchIfEmpty(int stringLength) {

        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitString);

        var defaultFlux = Flux.just("default")
                .transform(filterMap); //"D","E","F","A","U","L","T"

        var namesList = List.of("alex", "ben", "chloe"); // a, l, e , x
        return Flux.fromIterable(namesList)
                .transform(filterMap) // gives u the opportunity to combine multiple operations using a single call.
                .switchIfEmpty(defaultFlux);
        //using "map" would give the return type as Flux<Flux<String>

    }
    public Flux<String> splitString(String name){
        var charArr=name.split("");
        return Flux.fromArray(charArr);
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService =new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFlux().subscribe(name-> System.out.println("name = " + name));
        fluxAndMonoGeneratorService.nameMono().subscribe(name-> System.out.println("MONO name = " + name));
        System.out.println(fluxAndMonoGeneratorService.namesFlux_transform(3));
    }
}
