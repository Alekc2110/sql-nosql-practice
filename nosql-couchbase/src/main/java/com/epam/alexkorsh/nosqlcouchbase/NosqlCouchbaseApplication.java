package com.epam.alexkorsh.nosqlcouchbase;

import com.epam.alexkorsh.nosqlcouchbase.domain.model.Gender;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.Sport;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.SportProficiency;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.User;
import com.epam.alexkorsh.nosqlcouchbase.persistence.dao.UserDao;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class NosqlCouchbaseApplication {
    @Autowired
    private UserDao userDao;

    public static void main(String[] args) {
        SpringApplication.run(NosqlCouchbaseApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner(){
        User user = User.builder()
                .fullName("Alex Korsh")
                .email("alexal@mail.com")
                .birthDate(LocalDate.of(1975, 10, 10))
                .gender(Gender.MALE)
                .sports(List.of(
                        Sport.builder().sportName("Golf").sportProficiency(SportProficiency.ADVANCED).build(),
                        Sport.builder().sportName("Football").sportProficiency(SportProficiency.EXPERT).build())
                )
                .build();


        return args -> {
            User saved = userDao.save(user);

            Optional<User> found = userDao.findById(saved.getId());
            System.out.println(found.orElseThrow(()-> new RuntimeException("cannot find user")));
        };
    }
}
