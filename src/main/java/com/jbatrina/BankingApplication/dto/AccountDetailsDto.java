package com.jbatrina.BankingApplication.dto;

import java.time.LocalDateTime;

import com.jbatrina.BankingApplication.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDetailsDto {
	int accountId;
	int accountNumber;
	User user;
	BalanceDto balance;
    LocalDateTime creationTimeStamp;
    LocalDateTime closureTimeStamp;
}