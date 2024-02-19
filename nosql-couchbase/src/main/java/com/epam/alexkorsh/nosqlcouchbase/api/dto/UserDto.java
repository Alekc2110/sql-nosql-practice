package com.epam.alexkorsh.nosqlcouchbase.api.dto;

import com.epam.alexkorsh.nosqlcouchbase.domain.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public
class UserDto {
    private String id;
    private String email;
    private String fullName;
    private LocalDate birthDate;
    private Gender gender;
    private List<SportDto> sportDtos;
}