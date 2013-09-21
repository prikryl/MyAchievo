package cz.admin24.myachievo.web.security;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;

import cz.admin24.myachievo.web.dao.AccountDAO;

public class AutoSignUpService implements ConnectionSignUp {

    @Autowired
    private AccountDAO userDAO;


    @Override
    public String execute(Connection<?> connection) {
        UserProfile userProfile = connection.fetchUserProfile();
        String password = "" + Math.random();
        String firstName = userProfile.getFirstName();
        String lastName = userProfile.getLastName();
        String username = userProfile.getEmail();
        if (StringUtils.isBlank(username)) {
            username = userProfile.getUsername();
        }
        if (StringUtils.isBlank(username)) {
            username = userProfile.getUsername();
        }
        UUID userId = userDAO.createAccount(username, password, firstName, lastName);
        return userId.toString();
    }
}
