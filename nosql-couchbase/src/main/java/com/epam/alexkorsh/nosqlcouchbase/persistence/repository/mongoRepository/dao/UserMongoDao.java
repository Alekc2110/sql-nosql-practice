package com.epam.alexkorsh.nosqlcouchbase.persistence.repository.mongoRepository.dao;

import com.epam.alexkorsh.nosqlcouchbase.domain.model.Sport;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.User;
import com.epam.alexkorsh.nosqlcouchbase.persistence.dao.UserDao;
import com.epam.alexkorsh.nosqlcouchbase.persistence.repository.mongoRepository.UserMongoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Profile("mongo")
@Slf4j
@AllArgsConstructor
@Repository
@Primary
public class UserMongoDao implements UserDao {

    private final UserMongoRepository repository;

    @Override
    public Optional<User> findById(String userId) {
        return repository.findById(userId);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(repository.findUserByEmail(email));
    }

    @Override
    public List<User> findAllBySportName(String sportName) {
        return searchUsers(sportName);
    }

    @Override
    public List<User> searchUsers(String query) {
        var criteria = TextCriteria.forDefaultLanguage().matchingAny(query);
        return repository.findAllBy(criteria);
    }

    @Override
    public void deleteUser(String userId) {
        repository.deleteById(userId);
    }
}


