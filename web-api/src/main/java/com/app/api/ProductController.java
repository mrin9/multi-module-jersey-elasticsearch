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

@Path("")
@Api(value = "Products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductController extends BaseController{
    
    @GET 
    @Path("/products")
    @PermitAll
    //@RolesAllowed({"USER", "ADMIN"})
    @ApiOperation(value = "Search Products", response = ProductResponse.class)
    public Response getProducts( 
        @ApiParam(example="Phone", allowableValues="camera,laptop,tablet,phone,sd-card")   @PathParam("productType") String productType, 
        @ApiParam(example="0"    , defaultValue="0"  , required=true) @DefaultValue("0")   @QueryParam("start") int start,
        @ApiParam(example="100"  , defaultValue="100", required=true) @DefaultValue("100") @QueryParam("limit") int limit, 
        @ApiParam(value="sort field, prefix with '-' for descending order", example="-listPrice", defaultValue="-listPrice") @DefaultValue("-listPrice") @QueryParam("sort")  String sort, 
        @QueryParam("filter") String filter
    ) 
    throws Exception {
        User userFromToken = (User)sc.getUserPrincipal();
        BaseResponse resp = new BaseResponse();
        return Response.ok(resp).build();
    }
    
}
