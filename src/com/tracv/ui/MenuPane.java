package com.tracv.ui;

import com.tracv.swing.Button;
import com.tracv.swing.Label;
import com.tracv.util.Comp;
import com.tracv.util.Constants;

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
    private TDGame tdg;


    private Label displayMessage;
    private Button restartGame;
    private Button resumeGame;
    private Button returnToMain;
    private Button settings;

    private boolean clickToReturnEnabled = false;

    private BufferedImage background;

    public MenuPane(TDFrame tdf, TDGame tdg) {
        this.tdf = tdf;
        this.tdg = tdg;

        this.addMouseListener(new MyMouseListener());

        displayMessage = new Label("", Label.LARGE);
        resumeGame = new Button("Resume");
        restartGame = new Button("Restart");
        returnToMain = new Button("Return to Main");
        settings = new Button("Settings");

        Comp.add(displayMessage, this, 0, 0, 1, 1, 1, 0,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER, 0, 0, 10, 0); //Add 10 spacer to bottom

        Comp.add(resumeGame, this, 0, 1, 1, 1, 1, 0,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        Comp.add(restartGame, this, 0, 2, 1, 1, 1, 0,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        Comp.add(returnToMain, this, 0, 3, 1, 1, 1, 0,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        Comp.add(settings, this, 0, 4, 1, 1, 1, 0,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        resumeGame.addActionListener(this);
        restartGame.addActionListener(this);
        returnToMain.addActionListener(this);
        settings.addActionListener(this);
    }

    private void resume() {
        if(resumeGame.isVisible()){ //Equivalent to if(tdf.getContentPane == tdg)
            tdg.resumeGame(); //Resume game since it must be panel underneath
        }
        tdf.toggleMenu(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == resumeGame){
            resume();
        }else if(source == restartGame){
            tdg.restartGame();
        }else if (source == returnToMain) {
            tdf.switchToMainPanel();
        }else if(source == settings){
            tdf.switchToSettingsPanel();
        }
        tdf.toggleMenu(false);
    }


    /**
     * Show different menus based off of where the source is from.
     * @param source
     * @param msg
     */
    public void showMenu(JComponent source, String msg){
        if(source instanceof TDGame){
            if(msg == null){ //Pause game?
                displayMessage.setVisible(false);
                resumeGame.setVisible(true);
                clickToReturnEnabled = true;
            }else if(msg.equals(Constants.OBSERVER_LEVEL_COMPLETE)){
                displayMessage.setText("Level Complete! :)");
                displayMessage.setVisible(true);
                resumeGame.setVisible(false);
                clickToReturnEnabled = false;
            }else if(msg.equals(Constants.OBSERVER_LEVEL_FAILED)){
                displayMessage.setText("Game Over! :(");
                displayMessage.setVisible(true);
                resumeGame.setVisible(false);
                clickToReturnEnabled = false;
            }
            restartGame.setVisible(true);
            returnToMain.setVisible(true);
            settings.setVisible(true);
            clickToReturnEnabled = true;
        }else if(source instanceof MainPane){
            clickToReturnEnabled = false;
            displayMessage.setVisible(false);
            resumeGame.setVisible(false);
            restartGame.setVisible(false);
            returnToMain.setVisible(true);
            settings.setVisible(true);
        }
        setVisible(true);
    }

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
        g.setColor(new Color(238,242,245,200)); //TODO move to constants.
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    private class MyMouseListener implements MouseListener {
        /**
         * Close menu everytime user clicks outside of buttons
         * @param e
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            if(clickToReturnEnabled) { //Only allow cancel if resumeGame is visible...
                resume();
            }
        }

        @Override
        public void mousePressed(MouseEvent e){}
        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
    }

}
