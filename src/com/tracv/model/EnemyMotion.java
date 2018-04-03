package com.tracv.model;

import com.tracv.directional.Vector;
import com.tracv.gamecomponents.Enemy;
import com.tracv.gamecomponents.Terrain;

import java.util.List;

/**
 * Moves enemy based off it's path algorithm.
 */
public class EnemyMotion {


    /**
     * Updates position of enemy. Returns true if crashed into base.
     * @param e - Enemy to modify positon of.
     * @param refreshTime - Real time in milliseconds that it took to update frame.
     * @return - true if crashed into base, false otherwise.
     */
    public static boolean updateEnemy(Enemy e, long refreshTime){
        
        if(!e.getPath().isEmpty()){
            List<Terrain> path = e.getPath();

            Terrain curr = path.get(0);
            int curX = curr.getX();
            int curY = curr.getY();

            //If object already in the block, remove it from the path, so this allows block to always move forward
            if(e.getX() >= curX * curr.getWidth() &&
                    e.getX() <= (curX+1) * curr.getWidth() &&
                    e.getY() >= curY * curr.getHeight() &&
                    e.getY() <= (curY+1) * curr.getHeight()) {
                //System.out.println("Removing");
                path.remove(curr);
            }

            if(path.isEmpty()){
                //Must've reached base, crashing.
                return true; //Crashed

            }else{
                //Using vector, move.
                Terrain target = path.get(0);
                Vector v = new Vector((target.getCenX() - (e.getX() + e.getSize()/2)),
                        (target.getCenY() - (e.getY() + e.getSize()/2)));

                double speed = e.getSpeed()/(double)refreshTime; 
                e.addX(speed * v.getXRatio());
                e.addY(speed * v.getYRatio());
            }
        }
        return false;
    }

}
