package com.app.config;



import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

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
