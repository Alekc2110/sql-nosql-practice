package com.epam.alexkorsh.nosqlcouchbase.persistence.config;

import com.epam.alexkorsh.nosqlcouchbase.persistence.listener.IndexCreationListener;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

/**
 * Configuration for using @TestContainers.
 * For tests Mongo Data
 */
@Configuration
public class UserDaoMongoTestContainersConfiguration {


    @Container
    public static MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:6.0.13"));


    static {
        mongoDBContainer.start();
        String[] strings = mongoDBContainer.getConnectionString().split(":");
        System.setProperty("mongoPort", strings[strings.length-1]);

    }

//    @DynamicPropertySource
//    static void mongoDbProperties(DynamicPropertyRegistry registry) {
//        registry.add("mongoBdUri", mongoDBContainer::getConnectionString);
//    }


    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:" + System.getProperty("mongoPort"));
    }

    @Bean
    public IndexCreationListener indexCreationListener(){
        return new IndexCreationListener();
    }

}
