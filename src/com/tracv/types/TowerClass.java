package com.tracv.types;

public enum TowerClass {
    MAGE("Mage"), ARTILLARY("Artillary"), SNIPER("Sniper"), BASIC("Basic"), RAPID_FIRE("Archer");

    private String name;
    TowerClass(String s){
        name = s;
    }

    public String getName(){
        return this.name;
    }
}
