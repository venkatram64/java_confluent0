package com.venkat.avro.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "KafkaDestinationSettings")
public class KafkaConfig {

    private String bootstrapServiceConfig;
    private String zookeeperUrl;
    private String topicName;
    private int acks;
    private int retries;
    private String schemaRegistryUrl;

    public KafkaConfig(){}

    public KafkaConfig(String bootstrapServiceConfig, String zookeeperUrl, String topicName,
                       int acks, int retries, String schemaRegistryUrl) {
        this.bootstrapServiceConfig = bootstrapServiceConfig;
        this.zookeeperUrl = zookeeperUrl;
        this.topicName = topicName;
        this.acks = acks;
        this.retries = retries;
        this.schemaRegistryUrl = schemaRegistryUrl;
    }

    public String getBootstrapServiceConfig() {
        return bootstrapServiceConfig;
    }

    public void setBootstrapServiceConfig(String bootstrapServiceConfig) {
        this.bootstrapServiceConfig = bootstrapServiceConfig;
    }

    public String getZookeeperUrl() {
        return zookeeperUrl;
    }

    public void setZookeeperUrl(String zookeeperUrl) {
        this.zookeeperUrl = zookeeperUrl;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getAcks() {
        return acks;
    }

    public void setAcks(int acks) {
        this.acks = acks;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public String getSchemaRegistryUrl() {
        return schemaRegistryUrl;
    }

    public void setSchemaRegistryUrl(String schemaRegistryUrl) {
        this.schemaRegistryUrl = schemaRegistryUrl;
    }
}
