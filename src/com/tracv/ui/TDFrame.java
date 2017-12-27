package com.tracv.ui;

import com.tracv.util.Comp;
import com.tracv.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The Window containing all other components to the program
 */
public class TDFrame extends JFrame {

    //The various content panels.
    private MainPane mainPane;
    private TDGame tdGame;

    //GlassPane
    private MenuPane menuPane;


    public TDFrame() {
        //Create Content Panes.
        mainPane = new MainPane(this);
        mainPane.setOpaque(false);
        tdGame = new TDGame(this);
        tdGame.setOpaque(false);

        menuPane = new MenuPane(this);

        this.setUndecorated(true);
        //this.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.8f));

        this.setContentPane(mainPane);
        this.setGlassPane(menuPane);

        this.setSize(Constants.FRAME_DEFAULT_SIZE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Comp.center(this);
        this.setVisible(true);
    }

    public static void main(String args[]) {
        new TDFrame(); //Starts the program by calling TDFrame's constructor
    }


    public void switchToMainPanel() {
        switchPanel(mainPane);
    }
    public void switchToGamePanel() {
        switchPanel(tdGame);
    }

    private void switchPanel(JComponent panel){
        this.setContentPane(panel);
        if(menuPane.isVisible()){
            toggleMenu(false);
        }else {
            SwingUtilities.invokeLater(() -> this.validate());
        }


    }

    public void toggleMenu(boolean b) {
        menuPane.setVisible(b);
        SwingUtilities.invokeLater(()-> this.validate());

    }

}
