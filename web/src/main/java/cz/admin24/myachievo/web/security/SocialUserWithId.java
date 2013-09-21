package cz.admin24.myachievo.web.security;

import java.util.Collections;
import java.util.UUID;

import org.springframework.social.security.SocialUser;

import com.google.common.base.Objects;

import cz.admin24.myachievo.web.entity.Account;

public class SocialUserWithId extends SocialUser {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final Account     account;


    public SocialUserWithId(Account account) {
        super(account.getUsername(), account.getPassword(), Collections.EMPTY_LIST);
        this.account = account;
    }


    public UUID getUserUUID() {
        return account.getId();
    }


    @Override
    public String getUserId() {
        return getUserUUID().toString();
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(account);
    }


    @Override
    public boolean equals(Object rhs) {
        if (!(rhs instanceof SocialUserWithId)) {
            return false;
        }
        SocialUserWithId s2 = (SocialUserWithId) rhs;
        return Objects.equal(account, s2.account);
    }

}
