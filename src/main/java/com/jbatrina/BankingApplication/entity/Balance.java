package com.jbatrina.BankingApplication.entity;

import java.math.BigDecimal;

import org.javamoney.moneta.Money;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Balance {
	@JsonIgnore
	private static String baseCurrencyCode = "PHP";

	// NOTE: money stored in database as number with 19 digits and 4 decimal places
	//		see https://stackoverflow.com/questions/582797/should-you-choose-the-money-or-decimalx-y-datatypes-in-sql-server
	//      and https://opendata.stackexchange.com/questions/10346/what-specifications-are-out-there-for-the-precision-required-to-store-money
	// NOTE: update BalanceDTO whenever new balance types are added
	@NotNull
	@Digits(integer = 19, fraction = 4)
	@Column(nullable = false, columnDefinition = "DECIMAL(19,4)")
	@Convert(converter = MoneyToBigDecimalConverter.class)
	private Money depositBalance;
	
	// open for extension - other types of balances can be added
	// e.g. creditBalance
	
	public Money getNetBalance() {
		return depositBalance;
	}
	
	public Balance getAbsoluteValue() {
		// NOTE: update BalanceDTO whenever new balance types are added
		return new Balance(
				depositBalance.abs()
			);
	}
	
	public Balance withdraw(Balance other) {
		// NOTE: update BalanceDTO whenever new balance types are added
		depositBalance.subtract(other.getAbsoluteValue().getDepositBalance());
		
		return this;
	}

	public Balance deposit(Balance other) {
		// NOTE: update BalanceDTO whenever new balance types are added
		depositBalance.add(other.getAbsoluteValue().getDepositBalance());
		
		return this;
	}
	
	public boolean hasAdequateBalance(Balance requestedBalance) {
		// NOTE: update BalanceDTO whenever new balance types are added
		if (! (depositBalance.isGreaterThanOrEqualTo(requestedBalance.getDepositBalance()))) {
			return false;
		}
		
		return true;
	}
	
	public static Balance ofBase(BigDecimal amount) {
		return new Balance(Money.of(amount, baseCurrencyCode));
	}

	public static Balance ofBase(int amount) {
		return new Balance(Money.of(amount, baseCurrencyCode));
	}

	public static Balance of(BigDecimal amount, String currencyCode) {
		return new Balance(Money.of(amount, currencyCode));
	}
}
