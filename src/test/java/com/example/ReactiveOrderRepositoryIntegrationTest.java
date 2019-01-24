package com.example;

import com.example.Order;
import com.example.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReactiveOrderRepositoryIntegrationTest {

    @Autowired
    OrderRepository repository;
    @Autowired
    ReactiveMongoOperations operations;

    @Before
    public void setUp() {
        operations.collectionExists(Order.class)
                .flatMap(exists -> exists ? operations.dropCollection(Order.class) : Mono.just(exists))
                .flatMap(o -> operations.createCollection(Order.class))
                .then()
                .block();

        repository
                .saveAll(Flux.just(new Order(111, "PENDING", "some fish"),
                        new Order(222, "PENDING", "some pies"),
                        new Order(333, "COMPLETE", "some bananas")))
                .then()
                .block();
    }

    @Test
    public void findByStatusWithStringQueryTest() {
        List<Order> pending = repository.findByStatus("PENDING")
                .collectList()
                .block();
        assertThat(pending).hasSize(2);
    }

    @Test
    public void findByStatusWithMonoQueryTest() {
        List<Order> pending = repository.findByStatus(Mono.just("PENDING"))
                .collectList()
                .block();
        assertThat(pending).hasSize(2);
    }


}