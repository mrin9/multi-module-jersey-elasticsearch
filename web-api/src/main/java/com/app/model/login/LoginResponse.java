package com.app.model.login;

import com.app.model.response.BaseResponse;
import io.swagger.annotations.ApiModel;

@ApiModel(value = "LoginResponseModel", description="Login response object")
public class LoginResponse extends BaseResponse {
    
    private LoginOutputModel data;
	
    public LoginResponse(LoginOutputModel loginUserModel) {
        setMsgType(MessageTypeEnum.SUCCESS);
        this.data = loginUserModel;
    }

    public LoginOutputModel getData() {return data;}
    public void setData(LoginOutputModel data) {this.data = data;}
}
