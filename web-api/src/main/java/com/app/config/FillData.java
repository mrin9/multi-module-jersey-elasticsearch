package com.app.config;

import com.app.service.Util;
import java.io.*;
import java.util.*;
import java.nio.file.*;

import com.fasterxml.jackson.databind.*;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;

@Log4j2
public class FillData {
    public static void fromFile() {
        try {
            
            String schemaJsonUrl = FillData.class.getClassLoader().getResource("schema.json").getFile();
            String dataJsonUrl;
            Path   dataJsonPath;
            
            String submitStr="";
            Response elSearchResp;
            Map<String, String> urlParams = Collections.emptyMap();
            HttpEntity submitJsonEntity;
            
            //First Delete all indexes
            elSearchResp = ElasticClient.rest.performRequest("DELETE", "/*");
            
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(schemaJsonUrl));

            /* ************  INDEX CREATION *********** */
            
            // Create users_index
            submitStr = objectMapper.writeValueAsString(rootNode.get("users_index"));
            submitJsonEntity = new NStringEntity(submitStr, ContentType.APPLICATION_JSON);
            elSearchResp = ElasticClient.rest.performRequest("PUT", "/users", urlParams, submitJsonEntity);
            log.info("User Index Created");
            
            // Create products_index
            submitStr = objectMapper.writeValueAsString(rootNode.get("products_index"));
            submitJsonEntity = new NStringEntity(submitStr, ContentType.APPLICATION_JSON);
            elSearchResp = ElasticClient.rest.performRequest("PUT", "/products", urlParams, submitJsonEntity);
            log.info("Products Index Created");

            // Create orders_index
            submitStr = objectMapper.writeValueAsString(rootNode.get("orders_index"));
            submitJsonEntity = new NStringEntity(submitStr, ContentType.APPLICATION_JSON);
            elSearchResp = ElasticClient.rest.performRequest("PUT", "/orders", urlParams, submitJsonEntity);
            log.info("Orders Index Created");
            
            
            /* ************  DATA INSERTION *********** */
            // Insert Data into Users index
            
            submitStr = Util.getFileContent("users.dat");
            submitJsonEntity = new NStringEntity(submitStr, ContentType.APPLICATION_JSON);
            elSearchResp = ElasticClient.rest.performRequest("POST", "/users/users/_bulk", urlParams, submitJsonEntity);
            log.info("HTTP Response (Users Insert): " + elSearchResp.getStatusLine().getStatusCode() );

            // Insert Data into Products index
            submitStr = Util.getFileContent("products.dat");
            submitJsonEntity = new NStringEntity(submitStr, ContentType.APPLICATION_JSON);
            elSearchResp = ElasticClient.rest.performRequest("POST", "/products/products/_bulk", urlParams, submitJsonEntity);
            log.info("HTTP Response (Product Insert): " + elSearchResp.getStatusLine().getStatusCode() );

            // Insert Data into Orders index
            submitStr = Util.getFileContent("orders.dat");
            submitJsonEntity = new NStringEntity(submitStr, ContentType.APPLICATION_JSON);
            elSearchResp = ElasticClient.rest.performRequest("POST", "/orders/orders/_bulk", urlParams, submitJsonEntity);
            log.info("HTTP Response (Order Insert): " + elSearchResp.getStatusLine().getStatusCode() );

            //String responseBody = EntityUtils.toString(elSearchResp.getEntity()); 
            
        }
        catch (IOException ex) {
            log.error("ERROR: >>> In Exception : " + ex.getMessage());
        }
    }
}
