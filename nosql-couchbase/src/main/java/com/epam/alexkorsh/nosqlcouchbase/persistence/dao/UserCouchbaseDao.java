package com.epam.alexkorsh.nosqlcouchbase.persistence.dao;

import com.couchbase.client.core.error.CouchbaseException;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.search.SearchQuery;
import com.couchbase.client.java.search.result.SearchResult;
import com.couchbase.client.java.search.result.SearchRow;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.User;
import com.epam.alexkorsh.nosqlcouchbase.persistence.repository.couchbaseRepository.UserCBRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Repository
public class UserCouchbaseDao implements UserDao {

    private final UserCBRepository repository;
    private final Cluster cluster;

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
        return Optional.ofNullable(repository.findByEmail(email));
    }

    @Override
    public List<User> findBySportName(String sportName) {
        return repository.findBySportName(sportName);
    }

    @Override
    public List<User> searchUsers(String query) {
       final List<String> userIdsList = new ArrayList<>();
             List<User> foundUsers = new ArrayList<>();
        try {
            final SearchResult result = cluster.searchQuery(
                    "search_user_idx", SearchQuery.queryString(query));

            for (SearchRow row : result.rows()) {
                userIdsList.add(row.id());
            }

            List<UUID> uuidList = userIdsList.stream().map(UUID::fromString).toList();
            foundUsers = repository.findAllByIdIn(uuidList);

        } catch (CouchbaseException ex) {
            log.error("could not search by query: " + query, ex);
        }

        return foundUsers;
    }

}


