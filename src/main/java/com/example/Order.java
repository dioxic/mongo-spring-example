package com.example;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Order {

    @Id
    public ObjectId _id;

    public Integer orderId;

    public String status;

    public String details;

    public Order() {}

    public Order(Integer orderId, String status, String details) {
        this.orderId = orderId;
        this.status = status;
        this.details = details;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", status='" + status + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
