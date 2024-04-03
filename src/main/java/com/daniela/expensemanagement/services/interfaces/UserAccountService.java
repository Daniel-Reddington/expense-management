package com.daniela.expensemanagement.services.interfaces;

import com.daniela.expensemanagement.entities.UserAccount;
import org.springframework.boot.autoconfigure.security.SecurityProperties;

public interface UserAccountService {
    UserAccount save(UserAccount userAccount);
    UserAccount findByUsername(String username);
    UserAccount findByEmail(String email);

    boolean sendEmail(String email);
    UserAccount findByPhoneNumber(String phoneNumber);
    UserAccount getConnectedUser();
    void setConnectedUser(UserAccount userAccount);
    UserAccount checkUser(UserAccount userAccount);
}
