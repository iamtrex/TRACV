package com.tracv.gamecomponents;

import com.tracv.util.Constants;

import java.awt.*;

/**
 * Represents the Base on the Game Map
 * The base should exist on the GameMap and its health value should be greater than 0
 */
public class Base extends GameComponent{
    private int health;
    private int maxHealth;

    //private int width, height;

    /**
     * Constructor for the Base
     * @param health the health value of the base
     *
     * @param iconPath the directory of the base's icon
     */
    public Base(int health, Terrain t, String iconPath) {
        super(t.getPixelX(), t.getPixelY(), iconPath);

        this.health = health;
        this.maxHealth = health;

        this.width = t.getWidth();
        this.height = t.getHeight();

    }

    /**
     *CURRENTLY DRAWS A CENTERED BLUE CIRCLE WITH SIZE EQUALS TO size
     * @param g Swing Graphics
     */
    public void draw(Graphics g, Rectangle selectedRegion) {

        /*
        Graphics2D g2d = (Graphics2D)g;
        double centerX = x - (size/2);
        double centerY = y - (size/2);
        Ellipse2D.Double circle = new Ellipse2D.Double(centerX, centerY, size, size);
        g2d.fill(circle);
        */
        int screenX = (int) Math.round(x - selectedRegion.getX());
        int screenY = (int) Math.round(y - selectedRegion.getY());

        g.setColor(Color.BLUE);
        g.fillRect(screenX, screenY, (int)width, (int)height);
        g.setColor(Color.GREEN);

        double maxWidth = (width * Constants.HEALTH_BAR_WIDTH_REDUCTION_FACTOR);

        int healthX = (int)(screenX + 0.5*(width-maxWidth));
        int healthY = (screenY - Constants.HEALTH_BAR_SPACING_BASE);

        int hWidth = (int)Math.round((maxWidth * ((double)health/(double)maxHealth)));



        g.fillRect(healthX, healthY, hWidth, Constants.HEALTH_BAR_HEIGHT_BASE);

        g.setColor(Color.BLACK);
        g.drawRect(healthX, healthY, (int)maxWidth, Constants.HEALTH_BAR_HEIGHT_BASE);

    }


    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }


    /**
     * Returns true if nexus explodes (out of hp);
     * @param dmg - dmg to take.
     * @return
     */
    public boolean takeDmg(int dmg){
        health -= dmg;
        return health <= 0;
    }
    public String getHealth() {
        return health + "/" + maxHealth;
    }
}