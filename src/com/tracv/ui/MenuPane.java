package com.tracv.ui;

import com.tracv.swing.Button;
import com.tracv.util.Comp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/*
 * Represents a Menu Overlay.
 */
public class MenuPane extends JPanel implements ActionListener {

    private TDFrame tdf;

    private Button newGame;
    private Button returnToMain;


    public MenuPane(TDFrame tdf) {
        this.tdf = tdf;
        //    this.setBorder(new LineBorder(Color.RED, 3));
        this.addMouseListener(new MyMouseListener());

        returnToMain = new Button("Return to Main");
        Comp.add(returnToMain, this, 0, 0, 1, 1, 1, 1,
                GridBagConstraints.NONE, GridBagConstraints.CENTER);
        returnToMain.addActionListener(this);




    }

    private BufferedImage background;

    /**
     * Override set visible to create blurred image everytime the menu is shown.
     * @param b
     */
    @Override
    public void setVisible(boolean b){
        if(b){
            createBlurredImage();
        }

        super.setVisible(b);
    }

    /**
     * Takes a blurred image of where the menu is currently placed.
     */
    private void createBlurredImage(){
        try {
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(new Rectangle(
                    tdf.getX()+this.getX(), tdf.getY()+this.getY(), this.getWidth(), this.getHeight()));

            Kernel kernel = new Kernel(3, 3, new float[]{1f / 9f, 1f / 9f, 1f / 9f,
                    1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f, 1f / 9f});
            BufferedImageOp op = new ConvolveOp(kernel);
            background = op.filter(image, null);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Override paint to draw the background image as well as paint a translucent foggy background to create
     *      the frosted glass effect.
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(background != null) {
            g.drawImage(background, 0, 0, null);
        }
        g.setColor(new Color(238,242,245,200));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == returnToMain) {
            tdf.switchToMainPanel();
        }
    }


    private class MyMouseListener implements MouseListener {
        private boolean down = false;

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            down = true;
        }

        /**
         * Close menu everytime user clicks outside of buttons
         * @param e
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            if (down) {
                tdf.toggleMenu(false);
                down = false;
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
