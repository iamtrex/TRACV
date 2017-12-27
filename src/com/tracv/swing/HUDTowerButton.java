package com.tracv.swing;

import com.tracv.types.TowerType;
import com.tracv.util.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HUDTowerButton extends JButton{

    private TowerType type;

    private Border lineBorder, emptyBorder;

    public HUDTowerButton(TowerType type, String iconPath, int cost){
        lineBorder = new LineBorder(Color.BLUE, 1);
        emptyBorder = new EmptyBorder(1, 1, 1, 1);

        Icon i = getIcon(iconPath);
        this.type = type;

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
        //this.setBorderPainted(false);
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
    public void setSelected(boolean b){
        super.setSelected(b);
        if(b){
            this.setBorder(lineBorder);
        }else{
            this.setBorder(emptyBorder);
        }
    }
    @Override
    protected void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());


        super.paintComponent(g);

    }


    public TowerType getType(){
        return type;
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
