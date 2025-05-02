package com.jbatrina.BankingApplication.service;

import com.jbatrina.BankingApplication.dto.LoginDto;
import com.jbatrina.BankingApplication.entity.User;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @InjectMocks
    static UserService userService;

    @InjectMocks
    private AuthServiceImpl authService;

    private static User normalUser;
    private static User adminUser;

    @BeforeAll
    static void setup() {
		User adminUser = userService.addAdminUser(new User(
				"test admin first",
				"test adminLast",
				"test admin-middle",
				"test admin",
				"test admin@test admin.com",
				"test admin",	// password provided as separate arg
				Set.of(null)),
				"test admin"
		);

		User normalUser = userService.addNormalUser(new User(
				"test user first",
				"test userLast",
				"test user-middle",
				"test user",
				"test user@test user.com",
				"test user",	// password provided as separate arg
				Set.of(null)),
				"test user"
		);

    }

    @Test
    void valid_user_should_login() {
        authService.login(new LoginDto(normalUser));
        assertEquals(normalUser, authService.getCurrentUser());
    }

    @Test
    void is_admin_should_be_false_for_normal_user() {
        authService.login(new LoginDto(normalUser));
        assertFalse(authService.isAdmin());
    }

    @Test
    void is_admin_should_be_true_for_admin() {
        authService.login(new LoginDto(adminUser));
        assertTrue(authService.isAdmin());
    }
}
