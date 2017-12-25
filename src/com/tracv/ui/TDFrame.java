package com.tracv.ui;

import com.tracv.util.Comp;
import com.tracv.util.Constants;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The Window containing all other components to the game.
 */
public class TDFrame extends JFrame {

    //The various content panels.
    private MainPane mainPane;
    private TDGame tdGame;
    private MenuPane menuPane;

    //Underlying JLayeredPane which associates what thing to display.
    //TODO - Consider changing structure to Content/Glass with the Game having a JLayeredPane
    private BasePane basePane;

    public TDFrame() {
        //Create Content Panes.
        mainPane = new MainPane(TDFrame.this);
        tdGame = new TDGame();
        menuPane = new MenuPane();

        //Create LayeredPane
        basePane = new BasePane();
        this.setLayeredPane(basePane);

        this.setSize(Constants.FRAME_DEFAULT_SIZE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String args[]) {
        new TDFrame(); //Starts the program by calling TDFrame's constructor
    }

    public void switchToGamePanel() {
        basePane.switchPanel(tdGame);
    }


    private class BasePane extends JLayeredPane{

        JPanel currentPane;

        public BasePane(){
            this.switchPanel(mainPane);

            menuPane.addMouseListener(new MyMouseListener());
            menuPane.setVisible(false);

            Comp.add(menuPane, this, 1);
        }

        public JPanel getCurrentPane(){
            return currentPane;
        }

        public void toggleMenu(boolean on){
            if(on){
                menuPane.setVisible(true);
            }else{
                menuPane.setVisible(false);
            }
            SwingUtilities.invokeLater(()->this.validate());
        }

        public void switchPanel(JPanel panel) {
            if(currentPane != null){
                this.remove(currentPane);
            }
            Comp.add(panel, this, 0);

            currentPane = panel;

            SwingUtilities.invokeLater(()->this.validate());
        }

    }

    private class MyMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("hi");
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
