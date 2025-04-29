package com.jbatrina.BankingApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jbatrina.BankingApplication.entity.Account;
import com.jbatrina.BankingApplication.entity.AccountType;
import com.jbatrina.BankingApplication.entity.Balance;
import com.jbatrina.BankingApplication.entity.Role;
import com.jbatrina.BankingApplication.entity.Transaction;
import com.jbatrina.BankingApplication.entity.TransactionType;
import com.jbatrina.BankingApplication.entity.User;
import com.jbatrina.BankingApplication.service.AccountService;
import com.jbatrina.BankingApplication.service.AccountTypeService;
import com.jbatrina.BankingApplication.service.RoleService;
import com.jbatrina.BankingApplication.service.TransactionService;
import com.jbatrina.BankingApplication.service.TransactionTypeService;
import com.jbatrina.BankingApplication.service.UserService;

import java.math.BigDecimal;
import java.util.Set;

@SpringBootApplication
public class BankingApplication implements CommandLineRunner {
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	AccountService accountService;
	@Autowired
	AccountTypeService accountTypeService;
	@Autowired
	TransactionService transactionService;
	@Autowired
	TransactionTypeService transactionTypeService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlAuto;

	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// NOTE: database is re-created each run (via ddl-auto)

		System.out.println("DDL AUTO" + ddlAuto);
		if (ddlAuto != null && !ddlAuto.isBlank()) {
			// We add dummy values to populate database
			// add roles and users

			// TODO: add roles and users to permanent storage
			Role adminRole = roleService.addRole(new Role("ADMIN"));
			Role customerRole = roleService.addRole(new Role("CUSTOMER"));

			// add users
			User adminUser = userService.addAdminUser(new User(
					"admin first",
					"adminLast",
					"admin-middle",
					"admin",
					"admin@admin.com",
					"user",	// password provided as separate arg
					Set.of(adminRole)),
					"admin"
			);

			User customerAUser = userService.addNormalUser(new User(
					"Mandy",
					null,
					"Mapagbigay",
					"mapagbigay",
					"mapagbigay@customer.com",
					"mapagbigay",	// password provided as separate arg
					Set.of(customerRole)),
					"mapagbigay"
			);

			User customerBUser = userService.addNormalUser(new User(
					"Brett",
					"Ree",
					"Turisiv",
					"brett",
					"brett@customer.com",
					"brett",	// password provided as separate arg
					Set.of(customerRole)),
					"brett"
			);

			User customerCUser = userService.addNormalUser(new User(
					"Porque",
					"No",
					"Los Dos",
					"porque",
					"porque@customer.com",
					"porque",	// password provided as separate arg
					Set.of(customerRole)),
					"porque"
			);

		
			// seed with basic account types
			AccountType checkingType = accountTypeService.addAccountType("CHECKINGS");
			AccountType savingsType = accountTypeService.addAccountType("SAVINGS");
			
			// populate with test accounts
			Account accountA = accountService.addAccount(
					new Account(customerAUser, savingsType, Balance.ofBase(BigDecimal.valueOf(100000.1234)))
				);
			Account accountB = accountService.addAccount(
					new Account(customerBUser, savingsType, Balance.ofBase(BigDecimal.valueOf(543.2101)))
				);
			Account accountA2 = accountService.addAccount(
					new Account(customerAUser, checkingType, Balance.ofBase(200_000))
				);
			Account accountC = accountService.addAccount(
					new Account(customerCUser, savingsType, Balance.ofBase(8000))
				);
			
			// seed with basic transaction types
			TransactionType balanceCheckTransaction = transactionTypeService.addTransactionType("BALANCE_CHECK");
			TransactionType fundTransferTransaction = transactionTypeService.addTransactionType("FUND_TRANSFER");
			
			// Initialize sample transactions
			Transaction balanceTransaction = transactionService.createBalanceCheck(accountA);
			Transaction transferTransaction = transactionService.createFundTransfer(
					new Transaction(accountA, accountB, fundTransferTransaction, Balance.ofBase(1000))
				);
			Transaction transferTransaction2 = transactionService.createFundTransfer(
					new Transaction(accountC, accountA, fundTransferTransaction, Balance.ofBase(233))
				);
		}
	}
}
