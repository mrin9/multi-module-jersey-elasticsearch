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
            
            //Create Index Order
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(schemaJsonUrl));

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
            
            
            /* ************  INDEX CREATION *********** */
            
            // Create users_index
            submitStr = objectMapper.writeValueAsString(rootNode.get("users_index"));
            submitJsonEntity = new NStringEntity(submitStr, ContentType.APPLICATION_JSON);
            elSearchResp = ElasticClient.rest.performRequest("PUT", "/users", urlParams, submitJsonEntity);

            // Create products_index
            submitStr = objectMapper.writeValueAsString(rootNode.get("products_index"));
            submitJsonEntity = new NStringEntity(submitStr, ContentType.APPLICATION_JSON);
            elSearchResp = ElasticClient.rest.performRequest("PUT", "/products", urlParams, submitJsonEntity);

            // Create orders_index
            submitStr = objectMapper.writeValueAsString(rootNode.get("orders_index"));
            submitJsonEntity = new NStringEntity(submitStr, ContentType.APPLICATION_JSON);
            elSearchResp = ElasticClient.rest.performRequest("PUT", "/orders", urlParams, submitJsonEntity);
            
            
            /* ************  DATA INSERTION *********** */
            // Insert Data into Users index
            dataJsonUrl  = FillData.class.getClassLoader().getResource("users.dat").getFile();
            dataJsonPath  = Paths.get(dataJsonUrl);
            submitStr = new String(Files.readAllBytes(dataJsonPath));
            submitJsonEntity = new NStringEntity(submitStr, ContentType.APPLICATION_JSON);
            elSearchResp = ElasticClient.rest.performRequest("POST", "/users/users/_bulk", urlParams, submitJsonEntity);
            log.info("HTTP Response Code For Users Insert: " + elSearchResp.getStatusLine().getStatusCode() );

            // Insert Data into Products index
            dataJsonUrl  = FillData.class.getClassLoader().getResource("products.dat").getFile();
            dataJsonPath  = Paths.get(dataJsonUrl);
            submitStr = new String(Files.readAllBytes(dataJsonPath));
            submitJsonEntity = new NStringEntity(submitStr, ContentType.APPLICATION_JSON);
            elSearchResp = ElasticClient.rest.performRequest("POST", "/products/products/_bulk", urlParams, submitJsonEntity);
            log.info("HTTP Response Code For Product Insert: " + elSearchResp.getStatusLine().getStatusCode() );

            // Insert Data into Orders index
            dataJsonUrl  = FillData.class.getClassLoader().getResource("orders.dat").getFile();
            dataJsonPath  = Paths.get(dataJsonUrl);
            submitStr = new String(Files.readAllBytes(dataJsonPath));
            submitJsonEntity = new NStringEntity(submitStr, ContentType.APPLICATION_JSON);
            elSearchResp = ElasticClient.rest.performRequest("POST", "/orders/orders/_bulk", urlParams, submitJsonEntity);
            log.info("Response Code For Orders Insert: " + elSearchResp.getStatusLine().getStatusCode() );
        
        }
        catch (IOException ex) {
            log.error("ERROR: >>> In Exception : " + ex.getMessage());
        }
    }
}
