package cz.admin24.myachievo.web.entity;

import java.util.UUID;

public class Account {
    private final UUID   id;
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;


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

}
