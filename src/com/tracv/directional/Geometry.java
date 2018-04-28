package com.tracv.directional;

import com.tracv.gamecomponents.GameComponent;
import com.tracv.gamecomponents.Terrain;
import com.tracv.gamecomponents.Tower;
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
     * Returns whether two objects are within a range of each other.
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

    /**
     *
     * @param a
     * @param b
     * @return - if the two game components are overlapping.
     */
    public static boolean hasIntersection(GameComponent a, GameComponent b){
        Point pa = a.getPoint();
        Point pb = b.getPoint();
        //System.out.println(pa + " " + pb + " " + a.getWidth() + " " + a.getHeight() + " " + b.getWidth() + " " + b.getHeight());
        if(pa.x == pb.x && pa.y == pb.y){
            return true; //Overlapping.
        }

        boolean withinHeight = false, withinWidth = false;

        if(pb.x > pa.x){
            // b is to the right of a.
            if(pb.x - pa.x < a.getWidth()){
                withinWidth = true; //X units overlap.
            }
        }else{
            if(pa.x - pb.x < b.getWidth()){
                withinWidth = true;
            }
        }

        if(pb.y > pa.y){
            //b is below a
            if(pb.y - pa.y < a.getHeight()){
                withinHeight = true;
            }
        }else{
            if(pa.y - pb.y < b.getHeight()){
                withinHeight = true;
            }
        }
        //System.out.println(withinHeight + " " +  withinWidth);
        return withinWidth && withinHeight; //Must be in height and with to be intersecting.
    }
}
