package cz.admin24.myachievo.web2.security;

import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AchievoAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 1L;


    public AchievoAuthenticationToken(String username, String password) {
        super(username, password, Collections.EMPTY_LIST);
    }


    public String getUsername() {
        return (String) getPrincipal();
    }


    public String getPassword() {
        return (String) getCredentials();
    }
}