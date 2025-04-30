package com.jbatrina.BankingApplication.controller;

import com.jbatrina.BankingApplication.exceptions.AccountIdConflictException;
import com.jbatrina.BankingApplication.dto.AccountDetailsDto;
import com.jbatrina.BankingApplication.dto.AccountDto;
import com.jbatrina.BankingApplication.dto.BalanceDto;
import com.jbatrina.BankingApplication.entity.Account;
import com.jbatrina.BankingApplication.service.AccountService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@CrossOrigin
public class AccountController extends AdminController {

    private final TransactionController transactionController;
    @Autowired
    AccountService accountService;

    AccountController(TransactionController transactionController) {
        this.transactionController = transactionController;
    }

    @GetMapping("/")
    public void homePage(HttpServletResponse response) {
        response.setHeader("Location", "/accounts");
        response.setStatus(HttpStatus.FOUND.value());
    }

    @GetMapping("/accounts")
    public Page<AccountDto> getAllAccountsByPage(
    		@RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize
		) {

    	requireAdmin();

        return accountService.getAllAccountsByPage(pageNo, pageSize)
        		.map((acc) -> accountService.makeAccountDto(acc));
    }

    @GetMapping("/account/{id}")
    public AccountDto getAccount(@PathVariable int id) {
    	AccountDto dto = accountService.makeAccountDto(accountService.getAccount(id));
    	requireUserOrAdmin(dto.getUserId());

        return dto;
    }

    @PostMapping("/addAccount")
    public int addAccount(@Valid @RequestBody Account account) throws AccountIdConflictException {
    	requireUserOrAdmin(account.getUser().getUserId());

        Account newAccount = accountService.addAccount(account);
        return newAccount.getAccountId();
    }

    @DeleteMapping("/closeAccount/{id}")
    public void updateAccount(@PathVariable int id) {
    	requireUserId(accountService.getAccount(id).getUser().getUserId());

        accountService.closeAccount(id);
    }
    
    @GetMapping("/accountsOfUser/{userId}")
    public Page<AccountDto> getAllAccountsOfUserByPage(
    		@PathVariable int userId,
    		@RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize
		) {
    	requireUserOrAdmin(userId);

        return accountService.getAllAccountsOfUserByPage(userId, pageNo, pageSize)
        		.map((acc) -> accountService.makeAccountDto(acc));
    }

    @GetMapping("/getBalance/{id}")
    public BalanceDto getAccountBalane(@PathVariable int id) {
    	Account account = accountService.getAccount(id);
    	requireUserOrAdmin(account.getUser().getUserId());

        return BalanceDto.of(account.getBalance());
    }

    @GetMapping("/getAccountDetails/{id}")
    public AccountDetailsDto getAccountDetails(@PathVariable int id) {
    	AccountDetailsDto accountDetails = accountService.getAccountDetails(id);
    	requireUserOrAdmin(accountDetails.getUser().getUserId());

    	return accountDetails;
    }
}
