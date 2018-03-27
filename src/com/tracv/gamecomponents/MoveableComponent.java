package com.tracv.gamecomponents;

public abstract class MoveableComponent extends GameComponent {
    protected double speed;

    public MoveableComponent (double speed, double x, double y, String iconPath) {
        super(x, y, iconPath);
        this.speed = speed;
    }
    //public abstract void draw(Graphics g, Rectangle selectedRegion);

    public double getSpeed() {
        return speed;
    }

    public void addX(double n) {
         x = x + n;
    }

    public void addY(double n) {
        y = y + n;
    }
}
