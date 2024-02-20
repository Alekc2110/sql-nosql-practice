package com.epam.alexkorsh.nosqlcouchbase.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.index.QueryIndexed;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Document/*for couchbase*/
@org.springframework.data.mongodb.core.mapping.Document
@EqualsAndHashCode
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    private String id;
    @QueryIndexed
    @Indexed(unique = true)
    private String email;
    @TextIndexed(weight = 3)
    @Field("fullName")
    private String fullName;
    private LocalDate birthDate;
    private Gender gender;
//    @TextIndexed //possible to put here
    private List<Sport> sports;
}