package com.epam.alexkorsh.nosqlcouchbase.persistence.repository.mongoRepository.daoImpl;

import com.epam.alexkorsh.nosqlcouchbase.domain.model.User;
import com.epam.alexkorsh.nosqlcouchbase.persistence.dao.UserDao;
import com.mongodb.client.result.DeleteResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Profile("mongo")
@Slf4j
@AllArgsConstructor
@Repository
public class UserMongoDaoMongoTemplate implements UserDao {

    private final MongoTemplate template;

    @Override
    public Optional<User> findById(String userId) {
        log.info("find by {} using MongoTemplate", userId);
        return Optional.ofNullable(template.findById(userId, User.class));
    }

    @Override
    public User save(User user) {
        log.info("save new user: {} using MongoTemplate", user);
        return template.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        log.info("find by email: {} using MongoTemplate", email);
       return Optional.ofNullable(
               template.findOne(Query.query(Criteria.where("email").is(email)), User.class));
    }

    @Override
    public List<User> findAllBySportName(String sportName) {
        log.info("find all users by sportName: {} using MongoTemplate", sportName);
        return searchUsers(sportName);
    }

    @Override
    public List<User> searchUsers(String query) {
        log.info("find all users by: {} using MongoTemplate", query);
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(query);
        Query searchQuery = new TextQuery(criteria);
        return template.find(searchQuery, User.class);
    }

    @Override
    public void deleteUser(String userId) {
        log.info("delete user with id: {} using MongoTemplate", userId);
        Query query = Query.query(Criteria.where("id").is(userId));
        DeleteResult deleteResult = template.remove(query, User.class);
        System.out.println(deleteResult);
    }

}


