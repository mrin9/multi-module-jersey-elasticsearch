package com.app.api;

import com.app.model.product.ProductResponse;
import com.app.model.response.BaseResponse;
import com.app.model.user.Role;
import com.app.model.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;


@Path("")
@Api(value = "Products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductController {
    
    	@Context 
	HttpServletRequest req;
	
	@Context 
	HttpServletResponse res;
	
	@Context 
	SecurityContext sc;
	
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
	) throws Exception {
            User userFromToken = (User)sc.getUserPrincipal();
            BaseResponse resp = new BaseResponse();
            return Response.ok(resp).build();
	}
    
}
