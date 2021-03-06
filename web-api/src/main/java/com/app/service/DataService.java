package com.app.service;

import java.util.*;

import com.fasterxml.jackson.databind.*;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;

@Log4j2
public class DataService {
    
    public static final String[] indexNamesArray = new String[] {"users","products","orders"};

    public static void resetAllData() throws Exception {
        Response esResp;
        List<String> indexNameList = Arrays.asList(indexNamesArray);
        
        //First Delete all indexes 
        esResp = DataService.deleteIndex("all");
        log.info( "All Index Deleted");

        //Create all the indexes
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(Util.getFileContent("schema.json"));
        for (String index : indexNameList) {
            String indexSchema = objectMapper.writeValueAsString(rootNode.get(index+"_index"));
            DataService.createIndex(index, indexSchema);
        }

        //Insert data into the indexes
        for (String index : indexNameList) {
            DataService.insertDataFromFile(index);
        }
        
    }

    
    public static Response createIndex(String index, String indexSchema) throws Exception {
        if (Util.isValidIndex(index) == false ){
           throw new Exception ("Index can be one of [" + String.join(",", indexNamesArray) + "]" ); 
        }
        Map<String, String> emptyUrlParams = Collections.emptyMap();
        HttpEntity submitJsonEntity = new NStringEntity(indexSchema, ContentType.APPLICATION_JSON);
        Response esResp = ElasticClient.rest.performRequest("PUT", "/"+index, emptyUrlParams, submitJsonEntity);
        log.info( index +" Index Created");
        return esResp;
    }
    
    public static Response deleteIndex(String index) throws Exception {
        if (index.equalsIgnoreCase("all")){
            return ElasticClient.rest.performRequest("DELETE", "orders,products,users"); //Delete all indexes
        }
        else if( Util.isValidIndex(index)){
            return ElasticClient.rest.performRequest("DELETE", "/"+index);
        }
        else {
            throw new Exception ("Index can be either " + String.join(",", indexNamesArray) +" or all");
        }
    }

    
    public static Response insertDataFromFile(String index) throws Exception {
        if (Util.isValidIndex(index) == false ){
           throw new Exception ("Index can be one of [" + String.join(",", indexNamesArray) + "]" ); 
        }
        Response esResp;
        Map<String, String> emptyUrlParams = Collections.emptyMap();
        String fileName="", postUrl="", submitData = "" ;

        index = index.toLowerCase().trim();
        fileName = index+".dat";
        postUrl = String.format("/%s/%s/_bulk", index,index);
        submitData = Util.getFileContent(fileName);
        
        HttpEntity submitJsonEntity = new NStringEntity(submitData, ContentType.APPLICATION_JSON);
        esResp = ElasticClient.rest.performRequest("POST", postUrl, emptyUrlParams, submitJsonEntity);
        log.info("HTTP Response ("+ index +" insert): " + esResp.getStatusLine().getStatusCode() );
        return esResp;
    }

    public static Response deleteData(String index) throws Exception {
        if (Util.isValidIndex(index) == false ){
           throw new Exception ("Index can be one of [" + String.join(",", indexNamesArray) + "]" ); 
        }
        Map<String, String> emptyUrlParams = Collections.emptyMap();
        String submitData = ("{" 
           + "  `query`:{" 
           + "    `match_all`:{}" 
           + "  }" 
           + "}").replace('`', '"');
        log.info(submitData);
        HttpEntity submitJsonEntity = new NStringEntity(submitData, ContentType.APPLICATION_JSON);
        return ElasticClient.rest.performRequest("POST", "/"+index+"/"+index+"/_delete_by_query", emptyUrlParams, submitJsonEntity);
    }

    
    
    
}
