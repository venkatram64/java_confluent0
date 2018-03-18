package com.venkat.avro.service;

import com.venkat.avro.consumer.CustomerV1Consumer;

public class CustomerV1ConsumerService {

    public void consumer(){
        CustomerV1Consumer customerV1Consumer = new CustomerV1Consumer();
        customerV1Consumer.consume();
    }

    public static void main(String[] args) {
        CustomerV1ConsumerService customerV1Service = new CustomerV1ConsumerService();
        customerV1Service.consumer();
    }
}
