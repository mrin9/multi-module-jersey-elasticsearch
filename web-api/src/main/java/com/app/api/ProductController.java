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
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;


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
        @ApiParam(value="sort field, prefix with '-' for descending order", example="-listPrice", defaultValue="")  @QueryParam("sort")  String sort, 
        @QueryParam("filter") String filter
    ) {
        
        User userFromToken = (User)sc.getUserPrincipal();
        if (from<=0){from=0;}
        if (size==0 || size >500){size=500;}
        org.elasticsearch.client.Response esResp;
        Map<String, String> urlParams = Collections.emptyMap();
        String submitData = ("{"
            + "   `from` :%s"
            + "  ,`size` :%s"
            + "  ,`query`:{"
            + "     `match_all`:{}"
            + "    }"
            + "  ,`sort`:[%s]"
            + "}").replace('`', '"');

        String sortClause="", fieldName="", direction="";
        if (StringUtils.isNotBlank(sort)){
            sort = sort.trim();
            if (sort.startsWith("-")){
                direction = "desc";
                fieldName = sort.substring(1);
            }
            else{
                direction = "asc";
                fieldName = sort;
            }
            sortClause = String.format("{`%s`: { `order`: `%s` }}",fieldName,direction).replace('`', '"');
        }   
        submitData = String.format(submitData, from, size, sortClause);
        
        try {
            HttpEntity submitJsonEntity = new NStringEntity(submitData, ContentType.APPLICATION_JSON);
            esResp = ElasticClient.rest.performRequest("GET", "/products/products/_search?filter_path=hits.total,hits.hits._source", urlParams, submitJsonEntity);
            ProductResponse resp = new ProductResponse();
            resp.updateFromEsResponse(esResp, from, size);
            return Response.ok(resp).build();
        } 
        catch (IOException e) {
            return Response.ok(ElasticClient.parseException(e)).build();
        }

    }
    
    
    @DELETE 
    @Path("/products/{productId}")
    @PermitAll
    @ApiOperation(value = "Delete product by ID ", response = BaseResponse.class)
    public Response deleteProduct (@ApiParam(example="P1", required=true) @DefaultValue("")  @PathParam("productId") String productId) {
        User userFromToken = (User)sc.getUserPrincipal();
        org.elasticsearch.client.Response esResp;
        Map<String, String> urlParams = Collections.emptyMap();
        BaseResponse resp = new BaseResponse();
        String submitData = ("{" 
           + "  `query`:{" 
           + "     `term`:{ `productId`: `%s` }"
           + "  }" 
           + "}").replace('`', '"');
        try { 
            submitData = String.format(submitData, productId);
            HttpEntity submitJsonEntity = new NStringEntity(submitData, ContentType.APPLICATION_JSON);
            esResp = ElasticClient.rest.performRequest("POST", "/products/products/_delete_by_query", urlParams, submitJsonEntity);
            Map.Entry<Integer, String> deleteMsg  = ElasticClient.getDeleteByQueryResponse(esResp);
        
            if (null == deleteMsg.getKey()){
                resp.setErrorMessage(String.format("%s products deleted (product-id:%s) ",deleteMsg.getKey(),productId));
            }
            else switch (deleteMsg.getKey()) {
                case -1:
                    resp.setErrorMessage(String.format("Error while deleting (product-id:%s) ",productId));
                    break;
                case 0:
                    resp.setErrorMessage(String.format("Product not found (product-id:%s) ",productId));
                    break;
                default:
                    resp.setErrorMessage(String.format("%s products deleted (product-id:%s) ",deleteMsg.getKey(),productId));
                    break;
            }
            return Response.ok(resp).build();
        } 
        catch (IOException e) {
            return Response.ok(ElasticClient.parseException(e)).build();
        }

    }
    
    @POST	
    @PermitAll
    @Path("/products")
    @ApiOperation(value = "Add a new product ", response = BaseResponse.class)
    public Response insertProduct (ProductInput prodIn) {
        
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
            return Response.ok(ElasticClient.parseException(e)).build();        
        }
    }
}
