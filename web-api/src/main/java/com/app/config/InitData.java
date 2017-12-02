package com.app.config;

import com.app.MainApp;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.client.IndicesAdminClient;

@Log4j2
public class InitData {
    
    public static void createIndex() {
        IndicesAdminClient indicesService = MainApp.elasticClient.admin().indices();
        
        //Since we always recreate the index and load with initial dataset, So First Delete the index if exist
        /*
            Since version 6 of elasticsearch only one type is allowed per index 
        */
        final DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest( "orders", "products", "employees", "customers", "users" );
        indicesService.prepareCreate("orders").get();
        indicesService.prepareCreate("products").get();
        indicesService.prepareCreate("employees").get();
        indicesService.prepareCreate("customers").get();
        indicesService.prepareCreate("users").get();
        
        /*
        final DeleteIndexResponse deleteIndexResponse = indicesService.delete( deleteIndexRequest ).actionGet();
        if ( deleteIndexResponse.isAcknowledged() == false ){
            log.warn( "Cannot delete Indices : [orders, products, employees, customers, users] " );
        }
        else {
            log.debug( "Deleted Indices: [orders, products, employees, customers, users] deleted" );
        }
        */
        
        
        
    }
    
}
