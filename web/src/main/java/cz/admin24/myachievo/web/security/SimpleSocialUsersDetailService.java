package cz.admin24.myachievo.web.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

public class SimpleSocialUsersDetailService implements SocialUserDetailsService {

    @Autowired
    private cz.admin24.myachievo.web.security.SocialUserDetailsService userDetailsService;


    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        return userDetailsService.loadUserById(UUID.fromString(userId));
    }

}
