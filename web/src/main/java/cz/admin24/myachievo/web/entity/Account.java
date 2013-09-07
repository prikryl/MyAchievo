package cz.admin24.myachievo.web.entity;

import java.io.Serializable;
import java.util.UUID;

import com.google.common.base.Objects;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private final UUID        id;
    private final String      username;
    private final String      password;
    private final String      firstName;
    private final String      lastName;


    public Account(UUID id, String username, String password, String firstName, String lastName) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }


    public String getFirstName() {
        return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public UUID getId() {
        return id;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


    @Override
    public boolean equals(Object rhs) {
        if (!(rhs instanceof Account)) {
            return false;
        }
        Account a2 = (Account) rhs;
        return Objects.equal(id, a2.id);
    }
}
