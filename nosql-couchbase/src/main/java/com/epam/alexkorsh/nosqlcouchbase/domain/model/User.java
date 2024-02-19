package com.epam.alexkorsh.nosqlcouchbase.domain.model;

import com.couchbase.client.core.deps.com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.index.QueryIndexed;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Document
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
    @Field("fullName")
    private String fullName;
    private LocalDate birthDate;
    private Gender gender;
    private List<Sport> sports;
}