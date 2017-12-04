package com.app.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
public class User {
    private String userId;
    private String password = "";
    private String userName;
    private String email;
    private Role role;
    private boolean isActive;

    public User(){
        this("new", "PASSWORD", Role.USER, "new", true);
    }

    public User(String userId, String password, String firstName){
        this(userId, password, Role.USER, firstName, true);
    }

    public User(String userId, String password, Role role, String firstName){
        this(userId, password, role, firstName, true);
    }

    public User(String userId, String password, Role role, String userName,  boolean isActive){
        this.setUserId(userId);
        this.setEmail(userId);
        this.setPassword(password);
        this.setRole(role);
        this.setUserName(userName);
        this.setActive(isActive);
    }

}
