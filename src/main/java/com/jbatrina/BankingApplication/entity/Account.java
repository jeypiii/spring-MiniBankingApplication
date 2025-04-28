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
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int accountId;

    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;
 
    @NotNull
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "accountType_id", referencedColumnName = "typeId")
    private AccountType accountType;
    
    @NotNull
    @Embedded
    private Balance balance;
    
    // NOTE: @NotNull NOT applied since this is computed instead of supplied
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationTimeStamp;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime closureTimeStamp;
   
    public Account(User user, AccountType accountType, Balance balance) {
    	this.user = user;
    	this.balance = balance;
    	this.accountType = accountType;
    	this.creationTimeStamp = LocalDateTime.now();
    }
    
    public boolean hasBalance(Balance requestedBalance) {
    	return balance.hasAdequateBalance(requestedBalance);
    }
}