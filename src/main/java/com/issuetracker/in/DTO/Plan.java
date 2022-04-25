package com.issuetracker.in.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
public class Plan {

    @JsonProperty
    public String email;
    @JsonProperty
    public int weekPlannedFor;
    @JsonProperty
    public String storyId;
    @JsonProperty
    public int estimatedPoints;
}
