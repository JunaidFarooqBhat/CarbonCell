package com.carboncell.assessment.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntryResponse {
    @JsonProperty("count")
    int count;
    @JsonProperty("entries")
    private ArrayList<Entry> entries;

    // getters and setters
}
