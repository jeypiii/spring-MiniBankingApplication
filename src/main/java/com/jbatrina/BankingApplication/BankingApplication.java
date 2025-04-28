package com.jbatrina.BankingApplication;

import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jbatrina.BankingApplication.entity.Account;
import com.jbatrina.BankingApplication.entity.Balance;
import com.jbatrina.BankingApplication.entity.Role;
import com.jbatrina.BankingApplication.entity.User;
import com.jbatrina.BankingApplication.service.AccountService;
import com.jbatrina.BankingApplication.service.RoleService;
import com.jbatrina.BankingApplication.service.UserService;

import java.util.Set;

@SpringBootApplication
public class BankingApplication implements CommandLineRunner {
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	AccountService accountService;

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

		
			// populate with test accounts
			Account accountA = accountService.addAccount(
					new Account(customerAUser, new Balance(Money.of(100_000, "PHP")))
				);
			Account accountA2 = accountService.addAccount(
					new Account(customerAUser, new Balance(Money.of(200_000, "PHP")))
				);
			Account accountB = accountService.addAccount(
					new Account(customerBUser, new Balance(Money.of(500, "PHP")))
				);
			Account accountC = accountService.addAccount(
					new Account(customerCUser, new Balance(Money.of(8000, "PHP")))
				);
		}
	}
}
