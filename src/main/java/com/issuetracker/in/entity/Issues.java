package com.issuetracker.in.entity;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity(name="Issues")
@Table(name="tbl_issues")
public class Issues implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 5)
    private IssueType issueType;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ASSIGNEE", referencedColumnName = "username")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name="ASSIGNED_TO",referencedColumnName = "username")
    private User AssignedTo;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Priority priority;

    private int estimatedPoints;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

}
