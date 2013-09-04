package cz.admin24.myachievo.web.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

import cz.admin24.myachievo.web.dao.AccountDAO;

public class AutoSignUpService implements ConnectionSignUp {

    @Autowired
    private AccountDAO userDAO;


    @Override
    public String execute(Connection<?> connection) {
        UUID userId = userDAO.createAccount();
        return userId.toString();
    }

}
