package com.epam.alexkorsh.nosqlcouchbase.persistence.repository.mongoRepository.daoImpl;

import com.epam.alexkorsh.nosqlcouchbase.domain.model.Gender;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.Sport;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.SportProficiency;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.User;
import com.epam.alexkorsh.nosqlcouchbase.persistence.config.UserDaoMongoTestContainersConfiguration;
import com.epam.alexkorsh.nosqlcouchbase.persistence.dao.UserDao;
import com.epam.alexkorsh.nosqlcouchbase.persistence.listener.IndexCreationListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataMongoTest
@Testcontainers
@Import({UserDaoMongoTestContainersConfiguration.class, UserMongoDaoMongoTemplateTest.AdditionalConfig.class})
class UserMongoDaoMongoTemplateTest {

    @TestConfiguration
    static class AdditionalConfig {
        @Bean(value = "userMongoDaoMongoTemplate")
        public UserDao userMongoDaoMongoTemplate(MongoTemplate mongoTemplate) {
            return new UserMongoDaoMongoTemplate(mongoTemplate);
        }
    }

    @Autowired
    @Qualifier("userMongoDaoMongoTemplate")
    private UserDao subject;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IndexCreationListener listener;

    @Autowired
    private ApplicationContext context;

    @BeforeEach
    void setup() {
        List<User> users = createUserForPopulateDb();
        saveUsersToMongoDb(users);
        listener.initIndicesAfterStartup(new ContextRefreshedEvent(context));
    }


    @AfterEach
    void remove() {
        mongoTemplate.dropCollection("user");
    }


    @Test
    void findById() {
        //given
        User user = createTestUser();
        User expected = subject.save(user);
        //when
        Optional<User> result = subject.findById(expected.getId());
        //then
        result.ifPresent(u -> assertThat(u).isEqualTo(expected));
    }

    @Test
    void save() {
        //given
        User user = createTestUser();
        //when
        User saved = subject.save(user);
        //then
        Assertions.assertAll(()-> {
            assertThat(saved.getId()).isNotNull();
            assertThat(saved.getBirthDate()).isToday();
        });
    }

    @Test
    void findByEmail() {
        //given
        String searchEmail = "test1@mail.com";
        //when
        Optional<User> result = subject.findByEmail(searchEmail);
        //then
        result.ifPresent(user -> assertThat(user.getEmail()).isEqualTo(searchEmail));
    }

    @Test
    void findAllBySportName() {
        //given
        String sportName = "Golf";
        //when
        List<User> users = subject.findAllBySportName(sportName);
        //then
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void searchUsers() {
        //given
        String searchQuery = "INTERMEDIATE";
        //when
        List<User> users = subject.searchUsers(searchQuery);
        //then
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void deleteUser() {
        //given
        User user = createTestUser();
        User saved = subject.save(user);
        //when
        subject.deleteUser(saved.getId());
        //then
        Optional<User> result = subject.findById(saved.getId());
        assertThat(result).isEmpty();
    }

    private void saveUsersToMongoDb(List<User> users) {
        for (User u : users) {
            mongoTemplate.save(u);
        }
    }

    private List<User> createUserForPopulateDb() {
        Sport sport1 = Sport.builder().sportName("Golf").sportProficiency(SportProficiency.INTERMEDIATE).build();
        Sport sport2 = Sport.builder().sportName("Volleyball").sportProficiency(SportProficiency.INTERMEDIATE).build();
        Sport sport3 = Sport.builder().sportName("Box").sportProficiency(SportProficiency.ADVANCED).build();
        Sport sport4 = Sport.builder().sportName("Baseball").sportProficiency(SportProficiency.EXPERT).build();

        User user1 = User.builder()
                .fullName("Test1")
                .email("test1@mail.com")
                .birthDate(LocalDate.of(1951, 10, 25))
                .gender(Gender.MALE)
                .sports(List.of(
                        sport1,
                        sport2
                ))
                .build();
        User user2 = User.builder()
                .fullName("Test2")
                .email("test2@mail.com")
                .birthDate(LocalDate.of(1965, 1, 10))
                .gender(Gender.FEMALE)
                .sports(List.of(
                        sport3,
                        sport4
                ))
                .build();

        User user3 = User.builder()
                .fullName("Test3")
                .email("test3@mail.com")
                .birthDate(LocalDate.of(1965, 1, 10))
                .gender(Gender.FEMALE)
                .sports(List.of(
                        sport1,
                        sport4
                ))
                .build();
        return List.of(user1, user2, user3);
    }

    private User createTestUser() {
        return User.builder()
                .fullName("testuser")
                .gender(Gender.OTHER)
                .birthDate(LocalDate.now())
                .email("testuser@mail.com")
                .build();
    }
}