package com.epam.alexkorsh.nosqlcouchbase.domain.service;

import com.epam.alexkorsh.nosqlcouchbase.domain.exception.ResourceNotFoundException;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.Sport;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.User;
import com.epam.alexkorsh.nosqlcouchbase.persistence.dao.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class UserService {

    private final UserDao repository;

    public User createUser(User user) {
        return repository.save(user);
    }

    public User getUserById(String userId) {
        return repository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found with id: " + userId));
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("user not found by email: " + email));
    }

    public User addSportToUser(String userId, Sport sport) {
        User user = repository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + userId));
        if (user.getSports() == null) {
            user.setSports(new ArrayList<>());
        }
        user.getSports().add(sport);
        return repository.save(user);
    }

    public List<User> getUserBySportName(String sportName) {
       return repository.findAllBySportName(sportName);
    }

    public List<User> search(String query) {
      return repository.searchUsers(query);
    }

    public void deleteUserById(String userId) {
        repository.deleteUser(userId);
    }

}
