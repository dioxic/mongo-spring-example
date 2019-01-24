package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/order")
public class OrderReactiveController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ChangeStreamPrinter csPrinter;

    @GetMapping("/{id}")
    private Flux<Order> getOrderById(@PathVariable Integer id) {
        return orderRepository.findByOrderId(id);
    }

    @GetMapping
    private Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/subscribe")
    private void subscribeTo(@RequestParam("id") Integer id) {
        csPrinter.subscribe(id);
    }
}