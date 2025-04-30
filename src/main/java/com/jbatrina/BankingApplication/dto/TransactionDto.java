package com.jbatrina.BankingApplication.dto;

import java.time.LocalDateTime;

import com.jbatrina.BankingApplication.entity.TransactionType;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    TransactionType transactionType;
    @NotNull
    Integer sourceAccountId;
    @NotNull
    Integer sourceAccountNumber;
    String sourceAccountOwnerName;
    @NotNull
    Integer targetAccountId;
    @NotNull
    Integer targetAccountNumber;
    String targetAccountOwnerName;
    @NotNull
    BalanceDto affectedBalance;
    private LocalDateTime creationTimeStamp;
    private LocalDateTime closureTimeStamp;
}