package com.book.api.user.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "username")
    private String userName;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;

    @OneToOne(fetch = FetchType.EAGER)
    private JwtToken token = new JwtToken();
}
