package com.tracv.gamecomponents;

import com.tracv.types.TowerType;

import java.awt.*;
import java.util.List;

/**
 * Represents a Tower on the GameMap
 * No fields should be null, atkDmg, fireRate, cost, size and range should be greater than 0
 */
public class Tower extends GameComponent {
    private TowerType type;



    private double timeTilNextFire;
    private double fireRate;

    private boolean selected;


    /**
     *
     * @param x - top left x coord of tower
     * @param y - top left y coord of tower
     * @param type - Type of tower, contains properties such as range, dmg, fire rate, width/height, and image.
     *
     */
    public Tower(double x, double y, TowerType type){
        super(x, y, null);
        modifyType(type); //Setup the type
        selected = false;
        timeTilNextFire = type.getFireRate();
    }

    //Also how upgrading type works.
    public void modifyType(TowerType upgradeType) {
        this.type = upgradeType;
        width = type.getWidth();
        height = type.getHeight();
        fireRate = type.getFireRate();
    }

    /**
     * @param g Swing Graphics
     */
    public void draw(Graphics g) {
        int xPos = (int) (x);
        int yPos = (int) (y);
        g.drawImage(type.getSprite(), xPos, yPos, null);

        int range = (int) type.getRange();

        if(selected){
            g.setColor(Color.BLACK);
            g.drawRect(xPos, yPos, (int)width, (int)height);
            g.drawRect(xPos+1, yPos+1, (int)(width-1), (int) (height-1));

            //Draw range of selectd tower only
            g.setColor(Color.CYAN);
            g.drawOval((int)(x-range+width/2), (int)(y-range+width/2), 2*range, 2*range);

        }


    }

    public double getAtkDmg() {
        return type.getDmg();
    }

    public double getRange() {
        return type.getRange();
    }


    public boolean canFire(){
        return timeTilNextFire <= 0;

    }

    public void decrementCooldown(double v) {
        if(timeTilNextFire > 0) {
            this.timeTilNextFire -= v;
        }
    }

    public void setFired() {
        timeTilNextFire = 1000.0/fireRate;
    }

    public void setSelected(boolean b){
        selected = b;
        
    }

    public boolean isSelected() {
        return selected;
    }

    public List<TowerType> getUpgrades() {
        return type.getUpgrades();
    }

    public String getTypeName() {
        return type.getName();
    }
    public TowerType getType(){
        return type;
    }

    public double getSellPrice() {
        return type.getSellPrice();
    }

}
