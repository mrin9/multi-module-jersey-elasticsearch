package com.app.model.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.security.Principal;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class User implements Serializable, Principal {
    private String userId;
    private String userName;
    private String email;
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) private Role role;   // dont create getter or setter
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE) private boolean isActive; // dont create getter or setter 
    private String password = "";
    private String address1;
    private String address2;
    private String postal;
    private String city;
    private String state;
    private String country;
    
    public User(){
        this("new", "new", "", "CUSTOMER" , true, "","","","","","","");
    }

    public User(String userId, String userName,String email,  String role ){
        this(userId, userName, email, role, true,"","","","","","","");
    }

    public User(String userId  , String userName, String email, String role, boolean isActive, String password,
                String address1, String address2, String postal, String city, String state   , String country ){
        this.userId=userId;
        this.userName= userName;
        this.email=email;
        this.setRole(role);
        this.isActive = isActive;       
        this.password=password;
        this.address1 = address1;
        this.address2 = address2;
        this.postal = postal;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public String getRole(){return this.role.toString().toUpperCase();}
    public void setRole(String role){
        try{this.role = Role.valueOf(role.toUpperCase());}
        catch (IllegalArgumentException e) {this.role = Role.CUSTOMER;}
    }
    
    // lombok has different rule for naming getters/setters properties starting with 'is' and this creates a problem wile deserializing from JSON, so create them manually
    public boolean getIsActive(){return this.isActive;}
    public void setIsActive(boolean isActive){this.isActive = isActive;}
    
    @Override
    @JsonIgnore // This getter is duplicate of getId but is must for all classes that implements java.security.Principal
    public String getName() {return this.userName;}
    
}
