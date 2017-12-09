package com.app.service;

import com.app.model.response.MultiMessageResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    
    
    public static MultiMessageResponse parseResponse(Response esResp) {
        MultiMessageResponse restResp = new MultiMessageResponse();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String responseBody = EntityUtils.toString(esResp.getEntity());
            JsonNode rootNode = objectMapper.readTree(responseBody);
            if (rootNode.has("error")){
                
                JsonNode errNode = rootNode.get("error");
                if (errNode.isObject()){
                    restResp.setErrorMessage(rootNode.path("status").asText());
                    restResp.addSubMessage(errNode.get("type").asText("_type"), errNode.get("reason").asText("_reason"));
                    restResp.addSubMessage(errNode.path("resource.type").asText("_resource.type"), errNode.path("index").asText("_index"));
                }
                else if (errNode.isBoolean() && errNode.asBoolean() == true){
                    restResp.setErrorMessage(rootNode.path("status").asText()); // check bulk responses
                }
                else if (errNode.isTextual()){
                    restResp.setErrorMessage(rootNode.path("error").asText("_errorMessage"));
                }
                else{
                    // Parse Success Response Here
                    restResp.setSuccessMessage(rootNode.path("status").asText());
                }
            }
            return restResp;
        } 
        catch (IOException ex) {
            log.info("Response Parse IOException");
            restResp.setErrorMessage("Response Parse IOException");
            return restResp;
        } 
        catch (ParseException ex) {
            log.info("Jackson JSON Parse Exception");
            restResp.setErrorMessage("Response Parse IOException");
            return restResp;
        }
    }
    
    public static MultiMessageResponse parseException(Exception ex) {
        MultiMessageResponse restResp = new MultiMessageResponse();
        if (ex instanceof ResponseException){
            Response elResp = ((ResponseException)ex).getResponse();
            return parseResponse(elResp);
        }
        
        if (ex instanceof ElasticsearchStatusException){
            restResp.setErrorMessage(  " Status Exception:" + ((ElasticsearchStatusException)ex).getMessage());
            return restResp;
        }
        if (ex instanceof ElasticsearchCorruptionException){
            restResp.setErrorMessage(  " Curruption Exception:" + ((ElasticsearchCorruptionException)ex).getMessage());
            return restResp;
        }
        if (ex instanceof ElasticsearchParseException){
            restResp.setErrorMessage(  " Parse Exception:" + ((ElasticsearchParseException)ex).getMessage());
            return restResp;
        }
        if (ex instanceof ElasticsearchSecurityException){
            restResp.setErrorMessage(  " Security Exception:" + ((ElasticsearchSecurityException)ex).getMessage());
            return restResp;
        }
        if (ex instanceof ConnectException){
            restResp.setNoConnectionMessage("Unable to Connect to Elasticsearch");
            return restResp;
        }
        restResp.setErrorMessage(ex.getClass().getName() +": " + ex.getMessage());
        return restResp;
    }

    
}
