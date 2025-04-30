package com.jbatrina.BankingApplication.dto;

import java.math.BigDecimal;

import com.jbatrina.BankingApplication.entity.Balance;
import com.jbatrina.BankingApplication.exceptions.BankingApplicationException;

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
public class BalanceDto {
	// NOTE: update whenever new balances are added
	@NotNull
	BigDecimal depositBalance;
	BigDecimal totalBalance;
	String currencyCode;
	
	public Balance toBalance() {
		if (depositBalance == null) {
			throw new BankingApplicationException(-1)
				.setContextMessage(
						"BalanceDto should be complete for conversion to Balance",
						"::BALANCEDTO_HASNULL"
					);
		}
		return Balance.ofBase(depositBalance);
	}

	public static BalanceDto of(Balance balance) {
		// TODO: don't assume that all balances use the same currency
		return new BalanceDto(
			/* depositBalance = */ balance.getDepositBalance().getNumberStripped(),
			/* totalBalance = */ balance.getNetBalance().getNumberStripped(),
			/* currencyCode = */ balance.getNetBalance().getCurrency().getCurrencyCode()
		);
	}
}
