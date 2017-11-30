package com.app.model.authentication;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "LoginInputModel", description="Login object")
public class LoginInputModel {

    @ApiModelProperty(value = "User Name", example = "admin")
    private String  username;

    @ApiModelProperty(value = "Password", example = "password")
    private String  password;

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

	
}

