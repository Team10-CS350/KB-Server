package com.example.demo.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

//    @NotEmpty
//    @Email
    private String email;

//    @Size(min = 8, max = 200)
    private String password;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "users_roles",
//            joinColumns = @JoinColumn(
//                    name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "id"))
//    private Collection< Role > roles;

}
