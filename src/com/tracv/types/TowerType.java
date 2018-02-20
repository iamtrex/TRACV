package com.tracv.types;

import com.tracv.util.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public enum TowerType {

    BASE_TOWER("Base Tower", "TowerSprite", 35, 35, 100,
            1.5, 5.0, 100.0,
            ProjectileType.BASIC);


    private String name;
    private Image sprite;
    private int width;
    private int height;

    private double fireRate;
    private double dmg;
    private double range;

    private int cost;
    private ProjectileType projType;


    TowerType(String name, String spritePath, int width, int height, int cost,
              double fireRate, double dmg, double range,
              ProjectileType projType){
        this.name = name;
        this.width = width;
        this.height = height;
        this.fireRate = fireRate;
        this.dmg = dmg;
        this.range = range;
        this.projType = projType;
        this.cost = cost;

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

    public double getFireRate(){
        return fireRate;
    }

    public double getDmg(){
        return dmg;
    }

    public double getRange(){
        return range;
    }

    public int getCost() {
        return cost;
    }
}
