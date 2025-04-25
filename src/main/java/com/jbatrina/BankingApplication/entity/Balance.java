package com.jbatrina.BankingApplication.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Balance {
	@JsonIgnore
	private static String baseCurrencyCode = "PHP";

	@NotNull
	@Column(nullable = false)
	private Double depositBalance;
	
	// open for extension - other types of balances can be added
	// e.g. creditBalance
	
	public Double getNetBalance() {
		return depositBalance;
	}
}
