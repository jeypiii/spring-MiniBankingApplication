package com.jbatrina.BankingApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jbatrina.BankingApplication.entity.Department;
import com.jbatrina.BankingApplication.entity.Employee;
import com.jbatrina.BankingApplication.entity.EmployeeType;
import com.jbatrina.BankingApplication.entity.Role;
import com.jbatrina.BankingApplication.entity.User;
import com.jbatrina.BankingApplication.service.DepartmentService;
import com.jbatrina.BankingApplication.service.EmployeeService;
import com.jbatrina.BankingApplication.service.EmployeeTypeService;
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

	@Autowired
	EmployeeService employeeService;
	@Autowired
	EmployeeTypeService employeeTypeService;

	@Autowired
	DepartmentService departmentService;

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
			Role userRole = roleService.addRole(new Role("USER"));

			// add users
			userService.addAdminUser(new User(
					"admin",
					"admin@admin.com",
					"user",	// password provided as separate arg
					Set.of(adminRole)),
					"admin"
			);

			userService.addNormalUser(new User(
					"user",
					"user@user.com",
					"user",	// password provided as separate arg
					Set.of(userRole)),
					"user"
			);
		
			// TODO: add employees and types to permanent storage
			EmployeeType probationaryType = employeeTypeService.addEmployeeType("PROBATIONARY");
			EmployeeType regularType = employeeTypeService.addEmployeeType("REGULAR");
			EmployeeType contractualType = employeeTypeService.addEmployeeType("CONTRACTUAL");
		
			// make multiple copies per employee to demonstrate paging
			for (int cloneNum = 1; cloneNum <= 5; ++cloneNum) {
				
				Employee probationary = employeeService.addEmployee(new Employee(
							String.format("proby-%d", cloneNum),
							"probationary",
							"probo",
							LocalDate.of(2000, 2, 20),
							
							180000.0,
							probationaryType
						));

				Employee regular = employeeService.addEmployee(new Employee(
							String.format("reggie-%d", cloneNum),
							"regular",
							"regulo",
							LocalDate.of(1990, 9, 19),
							
							360000.0,
							regularType
						));

				Employee contractual = employeeService.addEmployee(new Employee(
							String.format("conty-%d", cloneNum),
							"contractual",
							"contro",
							LocalDate.of(2005, 5, 15),
							
							156000.0,
							contractualType
						));
				
				// add dummy departments
				Department devDept = departmentService.addDepartment(new Department(String.format("Development Department-%d", cloneNum)));
				Department testDept = departmentService.addDepartment(new Department(String.format("Test Department-%d", cloneNum)));
	
				departmentService.addEmployeesToDepartment(devDept.getDepartmentId(), new int[] { regular.getEmployeeId() });
				departmentService.addEmployeesToDepartment(devDept.getDepartmentId(), new int[] { probationary.getEmployeeId() });
				departmentService.addEmployeesToDepartment(testDept.getDepartmentId(), new int[] { contractual.getEmployeeId() });
				departmentService.addEmployeesToDepartment(testDept.getDepartmentId(), new int[] { probationary.getEmployeeId() });
			}
		}
	}
}
