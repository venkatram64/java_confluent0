package com.venkat.avro.service;

import com.venkat.avro.config.KafkaConfig;
import com.venkat.avro.entity.Customer;
import com.venkat.avro.producer.CustomerV1Producer;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerV1ProducerService {

    @Autowired
    private KafkaConfig kafkaConfig;

    public void producer(){
        CustomerV1Producer customerV1Producer = new CustomerV1Producer();
        Customer customerV1 = Customer.newBuilder()
                .setFirstName("Venkatram")
                .setLastName("Veerareddy")
                .setAge(47)
                .setHeight(160.2f)
                .setWeight(52.4f)
                .setAutomatedEmail(true)
                .build();

        customerV1Producer.produce(customerV1.getFirstName(), customerV1);
    }

    public static void main(String[] args) {
        CustomerV1ProducerService customerV1Service = new CustomerV1ProducerService();
        customerV1Service.producer();
    }
}
