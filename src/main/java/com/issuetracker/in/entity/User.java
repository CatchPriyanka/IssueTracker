package com.issuetracker.in.entity;


import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity(name = "tbl_users")
@Table(name = "tbl_users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @Column(unique = true)
    private String username;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;

    @OneToOne
    @JoinColumn(name = "fk_role_id", referencedColumnName = "roleId")
    private Role role;

    @Column
    private boolean isActive =true;


}
