package com.jbatrina.BankingApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jbatrina.BankingApplication.entity.Role;
import com.jbatrina.BankingApplication.entity.User;
import com.jbatrina.BankingApplication.service.RoleService;
import com.jbatrina.BankingApplication.service.UserService;

import java.time.LocalDate;
import java.util.Set;

@SpringBootApplication
public class BankingApplication implements CommandLineRunner {
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

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
			userService.addAdminUser(new User(
					"admin",
					"admin@admin.com",
					"user",	// password provided as separate arg
					Set.of(adminRole)),
					"admin"
			);

			userService.addNormalUser(new User(
					"customer",
					"customer@customer.com",
					"customer",	// password provided as separate arg
					Set.of(customerRole)),
					"customer"
			);
		}
	}
}
