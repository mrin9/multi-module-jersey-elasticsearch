package com.app;


import com.app.config.ElasticClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import lombok.extern.log4j.Log4j2;


@Log4j2
@WebListener
public class StartStopServerListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("Starting Servlet"); 
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("Stopping Servlet");
        try {
            ElasticClient.rest.close();
            ElasticClient.simpleRest.close();
        } 
        catch (IOException ex) {
            log.info("Error Stopping Services (Elastic Client): " + ex.getMessage() );
        }
        
    }
}
