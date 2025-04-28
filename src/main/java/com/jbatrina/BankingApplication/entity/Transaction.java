package com.jbatrina.BankingApplication.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int transactionId;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "transactionType_id", referencedColumnName = "typeId")
    private TransactionType transactionType;
    
    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    private Account sourceAccount;
 
    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "targetAccount_id", referencedColumnName = "accountId")
    private Account targetAccount;
    
    // TODO: make BalanceDelta class to represent addition/subtraction of finance (similar to Time vs TimeDelta)
    // TODO: make it possible to affect different balance type (e.g. transfer from deposit to credit/crypto etc.)
    // TODO: make final/non-changeable but also allow jackson/serialization to set affectedBalance
    @NotNull
    @Embedded
    private Balance affectedBalance;

    // NOTE: @NotNull NOT applied since this is computed instead of supplied
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationTimeStamp;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime closureTimeStamp;
   
    public Transaction(Account source, Account target, TransactionType transactionType, Balance affectedBalance) {
    	this.sourceAccount = source;
    	this.targetAccount = target;
    	this.affectedBalance = affectedBalance;
    	this.transactionType = transactionType;
    	this.creationTimeStamp = LocalDateTime.now();
    }
}