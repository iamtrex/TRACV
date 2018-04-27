package com.tracv.types;

import java.awt.*;

public enum TerrainType {
    MOVEABLE("M"),
    BUILDABLE("B"),
    VOID("V"),
    NEXUS("N"),
    START("S"); //Start same as Moveable, but indicates where it can spawn.

    private String type;
    private Color color;

    public String getType(){
        return this.type;
    }

    public Color getColor(){
        return this.color;
    }

    TerrainType(String s){
        this.type = s;
        if(s.equals("M") || s.equals("S")){
            this.color = Color.GRAY;
        }else if(s.equals("B")){
            this.color = Color.GREEN;
        }else if(s.equals("V")){
            this.color = Color.WHITE;
        }else if(s.equals("N")){
            this.color = Color.BLUE;
        }
    }

    public static TerrainType[] getTerrains(){
        TerrainType[] terrainTypes = {MOVEABLE, BUILDABLE, VOID, NEXUS, START};
        return terrainTypes;
    }

}
