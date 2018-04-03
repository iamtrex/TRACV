package com.tracv.directional;

import com.tracv.gamecomponents.GameComponent;
import com.tracv.gamecomponents.Terrain;
import com.tracv.util.Constants;

import java.awt.*;

/**
 * Some geometric maths.
 *
 */
public class Geometry {

    /**
     *
     * @param a
     * @param b
     * @return - if points a and b are within Constants.CLICK_VAR_DISTANCE of each other
     */
    public static boolean withinDistance(Point a, Point b){
        return getDistance(a, b) < Constants.CLICK_VAR_DISTANCE;
    }

    /**
     * Get distance between two points
     * @param a
     * @param b
     * @return double representation of double
     */
    public static double getDistance(Point a, Point b){
        return Math.sqrt(Math.pow(a.getX()-b.getX(), 2) + Math.pow(a.getY()-b.getY(), 2));
    }

    /**
     *
     * @param point
     * @param gc
     * @return
     */
    public static boolean isPointInObject(Point point, GameComponent gc) {
        return point.getX() >= gc.getX() && point.getX() <= gc.getX() + gc.getWidth() &&
            point.getY() >= gc.getY() && point.getY() <= gc.getY() + gc.getHeight();
    }

    /**
     * REturns whether two objects are within a range of each other.
     * @param g1
     * @param g2
     * @param range
     * @return
     */
    public static boolean withinRange(GameComponent g1, GameComponent g2, double range){
        return range >= Math.sqrt(Math.pow(g1.getX()-g2.getX(), 2) + Math.pow(g1.getY()-g2.getY(), 2));
    }

    public static boolean isObjectInsideRegion(Terrain terrain, Rectangle selectedRegion) {
        return (terrain.getPixelX() + terrain.getWidth() > selectedRegion.getX() &&
                terrain.getPixelX() < selectedRegion.getX() + selectedRegion.getWidth() &&

                terrain.getPixelY() + terrain.getHeight() > selectedRegion.getY() &&
                terrain.getPixelY() < selectedRegion.getY() + selectedRegion.getHeight());
    }
    public static boolean isObjectInsideRegion(GameComponent gc, Rectangle selectedRegion) {
        return (gc.getX() + gc.getWidth() > selectedRegion.getX() &&
                gc.getX() < selectedRegion.getX() + selectedRegion.getWidth() &&
                gc.getY() + gc.getHeight() > selectedRegion.getY() &&
                gc.getY() < selectedRegion.getY() + selectedRegion.getHeight());
    }

    public static boolean isPointInRegion(Point p, Rectangle r) {
        return (p.getX() >= r.getX() &&
                p.getX() <= r.getX() + r.getWidth() &&
                p.getY() >= r.getY() &&
                p.getY() <= r.getY() + r.getHeight());
    }


    public static boolean isPointInTop(Point p, Rectangle r, int pad) {
        return p.getY() <= r.getY() + pad && p.getY() >= r.getY();
    }

    public static boolean isPointInBot(Point p, Rectangle r, int pad) {
        return p.getY() >= r.getY() + r.getHeight() - pad && p.getY() < r.getY() + r.getHeight();
    }

    public static boolean isPointInLeft(Point p, Rectangle r, int pad) {
        return p.getX() <= r.getX() + pad && p.getX() >= r.getX();
    }

    public static boolean isPointInRight(Point p, Rectangle r, int pad) {
        return p.getX() >= r.getX() + r.getWidth() - pad && p.getX() <= r.getX() + r.getWidth();
    }
}
