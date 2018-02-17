package com.tracv.model;


import com.tracv.types.TerrainType;

public class Terrain {

    private int x, y, width, height;
    private TerrainType type;

    public Terrain(TerrainType type, int x, int y, int width, int height){
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public TerrainType getType(){
        return type;
    }

    public int getCenX() {
        return (int)(x*width + 0.5*width);
    }

    public int getCenY() {
        return (int) (y*height + 0.5*height);
    }
}


