package com.tracv.directional;

import com.tracv.gamecomponents.GameComponent;
import com.tracv.gamecomponents.Terrain;
import com.tracv.util.Constants;

import java.awt.*;

public class PointToPointDistance {

    public static boolean withinDistance(Point a, Point b){
        return getDistance(a, b) < Constants.CLICK_VAR_DISTANCE;
    }

    public static double getDistance(Point a, Point b){
        return Math.sqrt(Math.pow(a.getX()-b.getX(), 2) + Math.pow(a.getY()-b.getY(), 2));
    }

    public static boolean isPointInObject(Point point, GameComponent gc) {
        return point.getX() >= gc.getX() && point.getX() <= gc.getX() + gc.getWidth() &&
            point.getY() >= gc.getY() && point.getY() <= gc.getY() + gc.getHeight();
    }

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
}
