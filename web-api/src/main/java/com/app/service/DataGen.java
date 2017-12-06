package com.app.service;

import com.app.config.FillData;
import com.github.javafaker.Faker;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

@Log4j2
public class DataGen {
    
    public static final String userFileName   ="users.dat";
    public static final String productFileName="products.dat";
    public static final String orderFileName  ="orders.dat";
    private static List<String[]> prodList;
    private static List<String[]> custList;
    
    public static void allData(String folderPath){
        try {
            if (StringUtils.isBlank(folderPath)){
                folderPath = System.getProperty("user.home") + File.separator +"work"+ File.separator +"files";
            }
            readProdMaster();
            usersData(folderPath );
            productsData(folderPath);
            //ordersData(folderPath);
        } 
        catch (IOException ex) {
            Logger.getLogger(DataGen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void readProdMaster() throws IOException{
        String prodMasterUrl = FillData.class.getClassLoader().getResource("product_master.csv").getFile();
        String line = "";
        String splitChar = "\\s*,\\s*"; // comma with leading and trailing spaces
        prodList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(prodMasterUrl))) {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("#")){continue;}
                String[] products = line.split(splitChar);
                prodList.add(products);
            }
        }
    }
    
    public static void productsData(String folderPath) throws IOException{

        String productFilePath = folderPath + File.separator + productFileName;
        Path file = Paths.get(productFilePath) ;
        Faker faker = new Faker(new Locale("en-US"));
        int i=0;

        //Use try-with-resource to get auto-closeable writer instance
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            for (String temp[] : prodList) {
                i++;
                writer.write(String.format("{\"create\": {\"_id\":\"%d\"}}",i));
                writer.newLine();
                writer.write(String.format(
                    "{\"productId\":\"P%d\""
                    + ", \"productName\":\"%s\""
                    + ", \"productType\":\"%s\""
                    + ", \"unit\":\"%s\""
                    + ", \"listPrice\":%d"
                    + ", \"quantityOnHand\":%d"
                    + ", \"reorderLevel\":%d}"
                    ,i,temp[0],temp[1],temp[1]
                    ,faker.number().numberBetween(500, 3000)
                    ,faker.number().numberBetween(8, 30) 
                    ,faker.number().numberBetween(5, 20))
                );
                writer.newLine();
            }
        }
    }
    
    public static void usersData(String folderPath) throws IOException{
        
        String userFilePath = folderPath + File.separator + userFileName;
        Path file = Paths.get(userFilePath);

        Faker faker = new Faker();
        int i=0;
        String fieldJson = "{\"userId\":\"%s\""
            + ", \"password\":\"%s\""
            + ", \"userName\":\"%s\""
            + ", \"email\":\"%s\""
            + ", \"role\":\"%s\" "
            + ", \"isActive\":\"%s\""
            + ", \"address1\":\"%s\""
            + ", \"address2\":\"%s\""
            + ", \"postal\":\"%s\""
            + ", \"city\":\"%s\""
            + ", \"state\":\"%s\""
            + ", \"country\":\"%s\" }";


        //Use try-with-resource to get auto-closeable writer instance
        try (BufferedWriter writer = Files.newBufferedWriter(file,StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            
            //Create Admin User
            writer.write(String.format("{\"create\": {\"_id\":\"%s\"}}","admin"));
            writer.newLine();
            writer.write(String.format(fieldJson,"admin","admin", "Mrin", "mrin@example.com","ADMIN","true","","","","","",""));
            writer.newLine();
            
            //Create Support User
            writer.write(String.format("{\"create\": {\"_id\":\"%s\"}}","support"));
            writer.newLine();
            writer.write(String.format(fieldJson,"support","support", "Mickey Mouse", "mickey@example.com","SUPPORT_STAFF","true","","","","","",""));
            writer.newLine();

            // Create Customers
            for (i = 1; i <= 500; i++){
                writer.write(String.format("{\"create\": {\"_id\":\"%d\"}}",i));
                writer.newLine();
                writer.write(String.format(fieldJson
                    ,"user"+i
                    ,"password"
                    ,faker.name().name()
                    ,faker.internet().emailAddress()
                    ,"CUSTOMER"
                    ,faker.options().option("true", "false")
                    ,faker.address().streetName()
                    ,faker.address().streetAddressNumber()
                    ,faker.address().zipCode()
                    ,faker.address().city()
                    ,faker.address().stateAbbr()
                    ,faker.address().country())
                );
                writer.newLine();
            }
        }
        
    }
    public static void ordersData(String folderPath) throws IOException{
        Path file = Paths.get(folderPath);
        //Use try-with-resource to get auto-closeable writer instance
        try (BufferedWriter writer = Files.newBufferedWriter(file,StandardCharsets.UTF_8, StandardOpenOption.WRITE)){
            writer.write("Hello World !!");
        }
    }
    
}
