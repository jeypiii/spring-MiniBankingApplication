package com.jbatrina.BankingApplication.entity;

import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int userId;

    // personal detail(s)
    @NotEmpty(message = "User must have first name")
    private String firstName;
    @Column(nullable = true)
    private String middleName;
    @NotEmpty(message = "User must have last name")
    private String lastName;

    // login details
    @NotEmpty
    @NotEmpty(message = "User must have (unique) username")
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @Column(nullable = false)
    @NotEmpty
    @NotEmpty(message = "User must have password")
    private String password;

    @ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId")
    )
    private Set<Role> roles;

    public User(String firstName, String middleName, String lastName, String username, String email, String password, Set<Role> roles) {
    	this.firstName = firstName;
    	this.middleName = middleName;
    	this.lastName = lastName;
    	this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
    
    @Override
    public String toString() {
        final boolean hasMiddleName = middleName != null && !middleName.isBlank();

        return String.format("%s, %s%s", lastName.toUpperCase(), firstName, hasMiddleName ? (" " + middleName) : "");
    }
}
