package com.issuetracker.in.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class IssueResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long issueId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String issueType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;
    private String createdBy;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String assignedTo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String priority;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int estimatedPoints;
}
