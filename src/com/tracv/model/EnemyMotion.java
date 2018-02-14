package com.tracv.model;

public class EnemyMotion {

    //TODO THIS WOULD BE VERY DIFFICULT AND LONG, also needs terrain
    public static boolean updateEnemy(Enemy e, Base b){
        e.addX(1); //Just update move to the right by 1 unit per 1000/60 ms.
        return false;
    }
}
