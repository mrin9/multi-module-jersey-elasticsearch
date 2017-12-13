package com.app.model.user;

import com.app.model.response.BaseResponse;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "LoginResponseModel", description="Login response object")
public class LoginResponse extends BaseResponse {
    
    private Login data;
	
    public LoginResponse(Login loginUserModel) {
        setMsgType(MessageTypeEnum.SUCCESS);
        this.data = loginUserModel;
    }

    public Login getData() {return data;}
    public void setData(Login data) {this.data = data;}
}
