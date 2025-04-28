package com.jbatrina.BankingApplication.dto;

import java.math.BigDecimal;

import com.jbatrina.BankingApplication.entity.AccountType;

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
	// TODO: use Balance to get all balance breakdown
	//		for now we only use totalBalance for simplicity
	BigDecimal totalBalance;
}