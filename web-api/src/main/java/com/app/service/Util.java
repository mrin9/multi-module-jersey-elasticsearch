package com.app.service;

import java.io.*;

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
    
}
