package com.tracv.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageLoader {
    public static BufferedImage readImage(String loc, String dir, String fileType){
        if(loc != null && dir != null){
            try{
                BufferedImage i = ImageIO.read(ImageLoader.class.getResource(
                        dir + loc + fileType));
                return i;
            } catch (Exception e) {
                System.out.println(dir + loc + fileType);
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Image scaleImage(BufferedImage i, int width, int height){
        try{
            Image ret = i.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return ret;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
