package com.tracv.model;

import java.awt.*;

/**
 * An abstract mutable class representing the game components
 * Rep Invariant: Has a valid x and y coordinate on the GameMap
 */
public abstract class GameComponent {
    protected double x;
    protected double y;

    /**
     * Constructor for GameComponent; assigns the x, y coordinates of the
     * GameComponent
     * @param x a double representing the x coordinate
     * @param y a double representing the y coordinate
     */
    public GameComponent(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Abstract method with specific implementation in subtypes
     * @param g Swing Graphics
     */
    public abstract void draw(Graphics g);

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
