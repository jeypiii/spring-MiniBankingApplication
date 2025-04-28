package com.jbatrina.BankingApplication.service;

import com.jbatrina.BankingApplication.repository.AccountTypeRepository;
import com.jbatrina.BankingApplication.entity.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountTypeService {
    @Autowired
    AccountTypeRepository accountTypeRepository;

    public AccountType addAccountType(AccountType accountType) {
        Optional<AccountType> oldAccountType = accountTypeRepository.findByName(accountType.getName());
        if (oldAccountType.isPresent()) {
            // TODO: check if it's better to raise error here than silent ack of re-add attempt
            return oldAccountType.get();
        }

        accountTypeRepository.save(accountType);
        return accountType;
    }

    public AccountType addAccountType(String name) {
    	return addAccountType(new AccountType(name));
    }

    public Optional<AccountType> getAccountType(String name) {
        return accountTypeRepository.findByName(name);
    }
}
