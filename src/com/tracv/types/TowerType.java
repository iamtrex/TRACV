package com.tracv.types;

public enum TowerType {
    //TODO Change to include Tower stats and probably remove the name stufff

    BASE_TOWER("Base Tower");


    private String name;
    TowerType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
