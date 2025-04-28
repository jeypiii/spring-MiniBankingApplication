package com.jbatrina.BankingApplication.service;

import com.jbatrina.BankingApplication.repository.TransactionTypeRepository;
import com.jbatrina.BankingApplication.entity.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionTypeService {
    @Autowired
    TransactionTypeRepository accountTypeRepository;

    public TransactionType addTransactionType(TransactionType accountType) {
        Optional<TransactionType> oldTransactionType = accountTypeRepository.findByName(accountType.getName());
        if (oldTransactionType.isPresent()) {
            // TODO: check if it's better to raise error here than silent ack of re-add attempt
            return oldTransactionType.get();
        }

        accountTypeRepository.save(accountType);
        return accountType;
    }

    public TransactionType addTransactionType(String name) {
    	return addTransactionType(new TransactionType(name));
    }

    public Optional<TransactionType> getTransactionType(String name) {
        return accountTypeRepository.findByName(name);
    }
}
