package com.app.api;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.servlet.http.*;
import javax.annotation.security.RolesAllowed;
import javax.annotation.security.PermitAll;

import io.swagger.annotations.*;
import com.app.model.product.ProductResponse;
import com.app.model.response.BaseResponse;
import com.app.model.user.Role;
import com.app.model.user.User;
import com.app.service.DataService;
import static com.app.service.DataService.indexNamesArray;
import static com.app.service.DataService.insertDataFromFile;
import com.app.service.ElasticClient;
import com.app.service.Util;
import java.util.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;

@Log4j2
@Path("")
@Api(value = "System")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SystemController  extends BaseController {
    
    @DELETE 
    @Path("/system/data/{index:users|products|orders|all}/_remove_data")
    @RolesAllowed({"ADMIN"})
    @ApiOperation(value = "Reset data to original state (all the modification done will be lost)", response = BaseResponse.class)
    public Response resetData (@ApiParam(example="all", allowableValues="users,products,orders,all")   @PathParam("index") String index) throws Exception {

        User userFromToken = (User)sc.getUserPrincipal();
        BaseResponse resp = new BaseResponse();
        // catch exceptions of org.elasticsearch.client.ResponseException type
        
        if (index.equalsIgnoreCase("all")){
            List<String> indexNameList = Arrays.asList(indexNamesArray);
            for (String tmpIndex : indexNameList) {
                DataService.deleteData(tmpIndex);
            }
        }
        else if (Util.isValidIndex(index)){
            DataService.deleteData(index);
        }
        
        resp.setSuccesMessage("Documents deleted ");
        return Response.ok(resp).build();
    }
    
    
    
    @POST 
    @Path("/system/elastic/_execute")
    @RolesAllowed({"ADMIN"})
    @ApiOperation(value = "Execute elastic Query)", response = BaseResponse.class)
    public Response resetData (
        @ApiParam(example="GET", allowableValues="GET,PUT,POST,DELETE") @QueryParam("method") String method,
        @ApiParam(example="")  @QueryParam("url") String url,
        @ApiParam(example="")  @QueryParam("data") String data
    ) throws Exception {
        try {
            org.elasticsearch.client.Response elSearchResp;
            String responseBody="";
            Map<String, String> urlParams = Collections.emptyMap();
            HttpEntity submitJsonEntity;

            if (StringUtils.isBlank(data)){
                elSearchResp = ElasticClient.rest.performRequest(method, url, urlParams);
            }
            else{
                submitJsonEntity = new NStringEntity(data, ContentType.APPLICATION_JSON);
                elSearchResp = ElasticClient.rest.performRequest(method, url, urlParams, submitJsonEntity);
            }
            responseBody = EntityUtils.toString(elSearchResp.getEntity());
            BaseResponse resp = new BaseResponse();
            resp.setSuccesMessage(responseBody);
            return Response.ok(resp).build();
        }
        catch (Exception e){
            log.info(e.getMessage());
            BaseResponse resp = new BaseResponse();
            resp.setSuccesMessage(e.getMessage());
            return Response.ok(resp).build();
        }
    }

}
