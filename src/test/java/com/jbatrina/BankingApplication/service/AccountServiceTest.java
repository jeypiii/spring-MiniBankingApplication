package com.jbatrina.BankingApplication.service;

import com.jbatrina.BankingApplication.repository.AccountRepository;
import com.jbatrina.BankingApplication.exceptions.AccountIdConflictException;
import com.jbatrina.BankingApplication.exceptions.AccountNotFoundException;
import com.jbatrina.BankingApplication.exceptions.BankingApplicationException;
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
public class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    User testNormalUser;
    User testAdminUser;
    Account testAccount;

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
    void test_getAllAccounts() {
        List<Account> allAccounts = new ArrayList<>();
        allAccounts.add(testAccount);

        when(accountRepository.findAll()).thenReturn(allAccounts);
        Page<Account> returnedAccounts = accountService.getAllAccountsByPage(0, -1);
        assert(returnedAccounts.getSize() > 1);
    }

    @Test
    void test_getAccount_with_existing_account() {
        when(accountRepository.findById(1)).thenReturn(Optional.ofNullable(testAccount));
       Account returnedAccount = accountService.getAccount(1);

        assertEquals(returnedAccount, testAccount);
    }

    @Test
    void test_getAccount_with_nonexistent_account() {
        when(accountRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        BankingApplicationException e = assertThrows(AccountNotFoundException.class, () -> {
            accountService.getAccount(1);
        });
    }

    @Test
    void test_addAccount_new_account() {
        when(accountRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        when(accountRepository.save(testAccount)).thenReturn(testAccount);
        Account account = accountService.addAccount(testAccount);

        assertEquals(account, testAccount);
    }

    @Test
    void test_addAccount_with_duplicate_account() {
        when(accountRepository.findById(1)).thenReturn(Optional.ofNullable(testAccount));
        BankingApplicationException e = assertThrows(AccountIdConflictException.class, () -> {
            accountService.addAccount(testAccount);
        });

        assertEquals("Attempting to add " + testAccount, e.getContextMessage());
    }

    @Test
    void test_removeAccount_with_existing_account() {
        when(accountRepository.findById(1)).thenReturn(Optional.ofNullable(testAccount));
        accountService.closeAccount(1);

        verify(accountRepository, times(1)).deleteById(1);
    }

    @Test
    void test_removeAccount_with_nonexistent_account() {
        when(accountRepository.findById(1)).thenReturn(Optional.ofNullable(null));
        BankingApplicationException e = assertThrows(AccountNotFoundException.class, () -> {
            accountService.closeAccount(1);
        });
    }
}
