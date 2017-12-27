package com.tracv.swing;

import com.tracv.util.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HUDTowerButton extends JButton{

    private String name;

    public HUDTowerButton(String name, String iconPath, int cost){
        Icon i = getIcon(iconPath);
        this.name = name;

        if(i != null){
            this.setIcon(i);
        }else{
            System.out.println("No Icon designated for this tower");
        }

        this.setText(String.valueOf(cost));

        setLook();
    }

    private void setLook(){
        this.setPreferredSize(Constants.HUD_TOWER_ICON_SIZE);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.setFocusPainted(false);
        this.setIconTextGap(2);
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setFont(Constants.HUD_TOWER_GOLD_FONT);

    }

    @Override
    protected void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        super.paintComponent(g);

    }


    public String getTowerName() {
        return name;
    }

    private ImageIcon getIcon(String s){
        try {
            BufferedImage i = ImageIO.read(getClass().getResourceAsStream(Constants.TOWER_ICON_DIR + s + Constants.TOWER_ICON_FILETYPE));
            int a = (int) Constants.HUD_TOWER_ICON_SIZE.getWidth();
            Image scaled = i.getScaledInstance(a, a, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
