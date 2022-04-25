package com.issuetracker.in.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.issuetracker.in.entity.IssueType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class IssueDTO {

    @JsonProperty
    private IssueType issueType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;
    @JsonProperty
    private String description;
    @JsonProperty
    private String  assignee;
    @JsonProperty
    private String assignedTo;
    @JsonProperty
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String priority;

    @JsonInclude()
    private int estimatedPoints;


}
