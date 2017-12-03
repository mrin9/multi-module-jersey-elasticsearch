package com.app.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.action.bulk.BulkRequest;



@Log4j2
public class InitData {
    public static void createIndex() {
        try {
            
            String schemaJsonUrl = InitData.class.getClassLoader().getResource("schema.json").getFile();
            String dataJsonUrl   = InitData.class.getClassLoader().getResource("data.json").getFile();
            Path   dataJsonPath = Paths.get(dataJsonUrl);

            
            String submitStr="";
            Response elResp;
             
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(schemaJsonUrl));
            String stringFromFile = new String(Files.readAllBytes(dataJsonPath));
            //First Delete all indexes
            elResp = ElasticClient.rest.performRequest("DELETE", "/*");
            
            //Create Index Order
            Map<String, String> urlParams = Collections.emptyMap();
            HttpEntity submitJsonEntity;
            //Create Indices
            /* Sample of submit string while creating index
            submitStr =  "{" 
            + "  'settings' : { "
            + "     'number_of_shards':1"
            + "  }," 
            + "  'mappings':{ " 
            + "     'orders': {" 
            + "        'properties':{" 
            + "           'orderId'    :{'type': 'text' },"
            + "           'empId'      :{'type': 'text' },"
            + "           'custId'     :{'type': 'text' },"
            + "           'orderDate'  :{'type': 'text' },"
            + "           'shippedDate':{'type': 'text' },"
            + "           'address1'   :{'type': 'text' },"
            + "           'address2'   :{'type': 'text' } "
            + "        }" 
            + "     }" 
            + "   }" 
            + "}";
            */

            // Create products_index
            submitStr = objectMapper.writeValueAsString(rootNode.get("products_index"));
            submitJsonEntity = new NStringEntity(submitStr, ContentType.APPLICATION_JSON);
            elResp = ElasticClient.rest.performRequest("PUT", "/products", urlParams, submitJsonEntity);

            // Create users_index
            submitStr = objectMapper.writeValueAsString(rootNode.get("users_index"));
            submitJsonEntity = new NStringEntity(submitStr, ContentType.APPLICATION_JSON);
            elResp = ElasticClient.rest.performRequest("PUT", "/users", urlParams, submitJsonEntity);

            // Create custiomer_index
            submitStr = objectMapper.writeValueAsString(rootNode.get("custiomer_index"));
            submitJsonEntity = new NStringEntity(submitStr, ContentType.APPLICATION_JSON);
            elResp = ElasticClient.rest.performRequest("PUT", "/customers", urlParams, submitJsonEntity);

            // Create orders_index
            submitStr = objectMapper.writeValueAsString(rootNode.get("orders_index"));
            submitJsonEntity = new NStringEntity(submitStr, ContentType.APPLICATION_JSON);
            elResp = ElasticClient.rest.performRequest("PUT", "/orders", urlParams, submitJsonEntity);

            
        }
        catch (IOException ex) {
            log.error("ERROR: >>> In Exception : " + ex.getMessage());
        }
    }
}
