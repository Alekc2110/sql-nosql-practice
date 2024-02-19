package com.epam.alexkorsh.nosqlcouchbase.domain.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.index.QueryIndexed;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.UUID;

@Document
@org.springframework.data.mongodb.core.mapping.Document
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sport {
    @Id
    private String id;
    @QueryIndexed
    private String sportName;
    private SportProficiency sportProficiency;

}
