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
    @ApiOperation(value = "Search Products", response = ProductResponse.class)
    public Response search( 
        @ApiParam(example="Phone", allowableValues="users,products,orders")   @PathParam("index") String productType, 
        @ApiParam(example="0"    , defaultValue="0"  , required=true) @DefaultValue("0")   @QueryParam("start") int start,
        @ApiParam(example="100"  , defaultValue="100", required=true) @DefaultValue("100") @QueryParam("limit") int limit, 
        @ApiParam(value="sort field, prefix with '-' for descending order", example="-listPrice", defaultValue="-listPrice") @DefaultValue("-listPrice") @QueryParam("sort")  String sort, 
        @QueryParam("filter") String filter
    ) 
    throws Exception {
        User userFromToken = (User)sc.getUserPrincipal();
        String submitData = "{" 
           + "  `query`:{" 
           + "    `match_all`:{}" 
           + "  }" 
           + "}".replace('`', '"');
        HttpEntity submitJsonEntity = new NStringEntity(submitData, ContentType.APPLICATION_JSON);


        BaseResponse resp = new BaseResponse();
        return Response.ok(resp).build();
    }
    
}
