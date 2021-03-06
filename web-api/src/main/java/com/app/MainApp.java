package com.app;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import com.app.filter.CORSResponseFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import com.app.service.ElasticClient;
import com.app.service.DataService;
import com.app.DataGenerator;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.ElasticsearchException;


//@ApplicationPath("api") // (Dont work in a .jar may work in .war )
@Log4j2
public class MainApp extends ResourceConfig {

   
   public MainApp(@Context ServletContext servletContext) throws Exception {
        log.info("*** Jersey Init ***");
       
        // Register Features
        register(JacksonFeature.class );
        register(MultiPartFeature.class);
        
        // Register Filters 
        register(CORSResponseFilter.class);

        // Regster Source Packages
        packages("io.swagger.jaxrs.json");
        packages("io.swagger.jaxrs.listing");
        packages("com.app.api");
        
        log.info("\n *** System Variables *** \n");
        log.info("user.home     :" + System.getProperty("user.home"));
        log.info("user.dir      :" + System.getProperty("user.dir"));
        log.info("catalina.home :" + System.getProperty("catalina.home"));
        log.info("catalina.base :" + System.getProperty("catalina.base") +"\n\n");
        
        //log.info("\n Creating elasticsearch Data Files for bulk command... \n");
        //DataGenerator.generateElasticData("");

        log.info("\n Connecting To ElasticSearch... \n");
        try{
            ElasticClient.init();  // TODO: ensure its called only once
            DataService.resetAllData();
        }
        catch(java.net.ConnectException|ElasticsearchException e){
            ElasticClient.parseException(e);
        }
        
    }
   
   
    
}
