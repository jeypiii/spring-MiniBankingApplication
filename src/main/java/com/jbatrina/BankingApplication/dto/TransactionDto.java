package com.jbatrina.BankingApplication.dto;

import java.time.LocalDateTime;

import com.jbatrina.BankingApplication.entity.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionDto {
    int transactionId;
    TransactionType transactionType;
    int sourceAccountId;
    int targetAccountId;
    BalanceDto affectedBalance;
    private LocalDateTime creationTimeStamp;
    private LocalDateTime closureTimeStamp;
}