package com.jbatrina.BankingApplication.entity;

import jakarta.persistence.*;
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
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;
    // TODO: make role name type-safe
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
