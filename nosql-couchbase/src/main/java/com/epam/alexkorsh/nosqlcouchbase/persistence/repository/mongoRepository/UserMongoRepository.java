package com.epam.alexkorsh.nosqlcouchbase.persistence.repository.mongoRepository;

import com.epam.alexkorsh.nosqlcouchbase.domain.model.Sport;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("mongo")
public interface UserMongoRepository extends MongoRepository<User, String> {

    User findUserByEmail(String email);
    List<User> findAllBy(TextCriteria criteria);
}