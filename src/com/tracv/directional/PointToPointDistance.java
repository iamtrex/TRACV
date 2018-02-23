package com.tracv.directional;

import com.tracv.model.GameComponent;
import com.tracv.model.Tower;
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
}
