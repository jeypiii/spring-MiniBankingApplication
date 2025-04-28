package com.jbatrina.BankingApplication.controller;

import com.jbatrina.BankingApplication.dto.BalanceDto;
import com.jbatrina.BankingApplication.dto.TransactionDto;
import com.jbatrina.BankingApplication.exceptions.TransactionIdConflictException;
import com.jbatrina.BankingApplication.entity.Transaction;
import com.jbatrina.BankingApplication.service.TransactionService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@CrossOrigin
public class TransactionController extends AdminController {
    @Autowired
    TransactionService transactionService;

//    @GetMapping("/transactions")
//    public Page<TransactionDto> getAllTransactionsByPage(
//    		@RequestParam(defaultValue = "1") int pageNo,
//            @RequestParam(defaultValue = "5") int pageSize
//		) {
//
//        return transactionService.getAllTransactionsByPage(pageNo, pageSize)
//        		.map((acc) -> transactionService.makeTransactionDto(acc));
//    }

    @GetMapping("/transaction/{id}")
    public TransactionDto getTransaction(@PathVariable int id) {
        return transactionService.makeTransactionDto(transactionService.getTransaction(id));
    }

    @GetMapping("/transactionsForAccount/{accountId}")
    public Page<TransactionDto> getAllTransactionsOfAccountByPage(
    		@PathVariable int accountId,
    		@RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize
		) {

        return transactionService.getAllTransactionsOfAccountByPage(accountId, pageNo, pageSize)
        		.map((acc) -> transactionService.makeTransactionDto(acc));
    }
}
