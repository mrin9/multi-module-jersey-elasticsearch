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
import com.app.service.ElasticClient;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;

@Path("")
@Api(value = "Search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchController extends BaseController{
    
    @GET 
    @Path("/search/{index:users|products|orders}")
    @PermitAll
    //@RolesAllowed({"USER", "ADMIN"})
    @ApiOperation(value = "Search ", response = ProductResponse.class)
    public Response search( 
        @ApiParam(example="orders", allowableValues="users,products,orders", required=true)  @PathParam("index") String index, 
        @ApiParam(example="0"    , defaultValue="0"  , required=true) @DefaultValue("0")     @QueryParam("from") int from,
        @ApiParam(example="100"  , defaultValue="100", required=true) @DefaultValue("100")   @QueryParam("size") int size, 
        @ApiParam(value="sort field, prefix with '-' for descending order", example="-listPrice", defaultValue="-listPrice") @DefaultValue("-listPrice") @QueryParam("sort")  String sort, 
        @QueryParam("filter") String filter
    ) 
    throws Exception {
        User userFromToken = (User)sc.getUserPrincipal();
        if (from<=0){from=0;}
        if (size==0 || size >500){size=500;}
        
        String submitData = "{" 
           + "   `from`:%s" 
           + "  ,`size`:%s" 
           + "  ,`query`:{" 
           + "  ,`query`:{" 
           + "    `match_all`:{}" 
           + "  }" 
           + "}".replace('`', '"');
        
        org.elasticsearch.client.Response elSearchResp;

        submitData =  String.format(submitData, from,size);
        HttpEntity submitJsonEntity = new NStringEntity(submitData, ContentType.APPLICATION_JSON);
        BaseResponse resp = new BaseResponse();
        return Response.ok(resp).build();
    }
    
}
