package com.app.model.user;

import com.app.model.response.BaseResponse;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "LoginResponseModel", description="Login response object")
public class LoginResponse extends BaseResponse {
    
    private Login item;
	
    public LoginResponse(Login loginUserModel) {
        this.setSuccessMessage("Login Success");
        this.item = loginUserModel;
    }
}
