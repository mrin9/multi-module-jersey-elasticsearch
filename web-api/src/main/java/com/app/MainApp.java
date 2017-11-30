package com.app;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import com.app.filter.CORSResponseFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import com.app.FolderMonitorService;

//@ApplicationPath("api") // (Dont work in a .jar may work in .war )
public class MainApp extends ResourceConfig {
    
   public MainApp(@Context ServletContext servletContext) throws Exception {
        System.out.print("\n *** Jersey Init *** \n");
        
        // Register Features
        register(JacksonFeature.class );
        register(MultiPartFeature.class);
        
        // Register Filters 
        register(CORSResponseFilter.class);

        // Regster Source Packages
        packages("io.swagger.jaxrs.json");
        packages("io.swagger.jaxrs.listing");
        packages("com.app.api");
        
        System.out.print("\n *** System Variables *** \n");
        System.out.println("user.home     :" + System.getProperty("user.home"));
        System.out.println("user.dir      :" + System.getProperty("user.dir"));
        System.out.println("catalina.home :" + System.getProperty("catalina.home"));
        System.out.println("catalina.base :" + System.getProperty("catalina.base") +"\n\n");
        
        System.out.print("\n *** Folder Watch Init *** \n");
        FolderMonitorService.start("", 30000);
        
    }
   
   
    
}
