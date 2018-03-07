package com.tracv.swing;

import com.tracv.types.TowerType;
import com.tracv.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class TowerUpgradeButton extends Button implements MouseListener, MouseMotionListener {

    private TowerType upgradeType;



    public TowerUpgradeButton(TowerType tt){
        super();

        this.setIcon(new ImageIcon(tt.getSprite()));
        this.setText(tt.getCost() + " g");

        this.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.setHorizontalTextPosition(SwingConstants.CENTER);

        this.setIconTextGap(3);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setFont(Constants.SMALL_LABEL_FONT);

        upgradeType = tt;

        tool = new TowerToolTip(tt);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addActionListener((e)->{
            showHideToolTip(false, null);
        });

    }

    private JFrame tool;


    public TowerType getUpgradeType(){
        return upgradeType;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        showHideToolTip(true, e.getLocationOnScreen());
    }


    @Override
    public void mouseExited(MouseEvent e) {
        showHideToolTip(false,null);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        moveWindow(e.getLocationOnScreen());
    }

    private void showHideToolTip(boolean b, Point p) {
        if(b)
            moveWindow(p);

        tool.setVisible(b);
        tool.repaint();

    }
    private void moveWindow(Point point) {
        SwingUtilities.invokeLater(()->{
            tool.setLocation(new Point((int)point.getX() + 10, (int)point.getY() + 1));
            tool.repaint();
        });
    }



    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

}
