package cz.admin24.myachievo.web.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cz.admin24.myachievo.web.dao.AccountDAO;
import cz.admin24.myachievo.web.entity.Account;

public class SocialUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountDAO accountDAO;


    @Override
    public SocialUserWithId loadUserByUsername(String username) throws UsernameNotFoundException {
        Account acc = accountDAO.findByUsername(username);
        return new SocialUserWithId(acc);
    }


    public SocialUserWithId loadUserById(UUID id) throws UsernameNotFoundException {
        Account acc = accountDAO.findById(id);
        return new SocialUserWithId(acc);
    }

}
