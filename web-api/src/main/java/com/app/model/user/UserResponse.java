package com.app.model.user;

import java.util.*;
import lombok.*;
import com.app.model.response.*;
import com.app.service.ElasticClient;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import org.apache.http.ParseException;
import org.elasticsearch.client.Response;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserResponse extends PageResponse {
    private List<User> items = new ArrayList<>();
    
    /*
    public void updateFromEsResponse(Response esResp, int from, int size ) throws IOException, ParseException{
        JsonNode esRespNode = ElasticClient.getJsonNodeResponse(esResp);
        if (esRespNode.has("error")==false) {
            int total = esRespNode.path("hits").path("total").asInt(-1);
            this.items = ElasticClient.getTotalAndListFromSearchQueryResponse(esRespNode, User.class);
            if (this.items != null){
                this.setPageStats(total, size, from, items.size() );
            }
        }
        else{
            this.setErrorMessage("Unable to parse elastic search response structure");
        }
    } 
    */
    
    
}
