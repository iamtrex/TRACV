package com.tracv.model;

/**
 * Represents a mathematical 2D vector.
 */

public class Vector {
    private double i;
    private double j;

    public Vector(){
        i = 0;
        j = 0;
    }

    public void setI(int i){
        this.i = i;
    }

    public void setJ(int j){
        this.j = j;
    }

    public double getI(){
        return this.i;
    }

    public double getJ(){
        return this.j;
    }


}
