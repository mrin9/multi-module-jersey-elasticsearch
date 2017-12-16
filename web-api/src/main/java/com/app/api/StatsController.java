package com.app.api;

import java.io.*;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.annotation.security.*;

import io.swagger.annotations.*;
import com.app.model.response.*;
import com.app.model.stats.SingleSerise;
import com.app.model.user.Login;
import com.app.model.user.LoginResponse;
import com.app.service.*;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.nio.entity.NStringEntity;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Log4j2
@Path("")
@Api(value = "Statictics")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StatsController  extends BaseController {

    
    @GET
    @Path("/stats/orders/group-by/{groupBy:orderStatus|paymentType}")
    @ApiOperation(value = "Order Stats ", response = PageResponse.class)
    public Response groupedOrderCount( @ApiParam(example="orderStatus", allowableValues="orderStatus,paymentType", required=true) @PathParam("groupBy") String groupBy){
        
        try {
            org.elasticsearch.client.Response esResp;
            String url = "/orders/orders/_search?filter_path=hits,aggregations";
            Map<String, String> urlParams = Collections.emptyMap();
            String submitData = ("{" 
                + "   `size`:0" 
                + "  ,`aggs`:{" 
                + "     `group_by`:{" 
                + "       `terms`:{`field`:`%s`}" 
                + "      }" 
                + "    }" 
                + "}").replace('`', '"');
            submitData = String.format(submitData, groupBy );
            
            HttpEntity submitJsonEntity = new NStringEntity(submitData, ContentType.APPLICATION_JSON);
            esResp = ElasticClient.rest.performRequest("GET", url, urlParams, submitJsonEntity);
            Map.Entry<Integer, List<SingleSerise>> aggrList  = ElasticClient.getGroupAggrFromResponse(esResp, "group_by" );
            
            PageResponse resp = new <SingleSerise>PageResponse();
            int total = (int)aggrList.getKey();
            resp.setItems(aggrList.getValue());
            if (resp.getItems() != null){
                resp.setPageStats(resp.getItems().size(), resp.getItems().size());
            }
            return Response.ok(resp).build();
        }
        catch (IOException e){
            log.info("Exeception:[" + e.getClass().getName()+"] " +  e.getMessage()  );
            log.info("Stack Trace:[" + ExceptionUtils.getStackTrace(e));
            ObjectNode esRespNode = ElasticClient.parseException(e);
            return Response.ok(esRespNode).build();
        }
        
    }

}
