package com.tracv.ui;

import com.tracv.swing.Frame;
import com.tracv.util.*;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * The Window containing all other components to the program
 */
public class TDFrame extends Frame implements WindowFocusListener {

    //The various content panels.
    private MainPane mainPane;
    private TDGame tdGame;
    private LevelSelectPane levelSelectPane;
    private SettingsPane settingsPane;

    //GlassPane
    private MenuPane menuPane;

    //Holds previous pane allows us to jump back
    private JComponent last;

    //Keeps mouse on the map.
    private MouseHooker mouseHooker;

    /**
     * Initializes program.
     */
    public TDFrame() {
        super();
        mouseHooker = new MouseHooker(this);
        this.addWindowFocusListener(this);

        //Create Content Panes.
        mainPane = new MainPane(this);
        tdGame = new TDGame(this);
        levelSelectPane = new LevelSelectPane(this, tdGame);
        menuPane = new MenuPane(this, tdGame);
        settingsPane = new SettingsPane(this);

        this.setContentPane(mainPane);
        this.setGlassPane(menuPane);
    }

    public static void main(String args[]) {
        new TDFrame(); //Starts the program by calling TDFrame's constructor
        Logger.getInstance().log("Program Startup Complete", LoggerLevel.STATUS);
    }

    public void switchToMainPanel() {
        switchPanel(mainPane);
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
        if(panel == tdGame){
            mouseHooker.setActive(true);
        }else{
            mouseHooker.setActive(false);
        }
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
            menuPane.showMenu((JComponent)this.getContentPane(), msg);
            mouseHooker.setActive(false);
        }else {
            menuPane.setVisible(false);

            //If switching back to game, must grab mouse binder.
            if(this.getContentPane() == tdGame){
                mouseHooker.setActive(true);
            }
        }
        SwingUtilities.invokeLater(()-> this.validate());
    }

    /**
     * Default toggle menu.
     *
     */
    public void toggleMenu(boolean b) {
        toggleMenu(b, null);
    }

    public void returnToLast() {
        switchPanel(last);
    }


    //Adjust MouseHooker based off if we have focus on the window
    @Override
    public void windowGainedFocus(WindowEvent e) {
        if(TDFrame.this.getContentPane() == tdGame){
            mouseHooker.setActive(true);
        }
    }
    @Override
    public void windowLostFocus(WindowEvent e) {
        mouseHooker.setActive(false);
    }

}
