package com.tracv.types;

import com.tracv.util.Constants;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

public enum TowerType {
    //TODO Change to include Tower stats and probably remove the name stufff

    BASE_TOWER("Base Tower", "TowerSprite", 35, 35);


    private String name;
    private Image sprite;
    private int width;
    private int height;

    TowerType(String name, String spritePath, int width, int height){
        this.name = name;
        this.width = width;
        this.height = height;

        if(spritePath != null){
            try{

                BufferedImage i = ImageIO.read(getClass().getResource(
                        Constants.TOWER_SPRITE_DIR + spritePath + Constants.TOWER_SPRITE_FILETYPE));
                sprite = i.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            } catch (Exception e) {
                System.out.println("Tower of type " + name + " unable to read sprite of name " + spritePath);
                sprite = null;
            }
        }

    }

    public String getName(){
        return name;
    }

    public Image getSprite(){
        return sprite;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

}
