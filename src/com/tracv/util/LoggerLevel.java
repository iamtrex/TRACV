package com.tracv.util;

public enum LoggerLevel{
    WARNING("Warning"), ERROR("Error"), STATUS("Status");
    private String name;
    public String getName(){
        return name;
    }
    LoggerLevel(String s){
        name = s;
    }
}