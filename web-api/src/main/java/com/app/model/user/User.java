package com.app.model.user;

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
    
}
