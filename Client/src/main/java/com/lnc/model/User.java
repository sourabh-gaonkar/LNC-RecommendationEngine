package com.lnc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class User {
    @JsonProperty("employeeID")
    private String userId;
    private String name;
    private String role;
    private String email;
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password =  password;
    }
}
