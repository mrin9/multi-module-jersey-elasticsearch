package com.app.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

public class User {
    @Getter @Setter private String userId;
    @Getter @Setter private String password = "";
    @Getter @Setter private String company;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;
    @Getter @Setter private String email;
    @Getter @Setter private Role role;
    @JsonIgnore @Getter @Setter private boolean isActive;
    @JsonIgnore @Getter @Setter private boolean isBlocked;
    @JsonIgnore @Getter @Setter private String  secretQuestion;
    @JsonIgnore @Getter @Setter private String  secretAnswer;
    @JsonIgnore @Getter @Setter private boolean enableBetaTesting;
    @JsonIgnore @Getter @Setter private boolean enableRenewal;

    public User(){
        this("new", "PASSWORD", Role.USER, "new", "new", true, "");
    }

    public User(String userId, String password, String firstName, String lastName){
        this(userId, password, Role.USER, firstName, lastName, true, "");
    }

    public User(String userId, String password, Role role, String firstName, String lastName){
        this(userId, password, role, firstName, lastName, true, "");
    }

    public User(String userId, String password, Role role, String firstName, String lastName, boolean isActive){
        this(userId, password, role, firstName, lastName, isActive, "");
    }

    public User(String userId, String password, Role role, String firstName, String lastName, boolean isActive, String company){
        this.setUserId(userId);
        this.setEmail(userId);
        this.setPassword(password);
        this.setRole(role);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setActive(isActive);
        this.setCompany(company);
    }

    public String getFullName(){
        return this.firstName + this.lastName;
    }
}
