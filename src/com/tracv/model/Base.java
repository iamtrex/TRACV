package com.tracv.model;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Represents the Base on the Game Map
 * The base should exist on the GameMap and its health value should be greater than 0
 */
public class Base extends GameComponent{
    private int health;
    private int size;

    /**
     * Constructor for the Base
     * @param health the health value of the base
     * @param size the size of the base, width = height = size
     * @param x the x coordinate of the base
     * @param y the y coordinate of the base
     * @param iconPath the directory of the base's icon
     */
    public Base(int health, int size, double x, double y, String iconPath) {
        super(x, y, iconPath);
        this.health = health;
        this.size = size;
    }

    /**
     *CURRENTLY DRAWS A CENTERED BLUE CIRCLE WITH SIZE EQUALS TO size
     * @param g Swing Graphics
     */
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        Graphics2D g2d = (Graphics2D)g;
        double centerX = x - (size/2);
        double centerY = y - (size/2);
        Ellipse2D.Double circle = new Ellipse2D.Double(centerX, centerY, size, size);
        g2d.fill(circle);
    }

    public int getHealth() {
        return health;
    }
}
