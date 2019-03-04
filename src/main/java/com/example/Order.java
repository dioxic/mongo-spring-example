package com.example;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Document
public class Order {

    @Id
    private ObjectId id;

    @NotNull
    private Integer orderId;

    private String status;

    private String details;

    public Order() {}

    public Order(Integer orderId, String status, String details) {
        this.orderId = orderId;
        this.status = status;
        this.details = details;
    }

}
