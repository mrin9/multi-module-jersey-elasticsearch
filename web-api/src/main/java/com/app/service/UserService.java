package com.app.service;

import com.app.model.user.Role;
import com.app.model.user.User;

public class UserService {
    
    public User getUser(String userId, String password){

        User user = new User();
        user.setUserId("");
        user.setRole(Role.valueOf(""));
        return user;
    }

    
}
