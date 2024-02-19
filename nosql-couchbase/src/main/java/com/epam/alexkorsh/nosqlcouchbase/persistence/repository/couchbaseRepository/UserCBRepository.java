package com.epam.alexkorsh.nosqlcouchbase.persistence.repository.couchbaseRepository;

import com.epam.alexkorsh.nosqlcouchbase.domain.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Profile("couchbase")
@Repository
public interface UserCBRepository extends CouchbaseRepository<User, String> {
    @Query("#{#n1ql.selectEntity} WHERE email = $1")
    User findByEmail(String email);

    @Query("#{#n1ql.selectEntity} WHERE ANY sport IN sports SATISFIES sport.sportName = $1 END")
    List<User> findBySportName(String sportName);

    List<User> findAllByIdIn(List<String> ids);

}