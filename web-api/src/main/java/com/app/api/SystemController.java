package com.app.api;

import java.io.*;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.annotation.security.*;

import io.swagger.annotations.*;
import com.app.model.response.*;
import com.app.model.user.User;
import com.app.service.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.*;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.nio.entity.NStringEntity;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Log4j2
@Path("")
@Api(value = "System")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SystemController  extends BaseController {
   
    @GET 
    @Path("/system/data/{index:users|products|orders}/_show_all")
    @PermitAll
    //@RolesAllowed({"USER", "ADMIN"})
    @ApiOperation(value = "Search ", response = PageResponse.class)
    public Response getAllData( 
        @ApiParam(example="orders", allowableValues="users,products,orders", required=true)  @PathParam("index") String index, 
        @ApiParam(example="0"    , defaultValue="0"  , required=true) @DefaultValue("0")     @QueryParam("from") int from,
        @ApiParam(example="100"  , defaultValue="100", required=true) @DefaultValue("100")   @QueryParam("size") int size, 
        @ApiParam(value="sort field, prefix with '-' for descending order", example="-listPrice", defaultValue="-listPrice") @DefaultValue("-listPrice") @QueryParam("sort")  String sort, 
        @QueryParam("filter") String filter
    ) 
    throws Exception {
        try {
            org.elasticsearch.client.Response esResp;

            if (from<=0){from=0;}
            if (size==0 || size >500){size=500;}

            String url = String.format("/%s/%s/_search", index,index);
            Map<String, String> urlParams = Collections.emptyMap();
            String submitData = ("{" 
               + "   `from`:%s" 
               + "  ,`size`:%s" 
               + "  ,`query`:{" 
               + "    `match_all`:{}" 
               + "  }" 
               + "}").replace('`', '"');
            submitData = String.format(submitData, from,size);
            HttpEntity submitJsonEntity = new NStringEntity(submitData, ContentType.APPLICATION_JSON);

            esResp = ElasticClient.rest.performRequest("GET", url, urlParams, submitJsonEntity);
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
    
    @DELETE 
    @Path("/system/data/{index:users|products|orders|all}/_remove_data")
    @RolesAllowed({"ADMIN"})
    @ApiOperation(value = "Reset data to original state (all the modification done will be lost)", response = BaseResponse.class)
    public Response resetData (@ApiParam(example="all", allowableValues="users,products,orders,all")   @PathParam("index") String index) throws Exception {

        User userFromToken = (User)sc.getUserPrincipal();
        BaseResponse resp = new BaseResponse();
        // catch exceptions of org.elasticsearch.client.ResponseException type
        
        if (index.equalsIgnoreCase("all")){
            List<String> indexNameList = Arrays.asList(DataService.indexNamesArray);
            for (String tmpIndex : indexNameList) {
                DataService.deleteData(tmpIndex);
            }
        }
        else if (Util.isValidIndex(index)){
            DataService.deleteData(index);
        }
        
        resp.setSuccessMessage("Documents deleted ");
        return Response.ok(resp).build();
    }
    
    @POST 
    @Path("/system/elastic/_execute")
    @RolesAllowed({"ADMIN"})
    @ApiOperation(value = "Execute elastic Query)", response = BaseResponse.class)
    public Response resetData (
        @ApiParam(example="GET", allowableValues="GET,PUT,POST,DELETE") @QueryParam("method") String method,
        @ApiParam(example="/orders/orders/_search")  @QueryParam("url") String url,
        @ApiParam(example="{\"from\":0 ,\"size\":10, \"query\":{\"match_all\":{}}}")  @QueryParam("data") String data
    ) throws Exception{
        try {
            org.elasticsearch.client.Response esResp;
            Map<String, String> urlParams = Collections.emptyMap();
            HttpEntity submitJsonEntity;

            if (StringUtils.isBlank(data)){
                esResp = ElasticClient.rest.performRequest(method, url, urlParams);
            }
            else{
                submitJsonEntity = new NStringEntity(data, ContentType.APPLICATION_JSON);
                esResp = ElasticClient.rest.performRequest(method, url, urlParams, submitJsonEntity);
            }
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
