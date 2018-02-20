package com.tracv.model;

import java.util.List;

import java.awt.*;

public class Enemy extends MoveableComponent {
    private int distanceToEnd;
    private int health;
    private int totalHealth;

    private int dmg;
    private int gold;


    private List<Terrain> path;


    public Enemy(int distanceToEnd, int health, int dmg, double speed, double x, double y, String iconPath, int gold) {
            super(speed, x, y, iconPath);
            this.distanceToEnd = distanceToEnd;
            this.health = health;
            this.totalHealth = health;
            this.dmg = dmg;
            this.gold = gold;

    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval((int)x, (int)y, 25, 25);

        int healthX = (int) x;
        int healthY = (int) y - 15;
        int width = (int)((double)health/(double)totalHealth * 25);

        // Draw Health bar
        g.setColor(Color.GREEN);
        g.fillRect(healthX, healthY, width, 5);
        g.setColor(Color.BLACK);
        g.drawRect(healthX, healthY, 25, 5);
    }

    public int getDistanceToEnd() {
        return distanceToEnd;
    }

    public int getHealth() {
        return health;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDistanceToEnd(int d){
        this.distanceToEnd = d;
    }

    public void setHealth(int h) {
        this.health = h;
    }

    public void setDmg(int a) {
        this.dmg = a;
    }

    public void setSpeed(double s) {
        this.speed = s;

    }

    public boolean takeDmg(int dmg) {
        this.health -= dmg;
        return health <= 0;
    }

    public void setPath(List<Terrain> path){
        this.path = path;
    }
    public List<Terrain> getPath(){
        return path;
    }

    public double getSize() {
        return 25;
    }

    public int getKillGold() {
        return gold;
    }
}
