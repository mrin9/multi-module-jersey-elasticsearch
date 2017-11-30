package com.app;

import java.io.File;
import java.util.concurrent.ThreadFactory;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

public class FolderMonitorService {

    private static FileAlterationMonitor monitor;
    
    //public static void main(String[] args) throws Exception {
    public static void start( String folderToMonitorPath, int pollingIntervalInMilis) throws Exception {
        
        if(folderToMonitorPath.trim().equalsIgnoreCase("")) {
            folderToMonitorPath = System.getProperty("user.dir");
        }
        if (pollingIntervalInMilis <= 1000){
            pollingIntervalInMilis = 30 * 1000;
        }
        final File directory = new File(folderToMonitorPath);
        if (directory.exists()==false) {
            // Test to see if monitored folder exists
            throw new RuntimeException("Directory not found: " + folderToMonitorPath);
        }
        else{
            System.out.println("Configuring Observer for Folder: (" + folderToMonitorPath + ")");
        }
        
        // Create a new FileAlterationObserver on the given directory
        FileAlterationObserver observer = new FileAlterationObserver(directory);
        // Create a new FileAlterationMonitor with the given pollingInterval period
        monitor = new FileAlterationMonitor(pollingIntervalInMilis);
        observer.addListener(new FileAlterationListenerImpl());
        monitor.addObserver(observer);
        monitor.setThreadFactory(new ThreadFactory() {
            public Thread newThread(Runnable runnable) { 
                Thread t = new Thread(runnable); 
                t.setDaemon(true); 
                t.setName("folder-watcher-thread"); 
                return t; 
            } 
        }); 
        monitor.start();
        System.out.println("Starting monitor (" + folderToMonitorPath + "). \"Press CTRL+C to stop\"");
    }
    
    public static void stop() throws Exception{
        monitor.stop();
        
    }
    
    
    public static void main(String[] args) throws Exception {
        String folderToMonitorPath="";
        int pollingIntervalInMilis=0;

        if (args.length==1){
            folderToMonitorPath=args[0];
        }
        else if(args.length==2){
            folderToMonitorPath=args[0];
            pollingIntervalInMilis = Integer.parseInt(args[1]);
        }
        start(folderToMonitorPath, pollingIntervalInMilis);
    }

}
