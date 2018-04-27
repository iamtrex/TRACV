package com.tracv.gamecomponents;

import com.tracv.types.ProjectileType;

import java.awt.*;

public class Projectile extends MoveableComponent {
    private Enemy target;
    private Tower tower;

    private int dmg;
    private double speed;

    //TODO - Delete
    public Projectile (Enemy target, int dmg, double speed, double x, double y, String iconPath) {
        super (speed, x, y, iconPath);
        this.target = target;
        this.dmg = dmg;

        width = 10;
        height = 10;

    }

    public Projectile(Enemy e, Tower t, ProjectileType projectileType) {
        super(projectileType.getSpeed(), t.getX(), t.getY(), null);
        this.tower = t;
        this.target = e;
        this.dmg = (int)Math.round(t.getAtkDmg());
        width = 10;
        height = 10;
    }

    public void draw(Graphics g, Rectangle selectedRegion) {
        g.setColor(Color.ORANGE);

        int screenX = (int) Math.round(x - selectedRegion.getX());
        int screenY = (int) Math.round(y - selectedRegion.getY());
        g.fillOval(screenX, screenY, (int) width, (int) height);
    }

    public Tower getTower() {return tower;}
    public Enemy getTarget() {return target;}

    public int getDmg() {return dmg;}

    public void setTarget(Enemy t) {this.target = t;}

    public void setDmg(int d) {this.dmg = d;}

}
