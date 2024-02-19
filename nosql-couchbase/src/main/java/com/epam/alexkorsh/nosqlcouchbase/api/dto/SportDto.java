package com.epam.alexkorsh.nosqlcouchbase.api.dto;

import com.epam.alexkorsh.nosqlcouchbase.domain.model.SportProficiency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SportDto {
    private String id;
    private String sportName;
    private SportProficiency sportProficiency;

}
