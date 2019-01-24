package com.example;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Integer> {
  
    Flux<Order> findByStatus(String status);
    Flux<Order> findByStatus(Mono<String> status);
    Flux<Order> findByOrderId(Integer orderId);
    Flux<Order> findByOrderId(Mono<Integer> orderId);
}