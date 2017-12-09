package com.app.model.response;

import io.swagger.annotations.*;
import java.util.*;
import lombok.*;

@Data //for getters and setters
public class MultiMessageResponse extends BaseResponse{
    private List<Map<String,String>> subMessages = new ArrayList<>();
    
    public void addSubMessage(String key, String value){
        HashMap<String,String> subMessageMap = new HashMap();
        subMessageMap.put(key,value);
        subMessages.add(subMessageMap);
    }
    
}
