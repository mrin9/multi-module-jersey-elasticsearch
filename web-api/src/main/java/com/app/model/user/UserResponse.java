package com.app.model.user;

import java.util.*;
import lombok.*;
import com.app.model.response.*;
import com.app.service.ElasticClient;
import java.io.IOException;
import org.apache.http.ParseException;
import org.elasticsearch.client.Response;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserResponse extends PageResponse {
    private List<User> items = new ArrayList<>();
    
    public void updateFromEsResponse(Response esResp, int from, int size ) throws IOException, ParseException{
        Map.Entry<Integer, List<User>> totalAndList  = ElasticClient.<User>getTotalAndListFromSearchQueryResponse(esResp, User.class);
        int total = (int)totalAndList.getKey();
        this.items = totalAndList.getValue();
        if (this.items != null){
            this.setPageStats(total, size, from, items.size() );
        }
    } 
    
    
}
