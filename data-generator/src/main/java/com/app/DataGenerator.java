package com.app;

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

import lombok.extern.log4j.Log4j2;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

@Log4j2
public class DataGenerator {
    
    public static final String userFileName   ="users.dat";
    public static final String productFileName="products.dat";
    public static final String orderFileName  ="orders.dat";
    private static List<String[]> prodList;
    private static List<String[]> userList;
    private static int totalUsersToGenerate = 10;
    private static int maxOrdersPerUser = 5;

    public static void main(String[] args) throws Exception {
        String destinationFolderForGeneratedFiles="";
        if (args.length==1){
            destinationFolderForGeneratedFiles=args[0];
        }
        generateElasticData(destinationFolderForGeneratedFiles);
    }
    
    
    public static void generateElasticData(String folderPath) {
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
            log.error("ERROR:" + ex.getMessage());
        }
    }
    
    public static void readProdMaster() throws IOException{
        InputStream prodMasterStream = DataGenerator.class.getClassLoader().getResourceAsStream("/product_master.csv");
        
        String line = "";
        String splitChar = "\\s*,\\s*"; // comma with leading and trailing spaces
        List<String> tmpProd;
        String[] tmpProdArr;
        Random rand = new Random();
        prodList = new ArrayList<>();
        int i =0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(prodMasterStream))) {
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("#")){continue;}
                i++;
                tmpProd = new ArrayList<>( Arrays.asList(line.split(splitChar)) );
                tmpProd.add(0, "P"+i); // ProductId
                tmpProd.add( Integer.toString(10+rand.nextInt(80)*10) ); // Price
                tmpProdArr = new String[tmpProd.size()];
                tmpProdArr = tmpProd.toArray(tmpProdArr);
                prodList.add( tmpProdArr);
            }
        }
    }
    
    public static void productsData(String folderPath) throws IOException{

        Faker faker = new Faker(new Locale("en-US"));
        String prodInsertTemplate = getFileContent("/bulkinsert_template_product.txt"); 

        String generatedProductFilePath = folderPath + File.separator + productFileName;
        Path file = Paths.get(generatedProductFilePath) ;
        
        //Write the template into File
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            for (String[] tmpProd : prodList) {

                Map<String,String> prod = new HashMap<String, String>(){{
                    put("productId", tmpProd[0]);
                    put("productName", tmpProd[1]);
                    put("productType", tmpProd[2]);
                    put("unit", tmpProd[3]);
                    put("listPrice", tmpProd[4]);
                    put("quantityOnHand", Integer.toString(faker.number().numberBetween(8, 30)) );
                    put("reorderLevel",   Integer.toString(faker.number().numberBetween(5, 20)) );
                }};
                StrSubstitutor sub = new StrSubstitutor(prod);
                writer.write(sub.replace(prodInsertTemplate));
            }
        }
        log.info("Product File Created");
    }
    
    public static void usersData(String folderPath) throws IOException{
        
        Faker faker = new Faker();
        String templateString = getFileContent("/bulkinsert_template_user.txt"); 

        String userFilePath = folderPath + File.separator + userFileName;
        Path file = Paths.get(userFilePath);

        try (BufferedWriter writer = Files.newBufferedWriter(file,StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            //Create Admin User
            Map<String,String> admin = new HashMap<String, String>(){{
                put("userId", "admin");
                put("password", "admin");
                put("userName", "Mrin");
                put("email", "mrin@example.com");
                put("role", "ADMIN");
                put("isActive", "true");
                put("address1", "");
                put("address2", "");
                put("postal", "");
                put("city", "");
                put("state", "");
                put("country", "");
            }};
            StrSubstitutor sub = new StrSubstitutor(admin);
            writer.write(sub.replace(templateString));

            //Create Admin User
            Map<String,String> support = new HashMap<String, String>(){{
                put("userId", "support");
                put("password", "support");
                put("userName", "Mickey Mouse");
                put("email", "mickey@example.com");
                put("role", "SUPPORT_STAFF");
                put("isActive", "true");
                put("address1", "");
                put("address2", "");
                put("postal", "");
                put("city", "");
                put("state", "");
                put("country", "");
            }};
            sub = new StrSubstitutor(support);
            writer.write(sub.replace(templateString));
            
            userList = new ArrayList<>();
            // Create Users
            for (int i = 1; i <= totalUsersToGenerate; i++){
                String tmpUserId    = "user"+i; 
                String tmpUserName  = faker.name().name();
                String tmpUserEmail = faker.internet().emailAddress();

                userList.add(new String[]{tmpUserId, tmpUserName, tmpUserEmail});
                Map<String,String> user = new HashMap<String, String>(){{
                    put("userId", tmpUserId);
                    put("password", "password");
                    put("userName", tmpUserName);
                    put("email", tmpUserEmail);
                    put("role", "CUSTOMER");
                    put("isActive", faker.options().option("true", "false"));
                    put("address1", faker.address().streetName());
                    put("address2", faker.address().streetAddressNumber());
                    put("postal", faker.address().zipCode());
                    put("city", faker.address().city());
                    put("state", faker.address().stateAbbr());
                    put("country", faker.address().country());
                }};
                sub = new StrSubstitutor(user);
                writer.write(sub.replace(templateString));
            }
        }
        log.info("Users File Created");
        
    }
    public static void ordersData(String folderPath) throws IOException{
        
        String productFilePath = folderPath + File.separator + orderFileName;
        Path file = Paths.get(productFilePath) ;
        Faker faker = new Faker(new Locale("en-US"));
        Random rand = new Random();
        String orderTemplate = getFileContent("/bulkinsert_template_order.txt"); 
        String orderLineTemplate = getFileContent("/partial_template_orderline.txt"); 
                
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)){
            int totalOrderCount=0;
            for (final String[] tmpUser : userList) { 
                // For each customer generate few orders
                // before generating orders, generate few order-lines
                int orderLimit = (1+ rand.nextInt(maxOrdersPerUser));
                
                for (int oCount = 0; oCount < orderLimit; oCount++ ) { // Generate Orders
                    totalOrderCount++;
                    final String orderId = "O"+ totalOrderCount;
                    //Order Lines
                    int orderLineCount = rand.nextInt(6)+1;
                    String orderLines="";
                    for (int i=0; i<orderLineCount;i++){  // Generate Orderlines
                        int randomProductIndex =  rand.nextInt(prodList.size());
                        final String[] tmpProd = prodList.get(randomProductIndex);
                        
                        Map<String,String> orderLineParams = new HashMap<String, String>(){{
                            put("productId", tmpProd[0]);
                            put("productName", tmpProd[1]);
                            put("productType", tmpProd[2]);
                            put("price", tmpProd[4]);
                            put("quantity", Integer.toString(1+rand.nextInt(3)) );
                        }};
                        StrSubstitutor subOrderLines = new StrSubstitutor(orderLineParams);
                        orderLines = orderLines + "," + subOrderLines.replace(orderLineTemplate) ;
                    }
                    
                    final String orderLinesData = orderLines.replaceFirst(",","").replace("\n", "");
                    Map<String,String> orderParams = new HashMap<String, String>(){{
                        put("orderId", orderId);
                        put("userId", tmpUser[0]);
                        put("userName", tmpUser[1]);
                        put("userEmail", tmpUser[2]);
                        put("orderStatus", faker.options().option("On Hold", "Shipped", "Complete", "New"));
                        put("paymentType", faker.options().option("Check", "Cash", "Card"));
                        put("orderDate", "");
                        put("shippedDate", "");
                        put("address1", faker.address().streetName());
                        put("address2", faker.address().streetAddressNumber());
                        put("postal", faker.address().zipCode());
                        put("city", faker.address().city());
                        put("state", faker.address().stateAbbr());
                        put("country", faker.address().country());
                        put("orderLines", orderLinesData );
                    }};
                    StrSubstitutor subOrder = new StrSubstitutor(orderParams);
                    writer.write(subOrder.replace(orderTemplate));
                    
                }
            }
        }
        log.info("Orders File Created");
    }
    
    private static String getFileContent(String fileName) throws IOException{
        
        InputStream is = DataGenerator.class.getClassLoader().getResourceAsStream("/"+fileName);
        String content="", line="";
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) { continue; }
                content = content + line + "\n";
            }
        }
        return content;
    }
    
    
}
