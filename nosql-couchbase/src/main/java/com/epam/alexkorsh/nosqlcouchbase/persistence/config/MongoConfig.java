package com.epam.alexkorsh.nosqlcouchbase.persistence.config;

import com.mongodb.AuthenticationMechanism;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@Profile("mongo")
//@Configuration
//@EnableMongoRepositories(basePackages = {"com.epam.alexkorsh.nosqlcouchbase.persistence.repository.mongoRepository"})
public class MongoConfig /*extends AbstractMongoClientConfiguration */{
//    @Override
//    protected String getDatabaseName() {
//        return "mydb";
//    }
//
//    @Override
//    public MongoClient mongoClient() {
//       return MongoClients.create(
//               MongoClientSettings.builder().credential(
//                       MongoCredential
//                        .createCredential("root", "mydb", "example".toCharArray())
//                        .withMechanism(AuthenticationMechanism.SCRAM_SHA_1)
////                        .withMechanismProperty("mechanismPropertyKey", new String("root"))
//                        )
//               .build());
//
//    }
//
//    @Override
//    protected boolean autoIndexCreation() {
//        return true;
//    }

    //    @Bean
//    public MongoClient mongo() {
//        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/mydb");
//        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
//                .applyConnectionString(connectionString)
//                .build();
//
//        return MongoClients.create(mongoClientSettings);
//    }
//
//    @Bean
//    public MongoTemplate mongoTemplate() throws Exception {
//        return new MongoTemplate(mongo(), "mydb");
//   }


}
