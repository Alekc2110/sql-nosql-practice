package com.epam.alexkorsh.nosqlcouchbase.api.dto;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonFormat;
import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private Gender gender;
    private List<SportDto> sports;
}