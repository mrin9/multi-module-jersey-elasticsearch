package com.app.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ElasticClient {
    public static final String HOST = "localhost";
    public static final String CLUSTER_NAME = "elasticsearch";
    public static final int PORT = 9300;
    
     public static TransportClient connect() {
        try {
            Settings settings = Settings.builder().put("cluster.name", CLUSTER_NAME).build();
            TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName(HOST), PORT));
            return client;
        } 
        catch (UnknownHostException ex) {
            String errMsg = String.format("Unable to Connect to Elasticsearch (Host=%s:%d, Cluster=%s)", HOST, PORT, CLUSTER_NAME);
            log.error(errMsg);
        }
        return null;
    }
    
}
