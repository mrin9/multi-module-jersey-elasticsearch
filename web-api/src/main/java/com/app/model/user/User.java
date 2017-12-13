package com.app.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.security.Principal;
import lombok.*;

@Data
public class User implements Serializable, Principal {
    private String userId;
    private String password = "";
    private String userName;
    private String email;
    private Role role;
    private boolean isActive;
    private String address1;
    private String address2;
    private String postal;
    private String city;
    private String state;
    private String country;
    
    public User(){
        this("new", "new", "", "USER" , true, "");
    }

    public User(String userId, String userName,String email,  String role ){
        this(userId, userName, email, role, true,"");
    }

    public User(String userId, String userName, String email, String role, boolean isActive, String password){
        this.setUserId(userId);
        this.setEmail(email);
        this.setPassword(password);
        this.setRole(Role.valueOf(role.toUpperCase()));
        this.setUserName(userName);
        this.setActive(isActive);
    }

    @Override
    @JsonIgnore // This getter is duplicate of getId but is must for all classes that implements java.security.Principal
    public String getName() {return this.userName;}
    
}
