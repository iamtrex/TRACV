package com.tracv.model;

public enum Terrain {
    MOVEABLE("M"),
    BUILDABLE("B"),
    VOID("V");

    private String type;
    public String getType(){
        return this.type;
    }

    Terrain(String s){
        this.type = s;
    }

    public static Terrain[] getTerrains(){
        Terrain[] terrains = {MOVEABLE, BUILDABLE, VOID};
        return terrains;
    }

}
