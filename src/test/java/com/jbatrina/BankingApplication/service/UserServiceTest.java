package com.jbatrina.BankingApplication.service;


import com.jbatrina.BankingApplication.repository.UserRepository;
import com.jbatrina.BankingApplication.exceptions.AuthUserExistsException;
import com.jbatrina.BankingApplication.exceptions.BankingApplicationException;
import com.jbatrina.BankingApplication.entity.Role;
import com.jbatrina.BankingApplication.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    User testNormalUser;
    User testAdminUser;
    @BeforeEach
    void setup() {
        testNormalUser = new User();
        testNormalUser.setUserId(1);
        testNormalUser.setFirstName("testfirst");
        testNormalUser.setLastName("testlast");
        testNormalUser.setUsername("testuser");
        testNormalUser.setEmail("testuser@user.com");
        testNormalUser.setRoles(Set.of(new Role("USER")));
        testNormalUser.setPassword("testpass");

        testAdminUser = new User();
        testAdminUser.setUserId(2);
        testAdminUser.setFirstName("testAdminfirst");
        testAdminUser.setLastName("testAdminlast");
        testAdminUser.setUsername("testAdminuser");
        testAdminUser.setEmail("testAdminuser@user.com");
        testAdminUser.setRoles(Set.of(new Role("ADMIN")));
        testAdminUser.setPassword("testAdminpass");
    }

    @Test
    void get_by_id_should_return_correct_user() {
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(testNormalUser));
        User returnedUser = userService.getById(testNormalUser.getUserId());
        assertEquals(testNormalUser, returnedUser);

        when(userRepository.findById(2)).thenReturn(Optional.ofNullable(testAdminUser));
        returnedUser = userService.getById(testAdminUser.getUserId());
        assertEquals(testAdminUser, returnedUser);
    }

//    @Test
//    void duplicate_user_should_throw_AuthUserExistsException() {
//        when(userRepository.findByUsername("testuser")).thenReturn(Optional.ofNullable(testNormalUser));
//        BookstoreException e = assertThrows(AuthUserExistsException.class, () -> {
//          userService.addUser(testNormalUser, testNormalUser.getPassword());
//        });
//
//        assertEquals("The username is already taken.", e.getContextMessage());
//    }
}
