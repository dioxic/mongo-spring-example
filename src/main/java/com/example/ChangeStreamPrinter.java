package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ChangeStreamOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;


@Component
public class ChangeStreamPrinter {
 
    private final Logger logger = LoggerFactory.getLogger(ChangeStreamPrinter.class);

    @Autowired
    private ReactiveMongoTemplate reactiveTemplate;

    private Instant lastTimestamp;

    private List<Integer> orderSubscriptions = new ArrayList<>();


    public void subscribe(Integer orderId) {
        logger.info("subscribing to {}", orderId);
        orderSubscriptions.add(orderId);
        createChangeStream();
    }

    private void createChangeStream() {
        logger.info("Opening changestream...");

        ChangeStreamOptions.ChangeStreamOptionsBuilder builder = ChangeStreamOptions.builder()
                .filter(newAggregation(match(where("operationType").is("insert")
                        .and("fullDocument.orderId").in(orderSubscriptions)
                        .and("ns.coll").is("order"))));

        if (lastTimestamp != null) {
            builder.resumeAt(lastTimestamp);
        }

        ChangeStreamOptions csOptions = builder.build();

        Flux<ChangeStreamEvent<Order>> changeStream = reactiveTemplate
                .changeStream("test", "order", csOptions, Order.class);

        changeStream.doOnNext(this::print).subscribe();
    }

    private void print(ChangeStreamEvent<Order> event) {
        lastTimestamp = event.getTimestamp();
        System.out.println("Order: " + event.getBody().toString());
    }
}