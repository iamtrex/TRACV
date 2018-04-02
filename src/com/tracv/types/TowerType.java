package com.tracv.types;

import com.tracv.util.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TowerType {

    FAR_SHOT_TOWER_2("Far Shot 2 Tower", "FarSprite", "TestIcon2", TowerClass.SNIPER,
            35, 35, 200, 1.5, 12.0, 500.0,
            ProjectileType.BASIC, new TowerType[]{}),

    MEGA_HIT_TOWER("Mega Hit Tower", "MegaSprite","TestIcon2", TowerClass.ARTILLARY,
            35, 35, 200, 1.5, 40.0, 150,
            ProjectileType.BASIC, new TowerType[]{}),

    FAR_SHOT_TOWER("Far Shot Tower", "FarSprite", "TestIcon2", TowerClass.SNIPER,
            35, 35, 200, 1.5, 10.0, 300.0,
            ProjectileType.BASIC, new TowerType[]{FAR_SHOT_TOWER_2}),

    RAPID_TOWER("Rapid Tower", "RapidSprite", "TestIcon2", TowerClass.RAPID_FIRE,
            35, 35, 100, 2.5, 10.0, 100.0,
            ProjectileType.BASIC, new TowerType[]{}),

    BASE_TOWER("Base Tower", "BaseSprite", "TestIcon1", TowerClass.BASIC,
            35, 35, 100, 1.5, 10.0, 100.0,
            ProjectileType.BASIC, new TowerType[]{RAPID_TOWER, FAR_SHOT_TOWER, MEGA_HIT_TOWER}),

    MAGE_TOWER_2("Mage Tower 2", "Mage2Sprite", "TestIcon2", TowerClass.MAGE,
            35, 35, 100, 0.5, 20.0, 250.0,
               ProjectileType.MAGIC, new TowerType[]{}),

    MAGE_TOWER("Mage Tower", "MageSprite", "TestIcon2", TowerClass.MAGE,
            35, 35, 100, 0.5, 10.0, 250.0,
            ProjectileType.MAGIC, new TowerType[]{MAGE_TOWER_2});

    public static final TowerType[] BASIC_TOWERS =  {BASE_TOWER, MAGE_TOWER};

    private String name;
    private String iconPath;
    private BufferedImage sprite;
    private Image spriteActive, spriteDeactive;

    private int width;
    private int height;

    private double fireRate;
    private double dmg;
    private double range;

    private int cost;
    private ProjectileType projType;

    private List<TowerType> upgrades;


    private TowerClass classType;

    TowerType(String name, String spritePath, String iconPath, TowerClass classType,
              int width, int height, int cost,
              double fireRate, double dmg, double range,
              ProjectileType projType, TowerType[] ups){
        this.name = name;
        this.width = width;
        this.height = height;
        this.fireRate = fireRate;
        this.dmg = dmg;
        this.range = range;
        this.projType = projType;
        this.cost = cost;
        this.iconPath = iconPath;

        this.classType = classType;

        this.upgrades = new ArrayList<>();
        upgrades.addAll(Arrays.asList(ups));

        if(spritePath != null){
            try{
                BufferedImage img = ImageIO.read(getClass().getResource(
                        Constants.TOWER_SPRITE_DIR + spritePath + Constants.TOWER_SPRITE_FILETYPE));
                int w = img.getWidth();
                int h = img.getHeight();

                sprite = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                AffineTransform at = new AffineTransform();
                at.scale(((double)width/w), ((double)height/h));

                AffineTransformOp scaleOp =
                        new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
                sprite = scaleOp.filter(img, sprite);

                //Add border...
                ColorModel cm = sprite.getColorModel();
                boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
                WritableRaster raster = sprite.copyData(null);
                BufferedImage tmpActive = new BufferedImage(cm, raster, isAlphaPremultiplied, null);

                Graphics g = tmpActive.getGraphics();
                g.setColor(Color.RED);
                g.drawRect(0, 0, tmpActive.getWidth()-1, tmpActive.getHeight()-1);
                g.dispose();
                spriteActive = tmpActive;

                //Grayscale deactive.
                ImageFilter filter = new GrayFilter(true, 50);
                ImageProducer producer = new FilteredImageSource(sprite.getSource(), filter);
                spriteDeactive = Toolkit.getDefaultToolkit().createImage(producer);



            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Tower of type " + name + " unable to read sprite of name " + spritePath);
                sprite = null;
            }
        }

    }

    public String getName(){
        return name;
    }

    public Image getSprite(){
        return sprite;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public double getFireRate(){
        return fireRate;
    }

    public double getDmg(){
        return dmg;
    }

    public double getRange(){
        return range;
    }

    public int getCost() {
        return cost;
    }

    public List<TowerType> getUpgrades() {
        return upgrades;
    }

    public double getSellPrice() {
        return Math.round(cost * 0.6 * 100) / 100.0;
    }

    public int getUpgradeCost() {
        return cost; //TODO not sure how we want to do costs vs upgrade costs... so for now "cost is upgrade cost" for uppertier towers...
    }

    public String getIcon() {
        return iconPath;
    }

    public String getType() {
        return classType.getName();
    }

    public Image getSpriteActive(){
        return spriteActive;
    }
    public Image getSpriteDeactive() {
        return spriteDeactive;
    }
}
