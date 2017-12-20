package com.tracv.ui;

import com.tracv.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 *  The overall game window.
 */
public class GameFrame extends JFrame {
    private HomePanel homePanel;
    private GamePanel gamePanel;

    private GameFrame(){
        //Run JFrame Constructor, passing the game name as the window's title.
        super(Constants.GAME_NAME);

        //Sets default page to the "Home Panel"
        homePanel = new HomePanel(this);
        setPanel(homePanel);

        gamePanel = new GamePanel(); //Create a game panel, however dont' do anything with it yet.


        //Shuts down the program when the window is closed.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(Constants.FRAME_DEFAULT_SIZE);

        //Center on screen
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - this.getWidth()) / 2, (screen.height - this.getHeight()) / 2);

        this.setVisible(true); //Show the window;
    }

    public void startNewGame() {
        if(this.getContentPane() != gamePanel){
            this.setContentPane(gamePanel);
        }
    }

    public void setPanel(JPanel panel){
        this.setContentPane(panel);

    }
    //Create and run a new instance of GameFrame
    public static void main(String[] args){
       new GameFrame();
    }


}
