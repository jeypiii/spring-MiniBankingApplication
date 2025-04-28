package com.jbatrina.BankingApplication.controller;

import com.jbatrina.BankingApplication.dto.TransactionDto;
import com.jbatrina.BankingApplication.entity.Transaction;
import com.jbatrina.BankingApplication.service.AccountService;
import com.jbatrina.BankingApplication.service.TransactionService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@CrossOrigin
public class TransactionController extends AdminController {
    @Autowired
    TransactionService transactionService;
    @Autowired
    AccountService accountService;

    @GetMapping("/transaction/{id}")
    public TransactionDto getTransaction(@PathVariable int id) {
    	Transaction t = transactionService.getTransaction(id);
    	requireUsersOrAdmin(new int []{
    			t.getSourceAccount().getUser().getUserId(), 
    			t.getTargetAccount().getUser().getUserId()
			});

        return transactionService.makeTransactionDto(t);
    }

    @GetMapping("/transactionsForAccount/{accountId}")
    public Page<TransactionDto> getAllTransactionsOfAccountByPage(
    		@PathVariable int accountId,
    		@RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize
		) {

    	requireUserOrAdmin(accountService.getAccount(accountId).getUser().getUserId());
 
        return transactionService.getAllTransactionsOfAccountByPage(accountId, pageNo, pageSize)
        		.map((acc) -> transactionService.makeTransactionDto(acc));
    }
 
    @PostMapping("/fundTransfer")
    public TransactionDto transferFunds(@Valid @RequestBody TransactionDto transactionDto) {
    	Transaction t = transactionService.fromTransactionDto(transactionDto);
    	requireUsers(new int []{
    			t.getSourceAccount().getUser().getUserId(), 
    			t.getTargetAccount().getUser().getUserId()
			});

    	return transactionService.makeTransactionDto(
    			transactionService.createFundTransfer(transactionDto)
			);
    }
}
