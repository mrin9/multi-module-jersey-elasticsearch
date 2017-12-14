package com.app.api;

import com.app.model.order.OrderResponse;
import java.io.*;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.annotation.security.PermitAll;

import com.app.model.response.*;
import com.app.model.user.User;
import com.app.model.product.*;
import com.app.service.ElasticClient;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;


@Log4j2
@Path("")
@Api(value = "Orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderController extends BaseController{
    
    @GET 
    @Path("/orders")
    @PermitAll
    //@RolesAllowed({"USER", "ADMIN"})
    @ApiOperation(value = "Serach Orders ", response = ProductResponse.class)
    public Response search( 
        @ApiParam(example="0"  , defaultValue="0" , required=true) @DefaultValue("1")  @QueryParam("from") int from,
        @ApiParam(example="5"  , defaultValue="5" , required=true) @DefaultValue("5")  @QueryParam("size") int size, 
        @ApiParam(value="sort field, prefix with '-' for descending order", example="-totalPrice", defaultValue="")  @QueryParam("sort")  String sort, 
        @QueryParam("filter") String filter
    ) {
        
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
            esResp = ElasticClient.rest.performRequest("GET", "/orders/orders/_search?filter_path=hits.total,hits.hits._source", urlParams, submitJsonEntity);
            OrderResponse resp = new OrderResponse();
            resp.updateFromEsResponse(esResp, from, size);
            return Response.ok(resp).build();
        }
        catch (IOException ex) {
            return Response.ok(ElasticClient.parseException(ex)).build();
        }

    }
    
    
    @DELETE 
    @Path("/orders/{orderId}")
    @PermitAll
    @ApiOperation(value = "Delete order by ID ", response = BaseResponse.class)
    public Response deleteOrder (@ApiParam(example="O1", required=true) @DefaultValue("")  @PathParam("orderId") String orderId){
        User userFromToken = (User)sc.getUserPrincipal();
        org.elasticsearch.client.Response esResp;
        Map<String, String> urlParams = Collections.emptyMap();
        BaseResponse resp = new BaseResponse();
        String submitData = ("{" 
           + "  `query`:{" 
           + "     `term`:{ `orderId`: `%s` }"
           + "  }" 
           + "}").replace('`', '"');
        submitData = String.format(submitData, orderId);
        
        try{
            HttpEntity submitJsonEntity = new NStringEntity(submitData, ContentType.APPLICATION_JSON);
            esResp = ElasticClient.rest.performRequest("POST", "/orders/orders/_delete_by_query", urlParams, submitJsonEntity);
            Map.Entry<Integer, String> deleteMsg  = ElasticClient.getDeleteByQueryResponse(esResp);

            if (null == deleteMsg.getKey()){
                resp.setErrorMessage(String.format("%s orders deleted (order-id:%s) ",deleteMsg.getKey(),orderId));
            }
            else switch (deleteMsg.getKey()) {
                case -1:
                    resp.setErrorMessage(String.format("Error while deleting (order-id:%s) ",orderId));
                    break;
                case 0:
                    resp.setErrorMessage(String.format("Product not found (order-id:%s) ",orderId));
                    break;
                default:
                    resp.setErrorMessage(String.format("%s products deleted (order-id:%s) ",deleteMsg.getKey(),orderId));
                    break;
            }
            return Response.ok(resp).build();
        }
        catch (IOException e) {
            return Response.ok(ElasticClient.parseException(e)).build();
        }
            

    }
    
    /*
    @POST	
    @PermitAll
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
*/
}
