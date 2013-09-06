package cz.admin24.myachievo.web.security;

import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;

import cz.admin24.myachievo.web.dao.AccountDAO;
import cz.admin24.myachievo.web.entity.Account;

public class SocialUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountDAO accountDAO;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account acc = accountDAO.findByUsername(username);
        return new SocialUser(acc.getUsername(), acc.getPassword(), Collections.EMPTY_LIST);
    }


    public UserDetails loadUserById(UUID id) throws UsernameNotFoundException {
        Account acc = accountDAO.findById(id);
        return new SocialUser(acc.getUsername(), acc.getPassword(), Collections.EMPTY_LIST);
    }

}
