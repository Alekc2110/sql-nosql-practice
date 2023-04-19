package com.epam.alexkorsh.nosqlcouchbase.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.index.QueryIndexed;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.UUID;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sport {
    @Id
    private UUID id;
    @QueryIndexed
    private String sportName;
    private SportProficiency sportProficiency;

}
