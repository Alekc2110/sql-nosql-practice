package com.epam.alexkorsh.nosqlcouchbase.persistence.dao;

import com.epam.alexkorsh.nosqlcouchbase.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(String userId);

    User save(User user);

    Optional<User> findByEmail(String email);

    List<User> findBySportName(String sportName);

    List<User> searchUsers(String query);
}
