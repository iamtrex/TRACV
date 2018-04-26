package com.tracv.types;

public enum EnemyType {
    BASIC("B", 100, 50, 50, 15, "null"),
    FAST("F", 200, 25, 20, 10, "null");

    //TODO IconPath??



    private String shorthand;
    private Double speed;
    private int health, dmg, gold;
    private String iconPath;

    EnemyType(String shorthand, double speed, int health, int dmg, int gold, String iconPath){
        this.shorthand = shorthand;
        this.speed = speed;
        this.health = health;
        this.dmg = dmg;
        this.gold = gold;
        this.iconPath = iconPath;

    }

    public String getShorthand() {
        return shorthand;
    }

    public String getIconPath() {
        return iconPath;
    }

    public double getSpeed() {
        return speed;
    }

    public int getHealth(){
        return health;
    }

    public int getDmg(){
        return dmg;
    }

    public int getGold(){
        return gold;
    }

}
