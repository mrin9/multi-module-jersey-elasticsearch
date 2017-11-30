package com.app.api.authentication;

import com.app.api.BaseController;
import com.app.model.BaseResponse;
import com.app.model.authentication.LoginInputModel;
import com.app.model.authentication.LoginOutputModel;
import com.app.model.authentication.LoginResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.ws.rs.Consumes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;

@Path ("/auth")
@Api(value = "1st authenticate your self")
public class AuthenticationController extends BaseController{
    
    
    @POST	
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Authenticates user to access Email Security Application", response = LoginResponse.class)
    @Path("/user")
    public Response authenticateUser(LoginInputModel loginModel) {
        
        LoginOutputModel loginOutput = new LoginOutputModel("name", "name@email.com","ADMIN", "XXX.XXX.XXX");
        BaseResponse resp = new BaseResponse();
        return Response.ok(new LoginResponse(loginOutput)).build();
    }
    
    @GET
    @Path("/message")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMsg() {
        LoginOutputModel loginOutput = new LoginOutputModel("name", "name@email.com","ADMIN", "XXX.XXX.XXX");
        //BaseResponse resp = new BaseResponse();
        //resp.setSuccesMessage("This is Fine");
        return Response.ok(new LoginResponse(loginOutput)).build();
        //return "Hello World !! - Jersey 2";
    }
}
