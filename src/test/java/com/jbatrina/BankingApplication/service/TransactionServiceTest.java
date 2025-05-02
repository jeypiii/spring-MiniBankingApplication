package com.jbatrina.BankingApplication.service;

import com.jbatrina.BankingApplication.repository.TransactionRepository;
import com.jbatrina.BankingApplication.exceptions.TransactionIdConflictException;
import com.jbatrina.BankingApplication.exceptions.TransactionNotFoundException;
import com.jbatrina.BankingApplication.exceptions.BankingApplicationException;
import com.jbatrina.BankingApplication.entity.Transaction;
import com.jbatrina.BankingApplication.entity.TransactionType;
import com.jbatrina.BankingApplication.entity.Account;
import com.jbatrina.BankingApplication.entity.AccountType;
import com.jbatrina.BankingApplication.entity.Balance;
import com.jbatrina.BankingApplication.entity.Role;
import com.jbatrina.BankingApplication.entity.User;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    TransactionService transactionService;

    User testNormalUser;
    User testAdminUser;
    Account testAccount;
    Transaction testTransaction;

    @BeforeEach
    void setup() {
        testNormalUser = new User();
        testNormalUser.setUserId(1);
        testNormalUser.setFirstName("testfirst");
        testNormalUser.setLastName("testlast");
        testNormalUser.setUsername("testuser");
        testNormalUser.setEmail("testuser@user.com");
        testNormalUser.setRoles(Set.of(new Role("USER")));
        testNormalUser.setPassword("testpass");

        testAdminUser = new User();
        testAdminUser.setUserId(2);
        testAdminUser.setFirstName("testAdminfirst");
        testAdminUser.setLastName("testAdminlast");
        testAdminUser.setUsername("testAdminuser");
        testAdminUser.setEmail("testAdminuser@user.com");
        testAdminUser.setRoles(Set.of(new Role("ADMIN")));
        testAdminUser.setPassword("testAdminpass");

        testAccount = new Account();
        testAccount.setAccountId(1);
        testAccount.setAccountNumber(100000001);
        testAccount.setUser(testNormalUser);
        testAccount.setAccountType(new AccountType("SAVINGS"));
        testAccount.setBalance(Balance.ofBase(10_000));;
        testAccount.setCreationTimeStamp(LocalDateTime.now());
    }

    @Test
    void test_getAllTransactions() {
        List<Transaction> allTransactions = new ArrayList<>();
        allTransactions.add(testTransaction);

        when(transactionRepository.findAll()).thenReturn(allTransactions);
        Page<Transaction> returnedTransactions = transactionService.getAllTransactionsOfAccountByPage(1, 0, -1);
        assert(returnedTransactions.getSize() > 1);
    }

    @Test
    void test_getTransaction_with_existing_transaction() {
        when(transactionRepository.findById(1)).thenReturn(Optional.ofNullable(testTransaction));
       Transaction returnedTransaction = transactionService.getTransaction(1);

        assertEquals(returnedTransaction, testTransaction);
    }

    @Test
    void test_getTransaction_with_nonexistent_transaction() {
        when(transactionRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        BankingApplicationException e = assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.getTransaction(1);
        });
    }

    @Test
    void test_addTransaction_new_transaction() {
        when(transactionRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        when(transactionRepository.save(testTransaction)).thenReturn(testTransaction);
        Transaction transaction = transactionService.addTransaction(testTransaction);

        assertEquals(transaction, testTransaction);
    }

    @Test
    void test_addTransaction_with_duplicate_transaction() {
        when(transactionRepository.findById(1)).thenReturn(Optional.ofNullable(testTransaction));
        BankingApplicationException e = assertThrows(TransactionIdConflictException.class, () -> {
            transactionService.addTransaction(testTransaction);
        });

        assertEquals("Attempting to add " + testTransaction, e.getContextMessage());
    }
}
