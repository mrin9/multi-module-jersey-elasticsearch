package com.app.model.authentication;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "LoginOutputModel", description="Login object")
public class LoginOutputModel {

    private String username;
    private String emailAddresss;
    private String role;
    private String token;

    public LoginOutputModel(String username, String emailAddresss, String role, String token) {
        super();
        this.username = username;
        this.emailAddresss = emailAddresss;
        this.role = role;
        this.token = token;
    }
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getEmailAddresss() {return emailAddresss;}
    public void setEmailAddresss(String emailAddresss) {this.emailAddresss = emailAddresss;}

    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}

    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}
	
}

