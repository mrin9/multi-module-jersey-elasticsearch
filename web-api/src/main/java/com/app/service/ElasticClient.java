package com.app.service;

import com.app.model.product.Product;
import com.app.model.response.BaseResponse;
import com.app.model.response.MultiMessageResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.math.BigDecimal;
import lombok.extern.log4j.Log4j2;
import org.apache.http.ContentTooLongException;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.Response;
import org.elasticsearch.rest.RestStatus;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.ElasticsearchParseException;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.ElasticsearchCorruptionException;
import org.elasticsearch.ElasticsearchSecurityException;
import java.net.ConnectException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.client.ResponseException;


@Log4j2
public class ElasticClient {
    public static final String HOST = "localhost";
    public static final String CLUSTER_NAME = "elasticsearch";
    public static final int PORT = 9200;
    public static RestClientBuilder serverAddress;
    public static RestClient rest;
    public static RestHighLevelClient simpleRest;
            
    public static void init() {
        serverAddress = RestClient.builder(new HttpHost(HOST, PORT, "http"));
        serverAddress.setFailureListener(new RestClient.FailureListener() {
            @Override
            public void onFailure(HttpHost host) {
                log.error("Rest Client Failed " + host.getHostName() +":" + host.getPort());
            }
        });
        rest = serverAddress.build();
        simpleRest = new RestHighLevelClient(serverAddress);
    }
    
    public static String getStringFromResponse(Response esResp) {
        try {
            return EntityUtils.toString(esResp.getEntity());
        }
        catch (IOException e){
            log.error("Unable to get Response Body: " + e.getMessage() );
            return "";
        }
    }

    public static JsonNode getJsonNodeResponse(Response esResp) throws IOException, ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(getStringFromResponse(esResp));
    }

    
    //Return total and List of Items (if total is -1 it indicates there is some error
    public static <T> Map.Entry<Integer, List<T>>  getTotalAndListFromSearchQueryResponse(Response esResp, Class<T> genericClass)  throws IOException, ParseException {

        ObjectMapper mapper = new ObjectMapper();
        Integer total=0;
        List<T> list = new ArrayList<>();
        JsonNode esRespNode = ElasticClient.getJsonNodeResponse(esResp);
        Map.Entry<Integer, List<T>> returnVal;
        
        if (esRespNode.has("error")==false) {
            total = esRespNode.path("hits").path("total").asInt(-1);
            JsonNode nodeTree =  esRespNode.path("hits").path("hits");
            if (nodeTree.isArray()){
                for (int i=0; i < nodeTree.size(); i++){
                    JsonNode tmpNode = nodeTree.get(i).path("_source");
                    if (tmpNode.isMissingNode()==false){
                        T genericInstance = (T) mapper.convertValue(tmpNode, genericClass);
                        list.add(genericInstance);
                    }
                }
                returnVal = new AbstractMap.SimpleImmutableEntry<>(total, list);
            }
            else{
                returnVal = new AbstractMap.SimpleImmutableEntry<>(-1, list);
            }
        }
        else{
            returnVal = new AbstractMap.SimpleImmutableEntry<>(-1, list);
        }
        return returnVal;
    }    
    
    /*
    public static MultiMessageResponse getDeleteByQueryResponse(Response esResp, String successMsg, String notFoundMsg, String errorMsg ) {
        MultiMessageResponse restResp = new MultiMessageResponse();
        ObjectNode respJsonNode = parseResponse(esResp);
        int total=0, deleted =0;
        if (respJsonNode.has("esResponse")){
            JsonNode actualEsResNode = respJsonNode.path("esResponse");
            total = actualEsResNode.path("total").asInt(0);
            deleted = actualEsResNode.path("deleted").asInt(0);
            if (total==0){
                restResp.setErrorMessage(notFoundMsg);
            }
            else if (total > 0 & total == deleted ){
                restResp.setSuccessMessage(successMsg);
            }
            else{
                restResp.setErrorMessage(errorMsg);
            }
        }
        else if (respJsonNode.has("exception")){
            restResp.setErrorMessage(respJsonNode.path("exception").asText("_exception"));
        }
        else{
            restResp.setErrorMessage("Unknown Error");
        }
        return restResp;
    }
    */
    
    // Returns no of items deleted and message (0 indicates not found, -1 indicates error and +ve is the count of delete iiems)
    public static Map.Entry<Integer, String> getDeleteByQueryResponse(Response esResp ) {
        ObjectNode respJsonNode = parseResponse(esResp);
        Map.Entry<Integer, String> returnVal;
        int total=0, deleted =0;
        if (respJsonNode.has("esResponse")){
            JsonNode actualEsResNode = respJsonNode.path("esResponse");
            total = actualEsResNode.path("total").asInt(0);
            deleted = actualEsResNode.path("deleted").asInt(0);
            if (total==0){
                returnVal = new AbstractMap.SimpleImmutableEntry<>(0, "not_found:No items found ");
            }
            else if (total > 0 & total == deleted ){
                returnVal = new AbstractMap.SimpleImmutableEntry<>(deleted, String.format("success: Deleted %s items", deleted));
            }
            else{
                returnVal = new AbstractMap.SimpleImmutableEntry<>(-1, String.format("incomplete_deletion: total=%s, deleted=%s", total, deleted));
            }
        }
        else if (respJsonNode.has("exception")){
            returnVal = new AbstractMap.SimpleImmutableEntry<>(-1, String.format("error: %s", respJsonNode.path("exception").asText("_exception")));
        }
        else{
            returnVal = new AbstractMap.SimpleImmutableEntry<>(-1, "error: Unknown error");
        }
        return returnVal;
    }
    
    
    
    public static ObjectNode parseResponse(Response esResp) {
        ObjectNode respJsonNode = JsonNodeFactory.instance.objectNode();
        try {
            JsonNode esRespNode = getJsonNodeResponse(esResp);
            if (esRespNode.has("error")){
                JsonNode errNode = esRespNode.get("error");
                if (errNode.isObject()){
                    respJsonNode.put("msgType", "ERROR");
                    respJsonNode.put("msg", esRespNode.path("status").asText("_status")); 
                    respJsonNode.put("esErrType",      errNode.get("type").asText("_type")); 
                    respJsonNode.put("esErrReason",    errNode.get("reason").asText("_reason"));
                    respJsonNode.put("esResourceType", esRespNode.path("resource.type").asText("_resource.type"));
                    respJsonNode.put("esResourceName", esRespNode.path("index").asText("_index"));
                    respJsonNode.set("esResponse", esRespNode);
                }
                else if (errNode.isBoolean() && errNode.asBoolean() == true){
                    respJsonNode.put("msgType", "ERROR");
                    respJsonNode.put("msg", esRespNode.path("status").asText("_status")); // check Bulk response
                }
                else if (errNode.isBoolean() && errNode.asBoolean() == false){
                    // Parse Success Response Here
                    respJsonNode.put("msgType", "SUCCESS");
                    respJsonNode.put("msg", "Response From Elasticseach");
                    respJsonNode.set("esResponse", esRespNode);
                }
                else if (errNode.isTextual()){
                    respJsonNode.put("msgType", "ERROR");
                    respJsonNode.put("msg", esRespNode.path("error").asText("_error"));
                    respJsonNode.set("esResponse", esRespNode);
                }
            }
            else{
                // Parse Success Response Here
                respJsonNode.put("msgType", "SUCCESS");
                respJsonNode.put("msg", "Response From Elasticseach");
                respJsonNode.set("esResponse", esRespNode);
            }
            return respJsonNode;
        } 
        catch (IOException e) {
            log.info("Response Parse IOException");
            respJsonNode.put("msgType", "ERROR");
            respJsonNode.put("msg", "Response IOException");
            respJsonNode.put("exception", "[" + e.getClass() +"] " + e.getMessage());
            return respJsonNode;
        } 
        catch (ParseException e) {
            log.info("Jackson JSON Parse Exception");
            respJsonNode.put("msgType", "ERROR");
            respJsonNode.put("msg", "Response JSON Parse Exception");
            respJsonNode.put("exception", "[" + e.getClass() +"] " + e.getMessage());
            return respJsonNode;
        }
    }
    
    
    public static ObjectNode parseException(Exception ex) {
        ObjectNode respJsonNode = JsonNodeFactory.instance.objectNode();
        String errMsg ="";
        if (ex instanceof ResponseException){
            Response elResp = ((ResponseException)ex).getResponse();
            return parseResponse(elResp);
        }
        else if (ex instanceof ConnectException){
            errMsg = "Connection Exception: Unable to Connect to Elasticsearch" ;
        }
        else if (ex instanceof ElasticsearchStatusException){
            errMsg="Status Exception:" + ((ElasticsearchStatusException)ex).getMessage();
        }
        else if (ex instanceof ElasticsearchCorruptionException){
            errMsg = "Curruption Exception: " + ((ElasticsearchCorruptionException)ex).getMessage();
        }
        else if (ex instanceof ElasticsearchParseException){
            errMsg = "Parse Exception: " + ((ElasticsearchParseException)ex).getMessage();
        }
        else if (ex instanceof ElasticsearchSecurityException){
            errMsg = "Security Exception: " + ((ElasticsearchSecurityException)ex).getMessage();
        }
        else{
            errMsg = ex.getClass().getName() +": " + ex.getMessage();
        }
        log.error(errMsg);
        respJsonNode.put("msgType", "ERROR");
        respJsonNode.put("msg", errMsg);
        return respJsonNode;
    }
    

    
    


    
    
}
