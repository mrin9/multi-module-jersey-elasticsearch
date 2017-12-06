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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
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
    private static List<String[]> userList;
    private static int totalUsersToGenerate = 10;
    private static int maxOrdersPerUser = 5;
    
    
    public static void allData(String folderPath){
        try {
            if (StringUtils.isBlank(folderPath)){
                folderPath = System.getProperty("user.home") + File.separator +"work"+ File.separator +"files";
            }
            readProdMaster();
            usersData(folderPath );
            productsData(folderPath);
            ordersData(folderPath);
        } 
        catch (IOException ex) {
            Logger.getLogger(DataGen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void readProdMaster() throws IOException{
        String prodMasterUrl = FillData.class.getClassLoader().getResource("product_master.csv").getFile();
        String line = "";
        String splitChar = "\\s*,\\s*"; // comma with leading and trailing spaces
        List<String> tmpProd;
        String[] tmpProdArr;
        Random rand = new Random();
        prodList = new ArrayList<>();
        int i =0;
        try (BufferedReader br = new BufferedReader(new FileReader(prodMasterUrl))) {
            while ((line = br.readLine()) != null) {
                i++;
                line = line.trim();
                if (line.startsWith("#")){continue;}
                tmpProd = new ArrayList<String>( Arrays.asList(line.split(splitChar)) );
                tmpProd.add(0, "P"+i); // ProductId
                tmpProd.add( Integer.toString(1+rand.nextInt(80)*10) ); // Price
                tmpProdArr = new String[tmpProd.size()];
                tmpProdArr = tmpProd.toArray(tmpProdArr);
                prodList.add( tmpProdArr);
            }
        }
    }
    
    public static void productsData(String folderPath) throws IOException{

        String productFilePath = folderPath + File.separator + productFileName;
        Path file = Paths.get(productFilePath) ;
        Faker faker = new Faker(new Locale("en-US"));
        
        //Use try-with-resource to get auto-closeable writer instance
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            for (String[] tmpProd : prodList) {
                writer.write(String.format("{\"create\": {\"_id\":\"%s\"}}",tmpProd[3]));
                writer.newLine();
                writer.write(String.format("{\"productId\":\"%s\""
                    + ", \"productName\":\"%s\""
                    + ", \"productType\":\"%s\""
                    + ", \"unit\":\"%s\""
                    + ", \"listPrice\":%s"
                    + ", \"quantityOnHand\":%s"
                    + ", \"reorderLevel\":%s}"
                    ,tmpProd[0],tmpProd[1],tmpProd[2],tmpProd[3],tmpProd[4]
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
        String tmpUserId="", tmpUserName="", tmpUserEmail="";
        
        String schemaJson = "{"
            + "\"userId\":\"%s\""
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
            + ", \"country\":\"%s\" "
            + "}";

        
        //Use try-with-resource to get auto-closeable writer instance
        try (BufferedWriter writer = Files.newBufferedWriter(file,StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            
            //Create Admin User
            writer.write(String.format("{\"create\": {\"_id\":\"%s\"}}","admin"));
            writer.newLine();
            writer.write(String.format(schemaJson,"admin","admin", "Mrin", "mrin@example.com","ADMIN","true","","","","","",""));
            writer.newLine();
            
            //Create Support User
            writer.write(String.format("{\"create\": {\"_id\":\"%s\"}}","support"));
            writer.newLine();
            writer.write(String.format(schemaJson,"support","support", "Mickey Mouse", "mickey@example.com","SUPPORT_STAFF","true","","","","","",""));
            writer.newLine();
            
            userList = new ArrayList<>();
            // Create Users
            for (i = 1; i <= totalUsersToGenerate; i++){
                tmpUserId   = "user"+i; 
                tmpUserName = faker.name().name();
                tmpUserEmail= faker.internet().emailAddress();
                userList.add(new String[]{tmpUserId, tmpUserName, tmpUserEmail});
                
                writer.write(String.format("{\"create\": {\"_id\":\"%d\"}}",i));
                writer.newLine();
                writer.write(String.format(schemaJson
                    ,tmpUserId
                    ,"password"
                    ,tmpUserName
                    ,tmpUserEmail
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
        
        String productFilePath = folderPath + File.separator + orderFileName;
        Path file = Paths.get(productFilePath) ;
        Faker faker = new Faker(new Locale("en-US"));
        Random rand = new Random();
        /*
        
        {
          "orderId"    :{"type": "text" },
          "userId"     :{"type": "text" },
          "userName"   :{"type": "text" },
          "userEmail"  :{"type": "text" },
          "orderStatus":{"type": "date" },
          "paymentType":{"type": "date" },
          "orderDate"  :{"type": "date" },
          "shippedDate":{"type": "date" },
          "Address1"   :{"type": "text" },
          "address2"   :{"type": "text" },
          "postal"     :{"type": "text" },
          "city"       :{"type": "text" },
          "country"    :{"type": "text" },
          "orderLines" :{    
            "type"       : "nested",  
            "properties" : {
                "productId"  : { "type": "text"   },            
                "productName": { "type": "text"   }, 
                "productType": { "type": "text"   },
                "quantity"   : { "type": "integer"},            
                "price"      : { "type": "long"   }            
            }
          }  
        }
        
        
        */
        
        
        String orderSchemaJson = "{"
            + "\"orderId\":\"%s\""
            + ",\"userId\":\"%s\""
            + ",\"userName\":\"%s\""
            + ",\"userEmail\":\"%s\""
            + ",\"orderStatus\":\"%s\" "
            + ",\"paymentType\":\"%s\""
            + ",\"orderDate\":\"%s\""
            + ",\"shippedDate\":\"%s\""
            + ",\"address1\":\"%s\""
            + ",\"address2\":\"%s\""
            + ",\"postal\":\"%s\""
            + ",\"city\":\"%s\""
            + ",\"state\":\"%s\""
            + ",\"country\":\"%s\""
            + ",\"orderLines\":[ %s ]"
            + "}";

        String orderLinesSchemaJson = "{"
            + "\"productId\":\"%s\""
            + ",\"productName\":\"%s\""
            + ",\"productType\":\"%s\""
            + ",\"price\":%s "
            + ",\"quantity\":%s"
            + "}";
                
        int i=0;
        int oCount=0;
        
        int orderLineCount=0;
        int orderLimit=0;
        
        int orderId=0;
        
        int randomProductIndex=0, randomPrice=0, randomQuantity=0;
        String[] tmpProd;
        String orderLinesData="";
        String completeSingleOrderData="";
        
        //Use try-with-resource to get auto-closeable writer instance
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            for (String[] tmpUser : userList) {

                // For each customer generate few orders
                // before generating orders, generate few order-lines
                orderLimit = (1+ rand.nextInt(maxOrdersPerUser));
                for (oCount = 0; oCount < orderLimit; oCount++ ) {
                    orderId++;
                    //Order Lines
                    orderLineCount = rand.nextInt(6)+1;
                    orderLinesData="";
                    for (i=0; i<orderLineCount;i++){
                        randomProductIndex =  rand.nextInt(prodList.size());
                        randomPrice = 100 + (rand.nextInt(80)*10);
                        randomQuantity = 1 + rand.nextInt(2);
                        tmpProd = prodList.get(randomProductIndex);
                        orderLinesData = orderLinesData + ","+ String.format(orderLinesSchemaJson,tmpProd[0],tmpProd[1], tmpProd[2], tmpProd[4], (1+rand.nextInt(3)) );
                    }
                    orderLinesData = orderLinesData.replaceFirst(",","");

                    writer.write(String.format("{\"create\": {\"_id\":\"%s\"}}",orderId));
                    writer.newLine();
                    completeSingleOrderData =String.format(orderSchemaJson,
                        "O"+orderId
                        ,tmpUser[0]
                        ,tmpUser[1]
                        ,tmpUser[2]
                        ,faker.options().option("On Hold", "Shipped", "Complete", "New")
                        ,faker.options().option("Check", "Cash", "Card")
                        ,""
                        ,""
                        ,faker.address().streetName()
                        ,faker.address().streetAddressNumber()
                        ,faker.address().zipCode()
                        ,faker.address().city()
                        ,faker.address().stateAbbr()
                        ,faker.address().country()
                        ,orderLinesData    
                    );
                    writer.write(completeSingleOrderData);
                    writer.newLine();
                }
            }
        }
    }
    
}
