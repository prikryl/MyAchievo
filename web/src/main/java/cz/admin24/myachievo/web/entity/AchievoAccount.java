package cz.admin24.myachievo.web.entity;

import java.io.Serializable;
import java.util.UUID;

public class AchievoAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID        id;
    private final String      username;
    private final String      encryptedPassword;
    private final String      accountId;


    public AchievoAccount(UUID id, String username, String encryptedPassword, String accountId) {
        this.id = id;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
        this.accountId = accountId;
    }


    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    public UUID getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }


    public String getEncryptedPassword() {
        return encryptedPassword;
    }


    public String getAccountId() {
        return accountId;
    }

}
