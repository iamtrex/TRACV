package com.tracv.swing;

import com.tracv.util.Constants;
import com.tracv.util.ImageLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class IconButton extends JButton {

    //TODO implement class.
    public IconButton(String s){
        super();

        this.setContentAreaFilled(false);
        this.setPreferredSize(Constants.ICON_SIZE);
        //this.setBorderPainted(false);
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
        //this.setBorderPainted(false);

        this.setIconTextGap(0);

        BufferedImage i = ImageLoader.readImage(s, Constants.ICON_DIR, Constants.ICON_FILE_TYPE);
        this.setIcon(new ImageIcon(ImageLoader.scaleImage(i,
                (int)Constants.ICON_SIZE.getWidth(),
                (int)Constants.ICON_SIZE.getHeight())));

    }
}
