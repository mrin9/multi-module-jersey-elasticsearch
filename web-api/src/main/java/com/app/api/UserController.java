package com.app.api;

import java.util.*;
import java.io.IOException;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.annotation.security.PermitAll;

import com.fasterxml.jackson.databind.*;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import lombok.extern.log4j.Log4j2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.app.service.ElasticClient;
import com.app.model.user.*;
import com.app.model.response.BaseResponse;
import com.app.model.response.MultiMessageResponse;
import com.app.service.TokenService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.annotations.ApiParam;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;

@Log4j2
@Path ("")
@Api(value = "Login and User")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController extends BaseController{
    
    @POST	
    @PermitAll
    @ApiOperation(value = "Login as a User", response = LoginResponse.class)
    @Path("/login")
    public Response login(LoginInput loginModel) throws Exception {
        Login loginOutput;
        Map<String, String> urlParams = Collections.emptyMap();
        String submitData = ("{" 
           + " `query`: { "
           + "   `bool`: {"
           + "     `must`: ["
           + "       {`term`:{`userId`:`%s`}}," 
           + "       {`term`:{`password`:`%s`}}" 
           + "     ]" 
           + "   }" 
           + " }" 
           + "}").replace('`', '"');
        submitData = String.format(submitData, loginModel.getUserId() ,loginModel.getPassword());
        HttpEntity submitJsonEntity = new NStringEntity(submitData, ContentType.APPLICATION_JSON);

        org.elasticsearch.client.Response esResp = ElasticClient.rest.performRequest("GET", "/users/users/_search?filter_path=hits.total,hits.hits._source", urlParams, submitJsonEntity);
        Map.Entry<Integer, List<User>> totalAndList  = ElasticClient.<User>getTotalAndListFromSearchQueryResponse(esResp, User.class);
        int total = (int)totalAndList.getKey();
        List<User> users = totalAndList.getValue();
        if (total==1){
            User user = users.get(0);
            String token = TokenService.createTokenForUser(user);
            loginOutput = new Login(user,token);
            return Response.ok(new LoginResponse(loginOutput)).build();
        }
        else{
            BaseResponse resp = new BaseResponse();
            resp.setErrorMessage("incorrect username or passowrd");
            return Response.ok(resp).build();
        }
    }
    
    
    @GET 
    @Path("/users")
    @PermitAll
    //@RolesAllowed({"USER", "ADMIN"})
    @ApiOperation(value = "Serach Users ", response = UserResponse.class)
    public Response search( 
        @ApiParam(example="0"  , defaultValue="0" , required=true) @DefaultValue("1")  @QueryParam("from") int from,
        @ApiParam(example="5"  , defaultValue="5" , required=true) @DefaultValue("5")  @QueryParam("size") int size, 
        @ApiParam(value="sort field, prefix with '-' for descending order", example="-productId", defaultValue="-productId")  @QueryParam("sort")  String sort, 
        @QueryParam("filter") String filter
    ) throws Exception {
        
        if (from<=0){from=0;}
        if (size==0 || size >500){size=500;}

        Map<String, String> urlParams = Collections.emptyMap();
        String submitData = ("{" 
           + "   `from` :%s" 
           + "  ,`size` :%s" 
           + "  ,`query`:{`match_all`:{} }" 
           + "}").replace('`', '"');
        submitData = String.format(submitData, from,size);

        HttpEntity submitJsonEntity = new NStringEntity(submitData, ContentType.APPLICATION_JSON);
        org.elasticsearch.client.Response esResp = ElasticClient.rest.performRequest("GET", "/users/users/_search?filter_path=hits.total,hits.hits._source", urlParams, submitJsonEntity);
        UserResponse resp = new UserResponse();
        resp.updateFromEsResponse(esResp, from, size);
        return Response.ok(resp).build();

    }
    
    
    
    
    
    @GET
    @Path("/message")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getMsg() {
        Login loginOutput = new Login("name", "name@email.com","ADMIN", "XXX.XXX.XXX");
        //BaseResponse resp = new BaseResponse();
        //resp.setSuccesMessage("This is Fine");
        return Response.ok(new LoginResponse(loginOutput)).build();
        //return "Hello World !! - Jersey 2";
    }
    
    
    
}
