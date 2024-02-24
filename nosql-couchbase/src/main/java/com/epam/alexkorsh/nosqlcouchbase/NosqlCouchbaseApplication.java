package com.epam.alexkorsh.nosqlcouchbase;

import com.epam.alexkorsh.nosqlcouchbase.domain.model.Gender;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.Sport;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.SportProficiency;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.User;
import com.epam.alexkorsh.nosqlcouchbase.persistence.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class NosqlCouchbaseApplication {
    @Autowired
    private UserDao userDao;

    public static void main(String[] args) {
        SpringApplication.run(NosqlCouchbaseApplication.class, args);
    }


//    @Bean
    public CommandLineRunner commandLineRunner(){

        Sport sport1 = Sport.builder().sportName("Golf").sportProficiency(SportProficiency.INTERMEDIATE).build();
        Sport sport2 = Sport.builder().sportName("Volleyball").sportProficiency(SportProficiency.INTERMEDIATE).build();
        Sport sport3 = Sport.builder().sportName("Box").sportProficiency(SportProficiency.ADVANCED).build();
        Sport sport4 = Sport.builder().sportName("Baseball").sportProficiency(SportProficiency.EXPERT).build();



        return args -> {
//            Sport golfSaved = sportDao.save(golf);
//            Sport footballSaved = sportDao.save(football);

            User user1 = User.builder()
                    .fullName("Arnold Shvartz")
                    .email("arni@mail.com")
                    .birthDate(LocalDate.of(1951, 10,25))
                    .gender(Gender.MALE)
                    .sports(List.of(
                            sport1,
                            sport2
                    ))
                    .build();
            User user2 = User.builder()
                    .fullName("Tina Turner")
                    .email("tina@mail.com")
                    .birthDate(LocalDate.of(1965, 1,10))
                    .gender(Gender.FEMALE)
                    .sports(List.of(
                            sport3,
                            sport4
                    ))
                    .build();
            User saved = userDao.save(user1);
            userDao.save(user2);

            Optional<User> found = userDao.findById(saved.getId());
            System.out.println(found.orElseThrow(()-> new RuntimeException("cannot find user")));
        };
    }
}
