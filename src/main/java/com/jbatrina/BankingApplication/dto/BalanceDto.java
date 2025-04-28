package com.jbatrina.BankingApplication.dto;

import java.math.BigDecimal;

import org.javamoney.moneta.Money;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jbatrina.BankingApplication.entity.Balance;
import com.jbatrina.BankingApplication.util.MoneyToBigDecimalConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Digits;
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
public class BalanceDto {
	// NOTE: update whenever new balances are added
	BigDecimal depositBalance;
	BigDecimal totalBalance;
	
	public static BalanceDto of(Balance balance) {
		return new BalanceDto(
			/* depositBalance = */ balance.getDepositBalance().getNumberStripped(),
			/* totalBalance = */ balance.getNetBalance().getNumberStripped()
		);
	}
}
