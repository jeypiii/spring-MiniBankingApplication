package com.jbatrina.BankingApplication.dto;

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
	int accountNumber;
	int userId;
	String ownerName;
	AccountType accountType;
	boolean isClosed;
}