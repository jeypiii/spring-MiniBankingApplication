package com.jbatrina.BankingApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "accountTypes")
public class AccountType {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int typeId;

	private String name;
	
	public AccountType(String name) {
		this.name = name;
	}
}
