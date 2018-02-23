package com.tracv.types;

import com.tracv.util.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TowerType {

    FAR_SHOT_TOWER_2("Far Shot 2 Tower", "FarSprite", 35, 35, 200,
            1.5, 12.0, 500.0,
            ProjectileType.BASIC, new TowerType[]{}),

    MEGA_HIT_TOWER("Mega Hit Tower", "MegaSprite", 35, 35, 200,
            1.5, 40.0, 150,
            ProjectileType.BASIC, new TowerType[]{}),

    FAR_SHOT_TOWER("Far Shot Tower", "FarSprite", 35, 35, 200,
            1.5, 10.0, 300.0,
            ProjectileType.BASIC, new TowerType[]{FAR_SHOT_TOWER_2}),

    RAPID_TOWER("Rapid Tower", "RapidSprite", 35, 35, 100,
            2.5, 10.0, 100.0,
            ProjectileType.BASIC, new TowerType[]{}),

    BASE_TOWER("Base Tower", "BaseSprite", 35, 35, 100,
            1.5, 10.0, 100.0,
            ProjectileType.BASIC, new TowerType[]{RAPID_TOWER, FAR_SHOT_TOWER, MEGA_HIT_TOWER});


    private String name;
    private Image sprite;
    private int width;
    private int height;

    private double fireRate;
    private double dmg;
    private double range;

    private int cost;
    private ProjectileType projType;

    private List<TowerType> upgrades;



    TowerType(String name, String spritePath, int width, int height, int cost,
              double fireRate, double dmg, double range,
              ProjectileType projType, TowerType[] ups){
        this.name = name;
        this.width = width;
        this.height = height;
        this.fireRate = fireRate;
        this.dmg = dmg;
        this.range = range;
        this.projType = projType;
        this.cost = cost;

        this.upgrades = new ArrayList<>();
        upgrades.addAll(Arrays.asList(ups));

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

    public List<TowerType> getUpgrades() {
        return upgrades;
    }

    public double getSellPrice() {
        return Math.round(cost * 0.6 * 100) / 100.0;
    }

    public int getUpgradeCost() {
        return cost; //TODO not sure how we want to do costs vs upgrade costs... so for now "cost is upgrade cost" for uppertier towers...
    }
}
