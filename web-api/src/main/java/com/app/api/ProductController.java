package com.app.api;

import java.io.*;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.annotation.security.PermitAll;

import com.app.model.response.*;
import com.app.model.user.User;
import com.app.model.product.*;
import com.app.service.ElasticClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;


@Log4j2
@Path("")
@Api(value = "Products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductController extends BaseController{
    
    @GET 
    @Path("/products")
    @PermitAll
    //@RolesAllowed({"USER", "ADMIN"})
    @ApiOperation(value = "Serach Products ", response = ProductResponse.class)
    public Response search( 
        @ApiParam(example="0"  , defaultValue="0" , required=true) @DefaultValue("1")  @QueryParam("from") int from,
        @ApiParam(example="5"  , defaultValue="5" , required=true) @DefaultValue("5")  @QueryParam("size") int size, 
        @ApiParam(value="sort field, prefix with '-' for descending order", example="-productId", defaultValue="-productId")  @QueryParam("sort")  String sort, 
        @QueryParam("filter") String filter
    ) 
    throws Exception {
        User userFromToken = (User)sc.getUserPrincipal();
        if (from<=0){from=0;}
        if (size==0 || size >500){size=500;}
        String submitData = ("{" 
           + "   `from` :%s" 
           + "  ,`size` :%s" 
           + "  ,`query`:{" 
           + "     `match_all`:{}" 
           + "  }" 
           + "}").replace('`', '"');
        submitData = String.format(submitData, from,size);
        org.elasticsearch.client.Response esResp;
        Map<String, String> urlParams = Collections.emptyMap();

        HttpEntity submitJsonEntity = new NStringEntity(submitData, ContentType.APPLICATION_JSON);
        esResp = ElasticClient.rest.performRequest("GET", "/products/products/_search?filter_path=hits.total,hits.hits._source", urlParams, submitJsonEntity);
        ProductResponse resp = new ProductResponse();
        resp.parseFromEsResponse(esResp, from, size);
        
        return Response.ok(resp).build();

    }
    
    
    @DELETE 
    @Path("/products/{productId}")
    @PermitAll
    @ApiOperation(value = "Delete product by ID ", response = BaseResponse.class)
    public Response deleteProduct (@ApiParam(example="P1", required=true) @DefaultValue("")  @PathParam("productId") String productId) 
    throws Exception {
        User userFromToken = (User)sc.getUserPrincipal();
        org.elasticsearch.client.Response esResp;
        Map<String, String> urlParams = Collections.emptyMap();

        String submitData = ("{" 
           + "  `query`:{" 
           + "     `term`:{ `productId`: `%s` }"
           + "  }" 
           + "}").replace('`', '"');
        
        submitData = String.format(submitData, productId);
        HttpEntity submitJsonEntity = new NStringEntity(submitData, ContentType.APPLICATION_JSON);
        esResp = ElasticClient.rest.performRequest("POST", "/products/products/_delete_by_query", urlParams, submitJsonEntity);
        MultiMessageResponse resp =  ElasticClient.parseDeleteByQueryResponse(esResp
            , String.format("Deleted product [Product ID:%s]", productId)
            , String.format("Product not found [Product ID:%s]", productId)
            , String.format("Error deleting product [Product ID:%s]", productId)
        );
        return Response.ok(resp).build();

    }
    
    @POST	
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/products")
    @ApiOperation(value = "Add a new product ", response = BaseResponse.class)
    public Response insertProduct (ProductInput prodIn) throws Exception {
        
        ObjectMapper mapper = new ObjectMapper();
        org.elasticsearch.client.Response esResp;
        Map<String, String> urlParams = Collections.emptyMap();
        try {
            String uniqueID = UUID.randomUUID().toString();
            Product newProd = new Product(uniqueID
                , prodIn.getProductName()
                , prodIn.getUnit()
                , prodIn.getReorderLevel()
                , prodIn.getQuantityOnHand()
                , prodIn.getListPrice()
                , prodIn.getProductType()
            );
            String submitData = mapper.writeValueAsString(newProd);
            HttpEntity submitJsonEntity = new NStringEntity(submitData, ContentType.APPLICATION_JSON);
            esResp = ElasticClient.rest.performRequest("POST", "/products/products", urlParams, submitJsonEntity);
            ObjectNode esRespNode = ElasticClient.parseResponse(esResp);
            return Response.ok(esRespNode).build();
        }
        catch (IOException e){
            log.info("Exeception:[" + e.getClass().getName()+"] " +  e.getMessage()  );
            log.info("Stack Trace:[" + ExceptionUtils.getStackTrace(e));
            ObjectNode esRespNode = ElasticClient.parseException(e);
            return Response.ok(esRespNode).build();
        }
    }
}
