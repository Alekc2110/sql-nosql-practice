package com.epam.alexkorsh.nosqlcouchbase.persistence.repository.mongoRepository;

import com.epam.alexkorsh.nosqlcouchbase.domain.model.User;
import com.epam.alexkorsh.nosqlcouchbase.persistence.dao.UserDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
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
        return Optional.ofNullable(template.findById(userId, User.class));
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public List<User> findBySportName(String sportName) {
        return List.of();
    }

    @Override
    public List<User> searchUsers(String query) {
        return List.of();
    }

    @Override
    public void deleteUser(String userId) {

    }

}


