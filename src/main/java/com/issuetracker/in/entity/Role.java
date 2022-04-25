package com.issuetracker.in.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "tbl_roles")
@Table(name = "tbl_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int roleId;
    @NotNull
    private String roleName;

}
