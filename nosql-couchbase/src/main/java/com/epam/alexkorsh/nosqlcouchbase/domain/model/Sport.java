package com.epam.alexkorsh.nosqlcouchbase.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.index.QueryIndexed;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.mongodb.core.index.TextIndexed;

@Document/*for couchbase*/
@EqualsAndHashCode
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sport {
    @Id
    private String id;
    @QueryIndexed
    @TextIndexed
    private String sportName;
    private SportProficiency sportProficiency;

}
