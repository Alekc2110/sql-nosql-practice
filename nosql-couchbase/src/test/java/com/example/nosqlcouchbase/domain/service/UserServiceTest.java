package com.example.nosqlcouchbase.domain.service;

import com.epam.alexkorsh.nosqlcouchbase.domain.exception.ResourceNotFoundException;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.Gender;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.Sport;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.SportProficiency;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.User;
import com.epam.alexkorsh.nosqlcouchbase.domain.service.UserService;
import com.epam.alexkorsh.nosqlcouchbase.persistence.dao.UserDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao repository;

    @InjectMocks
    private UserService subject;

    @Test
    @DisplayName("Should save user and return saved user")
    void createUserTest() {
        //given
        User user = getUser();
        when(repository.save(any(User.class))).thenReturn(user);

        //when
        User result = subject.createUser(user);

        //then
        assertThat(result).isEqualTo(user);
    }

    @Test
    @DisplayName("Should find user by id")
    void getUserByIdTest() {
        //given
        String userId = "9e9a5147-8ebb-4344-a55a-b845aa6e2655";
        User user = getUser();
        when(repository.findById(any(String.class))).thenReturn(Optional.of(user));

        //when
        User result = subject.getUserById(userId);

        //then
        assertThat(result.getId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException if didn`t find user by id")
    void getUserByIdThrowsExceptionTest() {
        //given
        String userId = "9e9a5147-8ebb-4344-a55a-b845aa6e2655";
        when(repository.findById(any(String.class))).thenThrow(ResourceNotFoundException.class);

        //then
        Assertions.assertThrows(ResourceNotFoundException.class, () -> subject.getUserById(userId));

    }

    @Test
    @DisplayName("Should find user by email")
    void getUserByEmailTest() {
        //given
        User user = getUser();
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        //when
        User result = subject.getUserByEmail("john_doe@epam.com");

        //then
        assertThat(result.getEmail()).isEqualTo("john_doe@epam.com");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException if didn`t find user by email")
    void getUserByEmailThrowsExceptionTest() {
        //given
        String email = "john_doe@epam.com";
        when(repository.findByEmail(any(String.class))).thenThrow(ResourceNotFoundException.class);

        //then
        Assertions.assertThrows(ResourceNotFoundException.class, () -> subject.getUserByEmail(email));
    }

    @Test
    @DisplayName("Should add given sport to user by userId")
    void addSportToUserTest() {
        //given
        User user = getUser();
        Sport sport = getSportActivity();
        User userWithSport = getUser();
        userWithSport.setSports(new ArrayList<>(List.of(sport)));

        String userId = "9e9a5147-8ebb-4344-a55a-b845aa6e2655";
        when(repository.findById(any(String.class))).thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenReturn(userWithSport);

        //when
        User result = subject.addSportToUser(userId, sport);

        //then
        assertThat(result.getSports().size()).isEqualTo(1);
        assertThat(result.getSports().get(0).getSportName()).isEqualTo("Football");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException if didn`t find user by userId when trying to add sport to user")
    void addSportToUserThrowsExceptionTest() {
        //given
        String userId = "9e9a5147-8ebb-4344-a55a-b845aa6e2655";
        when(repository.findById(any(String.class))).thenThrow(ResourceNotFoundException.class);

        //then
        Assertions.assertThrows(ResourceNotFoundException.class, () -> subject.addSportToUser(userId, new Sport()));
    }


    @Test
    @DisplayName("Should find user by sportName")
    void getUserBySportNameTest() {
        //given
        Sport sport = new Sport();
        sport.setSportName("Tennis");
        sport.setSportProficiency(SportProficiency.ADVANCED);

        User user = getUser();
        user.setSports(new ArrayList<>(List.of(sport)));
        ArrayList<User> users = new ArrayList<>(List.of(user));
        when(repository.findBySportName("Tennis")).thenReturn(users);

        //when
        List<User> userList = subject.getUserBySportName("Tennis");

        //then
        assertThat(userList.size()).isEqualTo(1);
        assertThat(userList).contains(user);
        assertThat(userList.get(0).getSports()).contains(sport);

    }

    @Test
    @DisplayName("Should return list of user found by query param existing in user document")
    void searchTest() {
        //given
        List<User> users = Arrays.asList(
                new User("9e9a5147-8ebb-4344-a55a-784515421252", "john_doe@epam.com", "John Doe", LocalDate.of(1990, 10, 10), Gender.MALE, List.of()),
                new User("9e9a5147-8ebb-4344-a55a-b8cdfd758755", "jane_doe@epam.com", "Jane Doe", LocalDate.of(1995, 5, 15), Gender.FEMALE, List.of())
        );
        String query = "john";
        when(repository.searchUsers(query)).thenReturn(users);

        //when
        List<User> userList = subject.search(query);

        //then
        assertEquals(2, userList.size());
        assertEquals(users, userList);
    }

    private static User getUser() {
        User user = new User();
        user.setId("9e9a5147-8ebb-4344-a55a-b845aa6e2655");
        user.setEmail("john_doe@epam.com");
        user.setFullName("John Doe");
        user.setBirthDate(LocalDate.of(1990, 10, 10));
        user.setGender(Gender.MALE);
        return user;
    }

    private static Sport getSportActivity() {
        Sport sport = new Sport();
        sport.setId("9e9a5147-8ebb-4344-a55a-b845aa6e7777");
        sport.setSportName("Football");
        sport.setSportProficiency(SportProficiency.EXPERT);
        return sport;
    }

}