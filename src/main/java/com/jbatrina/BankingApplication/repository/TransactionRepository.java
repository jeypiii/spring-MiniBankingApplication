package com.jbatrina.BankingApplication.repository;

import com.jbatrina.BankingApplication.entity.Transaction;
import com.jbatrina.BankingApplication.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	Page<Transaction> findAllBySourceAccountAccountIdOrTargetAccountAccountId(int accountId, int sameAccountId,
			Pageable pageable);
}
