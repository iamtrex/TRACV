package com.tracv.swing;

import com.tracv.util.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HUDTowerButton extends JButton{

    public HUDTowerButton(String name){
        Icon i = getIcon();
        if(i != null){
            this.setIcon(getIcon(name));
        }else{
            System.out.println("No Icon designated for this tower");
        }
        setLook();
    }

    private void setLook(){
        this.setPreferredSize(Constants.HUD_TOWER_ICON_SIZE);
        this.setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

    }


    private ImageIcon getIcon(String s){
        try {
            BufferedImage i = ImageIO.read(getClass().getResourceAsStream(s));
            return new ImageIcon(i);
        }catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
