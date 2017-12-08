package com.app.service;

import static com.app.service.DataService.indexNamesArray;
import java.io.*;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class Util {
 
    public static String getFileContent(String fileName) throws IOException {
        String content="";
        if (fileName != null){
            fileName =  (fileName.startsWith("/") || fileName.startsWith("\\") )?"/" + fileName.substring(1) : "/"+ fileName;
            try (InputStream is = Util.class.getClassLoader().getResourceAsStream("/"+fileName)){
                String line="";
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                    while ((line = br.readLine()) != null) {
                        if (line.startsWith("#")) { continue; }
                        content = content + line + "\n";
                    }
                }
                is.close();
            }
        }
        return content;
    }
    
    public static boolean isValidIndex(String indexName){
        if (StringUtils.isNotBlank(indexName)){
            if(Arrays.asList(DataService.indexNamesArray).contains(indexName)){
                return true;
            }
        }
        return false;
    }
    
}
