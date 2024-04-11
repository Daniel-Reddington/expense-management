package com.daniela.expensemanagement.services.impl;

import com.daniela.expensemanagement.entities.UserAccount;
import com.daniela.expensemanagement.model.ResetPasswordResponse;
import com.daniela.expensemanagement.model.Status;
import com.daniela.expensemanagement.repositories.UserAccountRepository;
import com.daniela.expensemanagement.services.interfaces.EmailService;
import com.daniela.expensemanagement.services.interfaces.UserAccountService;
import com.daniela.expensemanagement.utils.CheckInternet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.eclipse.angus.mail.util.MailConnectException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownHostException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final EmailService emailService;
    private UserAccount userAccount = null;
    private final CheckInternet checkInternet;
    @Override
    public UserAccount save(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount findByUsername(String username) {
        return userAccountRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserAccount findByEmail(String email) {
        return userAccountRepository.findByEmail(email).orElse(null);
    }

    @Override
    public ResetPasswordResponse sendEmail(String email) {
        UserAccount userByEmail = findByEmail(email);
        String password = RandomString.make(16);


        if(userByEmail != null){

            String message = new StringBuilder()
                    .append("Dear ").append(userByEmail.getUsername()).append(",\n\n")
                    .append("Here is your new password : ").append(password).append(".\n\n")
                    .append("We strongly recommend that you change this password as soon as possible for security reasons.\n")
                    .append("To do this, log in to your account and go to your account settings to set a new password.\n\n")
                    .append("If you have any questions or concerns, feel free to contact us.\n\n")
                    .append("Best regards,\n")
                    .append("The team at [Expense Management]").toString();


            if(checkInternet.isInternetAvailable()){
                try{
                    emailService.sendSimpleMessage(userByEmail.getEmail(), "New Password", message);
                    userByEmail.setPassword(password);
                    return new ResetPasswordResponse(Status.SUCCESS, "Mail sending successfully");
                }catch (MailException | MailConnectException exception){
                    log.info("mail exception");
                    return new ResetPasswordResponse(Status.MAIL_ERROR, "Error when sending email");
                }
            }else{
                return new ResetPasswordResponse(Status.INTERNET, "Internet is disable");
            }
        }

        return new ResetPasswordResponse(Status.MAIL_NOT_FOUND, "Email not found");
    }

    @Override
    public UserAccount findByPhoneNumber(String phoneNumber) {
        return userAccountRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    @Override
    public UserAccount getConnectedUser() {
        return userAccount;
    }

    @Override
    public void setConnectedUser(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public UserAccount checkUser(UserAccount userAccount) {
         this.userAccount =  userAccountRepository
                .findByUsernameAndPassword(userAccount.getUsername(), userAccount.getPassword())
                .orElse(null);

        return this.userAccount;
    }
}
