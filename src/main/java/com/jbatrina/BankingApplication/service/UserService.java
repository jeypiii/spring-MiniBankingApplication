package com.jbatrina.BankingApplication.service;

import com.jbatrina.BankingApplication.dao.UserRepository;
import com.jbatrina.BankingApplication.exceptions.AuthUserExistsException;
import com.jbatrina.BankingApplication.exceptions.UserInvalidEmailException;
import com.jbatrina.BankingApplication.exceptions.UserNotFoundException;
import com.jbatrina.BankingApplication.entity.User;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    protected User addUser(User user, String password) {
        user.setPassword(passwordEncoder.encode(password));
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // HACK: spring.security exception not specific enough, need to check message
            // TODO: Update this whenever spring's exception message changes (e.g. i18n??)
            if (e.getMessage().contains("Duplicate")) {
                String message;
                String errorIdentifier;
                if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                    message = "The username is already taken.";
                    errorIdentifier = "::USERNAME_TAKEN";
                } else {
                    message = "The email is already associated with another account.";
                    errorIdentifier = "::EMAIL_TAKEN";
                }
                throw new AuthUserExistsException(user).setContextMessage(
                		message, errorIdentifier);
            }
        } catch (ConstraintViolationException e) {
            // HACK: ConstraintViolationException not specific enough, need to check message
            // TODO: Update this whenever Jakarta validation's exception message changes (e.g. i18n??)
            if (e.getMessage().toLowerCase().contains("email is not valid")) {
                throw new UserInvalidEmailException(user.getUserId()).setContextMessage(
                		"The provided email is invalid.", "::INVALID_EMAIL");
            }

        }

        return user;
    }

    public User addAdminUser(User user, String password) {
        user.setRoles(Set.of(roleService.getAdminRole()));
        return addUser(user, password);
    }

    public User addNormalUser(User user, String password) {
        user.setRoles(Set.of(roleService.getUserRole()));
        return addUser(user, password);
    }

    public User getById(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId).setContextMessage(
                		"No User with Id " + userId, "::NONEXISTENT_USER_ID")
                		);

        return user;
    }
}
