package com.example.reactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoService {

  private final List<String> fruits = List.of("Mango", "Orange", "Banana");


  public Flux<String> fruitsFlux() {
    return Flux.fromIterable(fruits).log();
  }

  public Flux<String> fruitsFluxMap() {
    return Flux.fromIterable(fruits)
            .map(String::toUpperCase);
  }

  public Flux<String> fruitsFluxFilter(int number) {
    return Flux.fromIterable(fruits)
            .filter(s -> s.length() > number);
  }

  public Flux<String> fruitsFluxFilterMap(int number) {
    return Flux.fromIterable(fruits)
            .filter(s -> s.length() > number)
            .map(String::toUpperCase);
  }

  public Flux<String> fruitsFluxFlatMap() {
    return Flux.fromIterable(fruits)
            .flatMap(s -> Flux.just(s.split("")))
            .log();
  }

  public Flux<String> fruitsFluxFlatMapAsync() {
    return Flux.fromIterable(fruits)
            .flatMap(s -> Flux.just(s.split(""))
                    .delayElements(Duration.ofMillis(
                            new Random().nextInt(1000)
                    )))
            .log();
  }

  public Flux<String> fruitsFluxConcatMap() {
    return Flux.fromIterable(fruits)
            .concatMap(s -> Flux.just(s.split(""))
                    .delayElements(Duration.ofMillis(
                            new Random().nextInt(1000)
                    )))
            .log();
  }

  public Mono<List<String>> fruitMonoFlatMap() {
    return Mono.just("Mango")
            .flatMap(s -> Mono.just(List.of(s.split(""))))
            .log();
  }

  public Flux<String> fruitMonoFlatMapMany() {
    return Mono.just("Mango")
            .flatMapMany(s -> Flux.just(s.split("")))
            .log();
  }

  public Flux<String> fruitsFluxTransform(int number) {

    Function<Flux<String>, Flux<String>> filterData
            = data -> data.filter(s -> s.length() > number);

    return Flux.fromIterable(fruits)
            .transform(filterData)
            .log();
  }

  public Flux<String> fruitsFluxTransformDefaultIfEmpty(int number) {

    Function<Flux<String>, Flux<String>> filterData
            = data -> data.filter(s -> s.length() > number);

    return Flux.fromIterable(fruits)
            .transform(filterData)
            .defaultIfEmpty("Default")
            .log();

  }

  public Flux<String> fruitsFluxTransformSwitchIfEmpty(int number) {

    Function<Flux<String>, Flux<String>> filterData
            = data -> data.filter(s -> s.length() > number);

    return Flux.fromIterable(fruits)
            .transform(filterData)
            .switchIfEmpty(Flux.just("Pineapple", "Jack Fruit")
                    .transform(filterData))
            .log();

  }

  public Flux<String> fruitsFluxConcat() {
    Flux<String> fruits = Flux.just("Mango", "Orange");
    Flux<String> veggies = Flux.just("Tomato", "Lemon");

    return Flux.concat(fruits, veggies);
  }

  public Flux<String> fruitsFluxConcatWith() {
    Flux<String> fruits = Flux.just("Mango", "Orange");
    Flux<String> veggies = Flux.just("Tomato", "Lemon");

    return fruits.concatWith(veggies);
  }


  public Flux<String> fruitsMonoConcatWith() {
    Mono<String> fruits = Mono.just("Mango");
    Mono<String> veggies = Mono.just("Tomato");

    return fruits.concatWith(veggies);
  }

  public Flux<String> fruitsFluxMerge() {
    Flux<String> fruits = Flux.just("Mango", "Orange")
            .delayElements(Duration.ofMillis(50));
    Flux<String> veggies = Flux.just("Tomato", "Lemon")
            .delayElements(Duration.ofMillis(75));

    return Flux.merge(fruits, veggies);
  }

  public Flux<String> fruitsFluxMergeWith() {
    Flux<String> fruits = Flux.just("Mango", "Orange")
            .delayElements(Duration.ofMillis(50));
    Flux<String> veggies = Flux.just("Tomato", "Lemon")
            .delayElements(Duration.ofMillis(75));

    return fruits.mergeWith(veggies);
  }

  public Flux<String> fruitsFluxMergeWithSequential() {
    Flux<String> fruits = Flux.just("Mango", "Orange")
            .delayElements(Duration.ofMillis(50));
    Flux<String> veggies = Flux.just("Tomato", "Lemon")
            .delayElements(Duration.ofMillis(75));

    return Flux.mergeSequential(fruits, veggies);
  }

  public Flux<String> fruitsFluxZip() {
    Flux<String> fruits = Flux.just("Mango", "Orange");
    Flux<String> veggies = Flux.just("Tomato", "Lemon");

    return Flux.zip(fruits, veggies,
            (first, second) -> first + second).log();
  }

  public Flux<String> fruitsFluxZipWith() {
    Flux<String> fruits = Flux.just("Mango", "Orange");
    Flux<String> veggies = Flux.just("Tomato", "Lemon");

    return fruits.zipWith(veggies,
            (first, second) -> first + second).log();
  }

  public Flux<String> fruitsFluxZipTuple() {
    Flux<String> fruits = Flux.just("Mango", "Orange");
    Flux<String> veggies = Flux.just("Tomato", "Lemon");
    Flux<String> moreVeggies = Flux.just("Potato", "Beans");

    return Flux.zip(fruits, veggies, moreVeggies)
            .map(objects -> objects.getT1() + objects.getT2() + objects.getT3());
  }

  public Mono<String> fruitsMonoZipWith() {
    Mono<String> fruits = Mono.just("Mango");
    Mono<String> veggies = Mono.just("Tomato");

    return fruits.zipWith(veggies,
            (first, second) -> first + second).log();
  }


  public Mono<String> fruitMono() {
    return Mono.just("Mango").log();
  }


  public Flux<String> fruitsFluxFilterDoOn(int number) {
    return Flux.fromIterable(fruits)
            .filter(s -> s.length() > number)
            .doOnNext(s -> {
              System.out.println("s = " + s);
            })
            .doOnSubscribe(subscription -> {
              System.out.println("subscription.toString() = " + subscription.toString());
            })
            .doOnComplete(() -> System.out.println("Completed!!!"));
  }


  public Flux<String> fruitsFluxOnErrorReturn() {
    return Flux.just("Apple", "Mango")
            .concatWith(Flux.error(
                    new RuntimeException("Exception Occurred")
            ))
            .onErrorReturn("Orange");
  }

  public Flux<String> fruitsFluxOnErrorContinue() {
    return Flux.just("Apple", "Mango", "Orange")
            .map(s -> {
              if (s.equalsIgnoreCase("Mango"))
                throw new RuntimeException("Exception Occurred");
              return s.toUpperCase();
            })
            .onErrorContinue((e, f) -> {
              System.out.println("e = " + e);
              System.out.println("f = " + f);
            });
  }

  public Flux<String> fruitsFluxOnErrorMap() {
    return Flux.just("Apple", "Mango", "Orange")
            .checkpoint("Error Checkpoint1")
            .map(s -> {
              if (s.equalsIgnoreCase("Mango"))
                throw new RuntimeException("Exception Occurred");
              return s.toUpperCase();
            })
            .checkpoint("Error Checkpoint2")
            .onErrorMap(throwable -> {
              System.out.println("throwable = " + throwable);
              return new IllegalStateException("From onError Map");
            });
  }

  public Flux<String> fruitsFluxOnError() {
    return Flux.just("Apple", "Mango", "Orange")
            .map(s -> {
              if (s.equalsIgnoreCase("Mango"))
                throw new RuntimeException("Exception Occurred");
              return s.toUpperCase();
            })
            .doOnError(throwable -> {
              System.out.println("throwable = " + throwable);

            });
  }
}
