package com.jbatrina.BankingApplication.controller;

import com.jbatrina.BankingApplication.exceptions.AccountIdConflictException;
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
    @Autowired
    AccountService accountService;

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

        return accountService.getAllAccountsByPage(pageNo, pageSize)
        		.map((acc) -> accountService.makeAccountDto(acc));
    }

    @GetMapping("/account/{id}")
    public AccountDto getAccount(@PathVariable int id) {
        return accountService.makeAccountDto(accountService.getAccount(id));
    }

    @PostMapping("/addAccount")
    public int addAccount(@Valid @RequestBody Account account) throws AccountIdConflictException {
        requireAdmin();
        Account newAccount = accountService.addAccount(account);
        return newAccount.getAccountId();
    }

    @DeleteMapping("/removeAccount/{id}")
    public void updateAccount(@PathVariable int id) {
        requireAdmin();
        accountService.removeAccount(id);
    }
    
    @GetMapping("/accountsOfUser/{userId}")
    public Page<AccountDto> getAllAccountsOfUserByPage(
    		@PathVariable int userId,
    		@RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize
		) {

        return accountService.getAllAccountsOfUserByPage(userId, pageNo, pageSize)
        		.map((acc) -> accountService.makeAccountDto(acc));
    }

    @GetMapping("/getBalance/{id}")
    public BalanceDto getAccountBalane(@PathVariable int id) {
        return BalanceDto.of(accountService.getAccount(id).getBalance());
    }
}
