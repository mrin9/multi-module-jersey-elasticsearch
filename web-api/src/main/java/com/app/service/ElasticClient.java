package com.app.service;

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
import org.elasticsearch.client.ResponseException;
import org.elasticsearch.rest.RestStatus;

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
    
}
