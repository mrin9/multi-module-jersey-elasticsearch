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
public class UserController extends BaseController{
    
    private org.elasticsearch.client.Response esResp;
    private Map<String, String> urlParams;
    
    @POST	
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Login as a User", response = LoginResponse.class)
    @Path("/login")
    public Response login(LoginInput loginModel) {
        String msg="";
        Login loginOutput;

        String userId = loginModel.getUserId();
        String password = loginModel.getPassword();
        String qParamVal = "userId:"+ userId + " AND password:" + password;

        urlParams = new HashMap<>();
        urlParams.put("q", qParamVal);
        urlParams.put("filter_path", "hits.hits");

        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse resp = new MultiMessageResponse();
                
        try {
            esResp = ElasticClient.rest.performRequest("GET", "/users/users/_search", urlParams);
            
            if (esResp.getStatusLine().getStatusCode() == 200){
                String responseBody = EntityUtils.toString(esResp.getEntity());
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
                        respSourceNode.path("userId").asText(),
                        respSourceNode.path("userName").textValue(),
                        respSourceNode.path("email").textValue(),
                        respSourceNode.path("role").textValue()
                    );
                    String token = TokenService.createTokenForUser(user);
                    log.info(respSourceNode.toString());
                    log.info(token);
                    loginOutput = new Login(user,token);
                    return Response.ok(new LoginResponse(loginOutput)).build();
                }
                else{
                    resp.setErrorMessage("incorrect username or passowrd");
                }
            }
        }
        catch (IOException | ParseException e) {
            ObjectNode esRespNode = ElasticClient.parseException(e);
            return Response.ok(esRespNode).build();
        }
        return Response.ok(resp).build();

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

        org.elasticsearch.client.Response esResp;
        Map<String, String> urlParams = Collections.emptyMap();
        String submitData = ("{" 
           + "   `from` :%s" 
           + "  ,`size` :%s" 
           + "  ,`query`:{`match_all`:{} }" 
           + "}").replace('`', '"');
        submitData = String.format(submitData, from,size);

        HttpEntity submitJsonEntity = new NStringEntity(submitData, ContentType.APPLICATION_JSON);
        esResp = ElasticClient.rest.performRequest("GET", "/users/users/_search?filter_path=hits.total,hits.hits._source", urlParams, submitJsonEntity);
        UserResponse resp = new UserResponse();
        //resp.updateFromEsResponse(esResp, from, size);
        
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
