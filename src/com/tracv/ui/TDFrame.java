package com.tracv.ui;

import com.tracv.util.Comp;
import com.tracv.util.Constants;

import javax.swing.*;

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

    //Create new game and swap to the game panel.
    public void newGame() {
        tdGame.startNewGame("TODO DEBUGGING");
        if(this.getContentPane() != tdGame) {
            switchToGamePanel();
        }
    }

    public void switchToGamePanel() {
        switchPanel(tdGame);
        tdGame.grabFocus(); // Give panel focus.
    }

    private void switchPanel(JComponent panel){
        this.setContentPane(panel);
        SwingUtilities.invokeLater(() -> this.validate());

    }


    /**
     * Bit hacky rn... Maybe do it a better way?
     * @param b - show menu if true, hide if false
     */
    public void toggleMenu(boolean b) {
        if(b){
            menuPane.showMenu((JComponent) this.getContentPane());
        }else {
            menuPane.setVisible(false);
        }

        //If resuming game, resume game. // should be in else lol.
        if(this.getContentPane() == tdGame && !b){
            tdGame.setGameRunning(true);
        }

        SwingUtilities.invokeLater(()-> this.validate());
    }

}
