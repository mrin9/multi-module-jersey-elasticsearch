package com.app.model.order;

import io.swagger.annotations.*;
import lombok.*;
import java.util.*;
import com.app.model.response.*;
import com.app.service.ElasticClient;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;
import org.apache.http.ParseException;
import org.elasticsearch.client.Response;

@Log4j2
@Data
public class OrderResponse extends PageResponse {
    
    @ApiModelProperty(required = true, value = "")
    private List<Order> items = new ArrayList<>();
    
    public void updateFromEsResponse(Response esResp, int from, int size ) throws IOException, ParseException{
        Map.Entry<Integer, List<Order>> totalAndList  = ElasticClient.<Order>getTotalAndListFromSearchQueryResponse(esResp, Order.class);
        int total = (int)totalAndList.getKey();
        this.items = totalAndList.getValue();
        if (this.items != null){
            this.setPageStats(total, size, from, items.size() );
        }
    }
    
    
}
