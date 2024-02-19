package com.epam.alexkorsh.nosqlcouchbase.api;

import com.epam.alexkorsh.nosqlcouchbase.api.dto.SportDto;
import com.epam.alexkorsh.nosqlcouchbase.api.dto.UserDto;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.Sport;
import com.epam.alexkorsh.nosqlcouchbase.domain.model.User;
import com.epam.alexkorsh.nosqlcouchbase.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        UserDto userDto = mapper.map(userService.getUserById(id), UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto userDto = mapper.map(userService.getUserByEmail(email), UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/sport/{sportName}")
    public List<UserDto> getUserBySportName(@PathVariable String sportName) {
        return userService.getUserBySportName(sportName)
                .stream().map(user -> mapper.map(user, UserDto.class)).toList();
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto savedUserDto = mapper.map(userService.createUser(mapper.map(userDto, User.class)), UserDto.class);
        return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/sport")
    public ResponseEntity<UserDto> addSportToUser(@PathVariable String id, @RequestBody SportDto sportDto) {
        UserDto updatedUserDto = mapper.map(
                userService.addSportToUser(id, mapper.map(sportDto, Sport.class)), UserDto.class);
        return ResponseEntity.ok(updatedUserDto);
    }

    @GetMapping("/search/user")
    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam("q") String query) {
        List<UserDto> foundUsersIds = userService.search(query).stream()
                .map(user -> mapper.map(user, UserDto.class)).toList();
        return ResponseEntity.ok(foundUsersIds);
    }

}
