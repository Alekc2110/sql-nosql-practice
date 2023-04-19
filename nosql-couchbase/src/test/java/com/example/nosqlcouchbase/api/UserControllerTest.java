package com.example.nosqlcouchbase.api;

import com.epam.alexkorsh.nosqlcouchbase.api.UserController;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.Gender;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.Sport;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.SportProficiency;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.User;
import com.epam.alexkorsh.nosqlcouchbase.domain.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController subject;


    @Test
    @DisplayName("Should find user by id")
    void getUserByIdTest() {
        //given
        String userId = "9e9a5147-8ebb-4344-a55a-b845aa6e2655";
        User user = getUser();
        when(userService.getUserById(any(String.class))).thenReturn(user);

        //when
        ResponseEntity<User> response = subject.getUserById(userId);

        //then
        assertThat(response.getBody().getId()).isEqualTo(UUID.fromString(userId));
    }

    @Test
    @DisplayName("Should find user by email")
    void getUserByEmailTest() {
        //given
        User user = getUser();
        when(userService.getUserByEmail(any(String.class))).thenReturn(user);

        //when
        ResponseEntity<User> response = subject.getUserByEmail("john_doe@epam.com");

        //then
        assertThat(response.getBody().getEmail()).isEqualTo("john_doe@epam.com");
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
        when(userService.getUserBySportName("Tennis")).thenReturn(users);

        //when
        List<User> userList = subject.getUserBySportName("Tennis");

        //then
        assertThat(userList.size()).isEqualTo(1);
        assertThat(userList).contains(user);

    }

    @Test
    @DisplayName("Should save user and return saved user")
    void createUserTest() {
        //given
        User user = getUser();
        when(userService.createUser(any(User.class))).thenReturn(user);

        //when
        ResponseEntity<User> responseEntity = subject.createUser(new User());

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isEqualTo(user);
    }

    @Test
    @DisplayName("Should return OkResponse with updatedUser when valid userId and sport given")
    void addSportToUserTest() {
        // given
        Sport sport = new Sport();
        sport.setSportName("Polo");
        sport.setSportProficiency(SportProficiency.ADVANCED);

        User user = getUser();
        user.setSports(new ArrayList<>(List.of(sport)));

        when(userService.addSportToUser(any(String.class), any(Sport.class))).thenReturn(user);

        // when
        ResponseEntity<User> responseEntity = subject.addSportToUser("9e9a5147-8ebb-4344-a55a-b845aa6e2655", sport);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(user);
    }



    @Test
    @DisplayName("Should return list of user found by query param existing in user document")
    void searchUsersTest() {
        //given
        List<User> users = Arrays.asList(
                new User(UUID.randomUUID(), "john_doe@epam.com", "John Doe", LocalDate.of(1990, 10, 10), Gender.MALE, List.of()),
                new User(UUID.randomUUID(), "jane_doe@epam.com", "Jane Doe", LocalDate.of(1995, 5, 15), Gender.FEMALE, List.of())
        );
        String query = "john";
        when(userService.search(query)).thenReturn(users);

        //when
        ResponseEntity<List<User>> response = subject.searchUsers(query);
        List<User> result = response.getBody();

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(users, result);
    }

    private static User getUser() {
        User user = new User();
        user.setId(UUID.fromString("9e9a5147-8ebb-4344-a55a-b845aa6e2655"));
        user.setEmail("john_doe@epam.com");
        user.setFullName("John Doe");
        user.setBirthDate(LocalDate.of(1990, 10, 10));
        user.setGender(Gender.MALE);
        return user;
    }

}