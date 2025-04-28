package com.jbatrina.BankingApplication.dto;

import org.javamoney.moneta.Money;

import com.jbatrina.BankingApplication.entity.AccountType;
import com.jbatrina.BankingApplication.entity.Balance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDto {
	int accountId;
	int userId;
	String ownerName;
	AccountType accountType;
	Balance balance;
	Money totalBalance;
}