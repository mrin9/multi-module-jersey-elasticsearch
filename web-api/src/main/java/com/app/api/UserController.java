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

import com.app.api.BaseController;
import com.app.config.ElasticClient;
import com.app.model.user.*;
import com.app.model.response.BaseResponse;
import com.app.service.TokenService;

@Log4j2
@Path ("")
@Api(value = "Login and User")
public class UserController extends BaseController{
    
    private org.elasticsearch.client.Response elSearchResp;
    private Map<String, String> urlParams;
    
    @POST	
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Login as a User", response = LoginResponse.class)
    @Path("/login")
    public Response login(LoginInputModel loginModel) {
        String msg="";
        LoginOutputModel loginOutput;

        String userId = loginModel.getUserId();
        String password = loginModel.getPassword();
        String qParamVal = "userId:"+ userId + " AND password:" + password;

        urlParams = new HashMap<>();
        urlParams.put("q", qParamVal);
        urlParams.put("filter_path", "hits.hits");

        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse resp = new BaseResponse();
                
        try {
            elSearchResp = ElasticClient.rest.performRequest("GET", "/users/users/_search", urlParams);
            
            if (elSearchResp.getStatusLine().getStatusCode() == 200){
                String responseBody = EntityUtils.toString(elSearchResp.getEntity());
                JsonNode respJsonNode = objectMapper.readTree(responseBody);
                JsonNode respSourceNode = respJsonNode.path("hits").path("hits");
                log.info("ES Response: " + responseBody);
                if ( respSourceNode == null || respSourceNode.isMissingNode()){
                    resp.setErrorMessage("Internal Error: Search response structure is incorrect ");
                    return Response.ok(resp).build();
                }
                
                if (respSourceNode.isArray() && respSourceNode.has(0)){
                    respSourceNode = respSourceNode.get(0).path("_source"); // This is the standard structure of elasticsearch _serach queries
                    User user = new User(
                        respSourceNode.path("userId").textValue(),
                        respSourceNode.path("userName").textValue(),
                        respSourceNode.path("email").textValue(),
                        respSourceNode.path("role").textValue()
                    );
                    String token = TokenService.createTokenForUser(user);
                    log.info(respSourceNode.toString());
                    log.info(token);
                    loginOutput = new LoginOutputModel(user,token);
                    return Response.ok(new LoginResponse(loginOutput)).build();
                }
                else{
                    resp.setErrorMessage("incorrect username or passowrd");
                }
            }
        }
        catch (IOException | ParseException ex) {
            log.error("ERROR :" + ex.getMessage());
            resp.setSuccesMessage("Internal Exception:" + ex.getMessage());
            return Response.ok(resp).build();
        }
        return Response.ok(resp).build();

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
