package com.jbatrina.BankingApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.jbatrina.BankingApplication.exceptions.AuthAdminRequiredException;
import com.jbatrina.BankingApplication.exceptions.UserIdMismatchException;
import com.jbatrina.BankingApplication.service.AuthService;

public abstract class AdminController {
    @Autowired
    public AuthService authService;
 
    protected void requireAdmin() {
    	requireAdmin(true);
    }

    protected boolean requireAdmin(boolean shouldThrow) {
        if (!authService.isAdmin()) {
        	if (shouldThrow) {
				throw new AuthAdminRequiredException()
						.setContextMessage("Attempting to use an admin-only api", "::ADMIN_ONLY");
        	}
        	
        	return false;
        }
        
        return true;
    }

    protected void requireUserId(int userId) {
    	requireUserId(userId, true);
    }

    protected boolean requireUserId(int userId, boolean shouldThrow) {
    	final int currUserId = authService.getCurrentUser().getUserId();
        if (currUserId != userId) {
        	if (shouldThrow) {
				throw new UserIdMismatchException(userId)
						.setContextMessage("Private user information can only be accessed by the account owner", "::INCORRECT_USER");
        	}
        	
        	return false;
        }
        
        return true;
    }

    protected void requireUserOrAdmin(int userId) {
		requireUserOrAdmin(userId, true);
    }

    protected void requireUsersOrAdmin(int[] userIds) {
		requireUsersOrAdmin(userIds, true);
    }

    protected void requireUsers(int[] userIds) {
		requireUsers(userIds, true);
    }

    protected boolean requireUsers(int[] userIds, boolean shouldThrow) {
		final int currUserId = authService.getCurrentUser().getUserId();
		for (int id : userIds) {
			System.out.println("CHECK " + id + " CURRENT" + currUserId);
			if (requireUserId(id, false)) {
				return true;
			}
		}

		if (shouldThrow && userIds.length > 0) {
			// Force throw by (re)invoking requireUserId
			System.out.println("NO USER FOUND");
			return requireUserId(userIds[0], true);
		}

		return false;
    }

    protected boolean requireUsersOrAdmin(int[] userIds, boolean shouldThrow) {
    	if (! requireAdmin(false)) {
			return requireUsers(userIds, shouldThrow);
    	}
    	
    	return true;
    }

    protected boolean requireUserOrAdmin(int userId, boolean shouldThrow) {
    	if (! requireAdmin(false)) {
    		return requireUserId(userId, shouldThrow);
    	}
    	
    	return true;
    }
}