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
    private LevelSelectPane levelSelectPane;
    private SettingsPane settingsPane;

    //GlassPane
    private MenuPane menuPane;

    private JComponent last;


    public TDFrame() {
        //Create Content Panes.
        mainPane = new MainPane(this);
        tdGame = new TDGame(this);

        levelSelectPane = new LevelSelectPane(this);

        menuPane = new MenuPane(this);

        settingsPane = new SettingsPane(this);

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
        newGame(1); //Default
    }
    public void newGame(int i){
        tdGame.startNewGame(i);
        if(this.getContentPane() != tdGame) {
            switchToGamePanel();
        }
    }

    public void switchToLevelSelectPanel(){
        switchPanel(levelSelectPane);
    }
    public void switchToGamePanel() {
        switchPanel(tdGame);
        tdGame.grabFocus(); // Give panel focus.
    }

    public void switchToSettingsPanel(){
        switchPanel(settingsPane);
    }
    private void switchPanel(JComponent panel){
        last = (JComponent) this.getContentPane();
        this.setContentPane(panel);
        SwingUtilities.invokeLater(() -> this.validate());

    }


    /**
     * Bit hacky rn... Maybe do it a better way?
     * @param b - show menu if true, hide if false
     * @param msg
     */
    public void toggleMenu(boolean b, String msg) {
        if(b){
            menuPane.showMenu((JComponent) this.getContentPane(), msg);
        }else {
            menuPane.setVisible(false);
        }

        //If resuming game, resume game. // should be in else lol.
        if(this.getContentPane() == tdGame && !b){
            tdGame.getGameState().setGameRunning(true);
        }

        SwingUtilities.invokeLater(()-> this.validate());
    }

    public void toggleMenu(boolean b) {
        toggleMenu(b, null);
    }

    public void returnToLast() {
        switchPanel(last);
    }
}
