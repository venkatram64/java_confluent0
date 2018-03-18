package com.venkat.avro.consumer;

import com.venkat.avro.entity.Customer;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class CustomerV1Consumer {

    private String topicName;

    private KafkaConsumer<String, Customer> kafkaConsumer;

    public CustomerV1Consumer(){

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.99.100:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        properties.put("schema.registry.url", "http://192.168.99.100:8081");
        properties.put("group.id","my-avro-groupid");
        properties.put("enable.auto.commit", "false");
        properties.put("auto.offset.reset","earliest"); //from beginning
        properties.put("specific.avro.reader", "true");

        this.topicName = "customer11-avro";
        this.kafkaConsumer = new KafkaConsumer(properties);
        this.kafkaConsumer.subscribe(Collections.singleton(this.topicName));
        System.out.println("Waiting for data!!!");
    }

    public void consume(){

        while(true){
            ConsumerRecords<String, Customer> consumerRecords = kafkaConsumer.poll(100);
            //System.out.println("records size " + consumerRecords.count());
            for(ConsumerRecord<String, Customer> consumerRec : consumerRecords){
                Customer v1 = consumerRec.value();
                System.out.println(v1.toString());
            }
            kafkaConsumer.commitAsync();
        }
    }
}
