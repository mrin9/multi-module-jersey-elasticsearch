package com.app.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@ApiModel(value = "LoginInputModel", description="Login input model")
public class LoginInput {

    @ApiModelProperty(value = "User Name", example = "demo")
    private String  username;

    @ApiModelProperty(value = "Password", example = "demo")
    private String  password;

}

