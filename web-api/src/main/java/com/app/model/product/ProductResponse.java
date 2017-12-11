package com.app.model.product;

import io.swagger.annotations.*;
import lombok.*;
import java.util.*;
import com.app.model.response.*;
import com.app.service.ElasticClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.apache.http.ParseException;
import org.elasticsearch.client.Response;

@Log4j2
@Data
@EqualsAndHashCode(callSuper=false)
public class ProductResponse extends PageResponse {
    
    @ApiModelProperty(required = true, value = "")
    private List<Product> items = new ArrayList<>();
  
    public void parseFromEsResponse(Response esResp, int from, int size ) throws IOException, ParseException{
        ObjectNode respJsonNode = JsonNodeFactory.instance.objectNode();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode esRespNode = ElasticClient.getJsonFromESResponse(esResp);
        
        if (esRespNode.has("error")==false) {
            int total = esRespNode.path("hits").path("total").asInt(-1);
            JsonNode prodTree =  esRespNode.path("hits").path("hits");
            if (prodTree.isArray()){
                for (int i=0; i < prodTree.size(); i++){
                    JsonNode tmpProdJson = prodTree.get(i).path("_source");
                    if (tmpProdJson.isMissingNode()==false){
                        Product p = mapper.convertValue(tmpProdJson, Product.class);
                        this.items.add(p);
                    }
                }
                this.setPageStats(total, size, from, items.size() );
            }
            else{
                this.setErrorMessage("Unable to parse elastic search response structure");
            }
        }

    }  
}
