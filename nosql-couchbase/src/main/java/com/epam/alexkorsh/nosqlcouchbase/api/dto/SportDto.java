package com.epam.alexkorsh.nosqlcouchbase.api.dto;

import com.epam.alexkorsh.nosqlcouchbase.domain.model.SportProficiency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties("id")
public class SportDto {
    private String id;
    private String sportName;
    private SportProficiency sportProficiency;

}
