package com.tracv.gamecomponents;

import java.awt.*;

/**
 * An abstract mutable class representing the game components
 * Rep Invariant: Has a valid x and y coordinate on the GameMap
 */
public abstract class GameComponent {
    protected double x;
    protected double y;
    protected double width;
    protected double height;

    protected String iconPath;
    /**
     * Constructor for GameComponent; assigns the x, y coordinates of the
     * GameComponent
     * @param x a double representing the x coordinate
     * @param y a double represent
     *          ng the y coordinate
     * @param iconPath a String representing the directory and name of the icon file
     */
    public GameComponent(double x, double y, String iconPath) {
        this.x = x;
        this.y = y;
        this.iconPath = iconPath;
    }

    /**
     * Abstract method with specific implementation in subtypes
     * @param g Swing Graphics
     */
    public abstract void draw(Graphics g);

    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }



    /**
     * Getter method for the x coordinate
     * @return the x coordinate as a double
     */
    public double getX() {
        return x;
    }

    /**
     * Getter method for the y coordinate
     * @return the y coordinate as a double
     */
    public double getY() {
        return y;
    }

    /**
     * Setter method for the x coordinate
     * @param x modifies the x coordinate to this input value
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Setter method for the y coordinate
     * @param y modifies the y coordinate to this input v alue
     */
    public void setY(double y) {
        this.y = y;
    }


}
