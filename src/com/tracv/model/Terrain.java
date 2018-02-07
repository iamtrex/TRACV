package com.tracv.model;

import java.awt.*;

public enum Terrain {
    MOVEABLE("M"),
    BUILDABLE("B"),
    VOID("V");

    private String type;
    private Color color;

    public String getType(){
        return this.type;
    }

    public Color getColor(){
        return this.color;
    }

    Terrain(String s){
        this.type = s;
        if(s.equals("M")){
            this.color = Color.GRAY;
        }else if(s.equals("B")){
            this.color = Color.GREEN;
        }else if(s.equals("V")){
            this.color = Color.WHITE;
        }
    }

    public static Terrain[] getTerrains(){
        Terrain[] terrains = {MOVEABLE, BUILDABLE, VOID};
        return terrains;
    }

}
