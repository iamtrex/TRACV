package com.tracv.ui.game;

import com.tracv.directional.PointToPointDistance;
import com.tracv.model.GameComponent;
import com.tracv.model.GameState;
import com.tracv.observerpattern.Observable;
import com.tracv.observerpattern.Observer;
import com.tracv.types.TowerType;
import com.tracv.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * The 'game' portion of the interface (not including hud).
 *
 * TODO - May change implementation to have 3 layers: Background (Terrain), moveable sprites, and mouse... and only redraw as needed
 *      a. Not interfere
 *      b. Redraw faster?
 */
public class GamePane extends JPanel implements Observer{

    private Point mouse;

    private GameState gs;
    private TowerType selectedTower;

    public GamePane(){
        gs = new GameState();

        this.setPreferredSize(Constants.GAME_DIMENSION);
        this.setBackground(Color.BLUE);

        this.addMouseListener(new MyMouseListener());
        this.addMouseMotionListener(new MyMouseMotionListener());

    }

    public GameState getGameState(){
        return gs;
    }

    public void attemptToBuildTower(Point point) {
        if(selectedTower != null) {
            System.out.println("Attempting to build tower of type " + selectedTower.getName() +
                    " on point " + point.getX() + "," + point.getY());
        } else{
            System.out.println("No Tower selected, no build.");
        }//GS will handle null cases, currently here for tests.


        gs.attemptToBuildTower(point, selectedTower);

    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        //DONE -- Awaiting implementation of getGameComponents();
        //DONE -- consider implementing iterator in gs.

        //TODO -- Awaiting Draw implementation of GameComponents();
        /*
        for(GameComponent gc : gs){
            gc.draw(g);
        }
        */


        //Draw Mouse hover.
        //TODO - Implement actual drawing... Right now draws a RED dot
                // -> Get the Sprite based off of TowerType that is currently selected.

        if(mouse != null) {
            //TODO - Ask GameState if the current position is legal. (Determine red or green shade on the object)

            /* //Old testing code to draw a red dot.
            g.setColor(Color.RED);
            int r = 5; //5 pixel radius.
            int x = (int) Math.round(this.mouse.getX() - r);
            int y = (int) Math.round(this.mouse.getY() - r);

            g.fillOval(x, y, 2*r, 2*r);
            */

            //TODO CURRENTLY DRAWS FULL SPRITE... NEED TO ADJUST...
            //TODO ALSO SHOULD DRAW TO THE GRID? idek anymore... maybe not draw this so early
            if(selectedTower != null) { // Only draw if currently has selected tower!

                int x = (int) Math.round(this.mouse.getX() - selectedTower.getWidth()/2);
                int y = (int) Math.round(this.mouse.getY() - selectedTower.getHeight()/2);
                g.drawImage(selectedTower.getSprite(), x, y, null);
            }
        }
    }

    @Override
    public void update(Observable o) {

    }

    public void setSelectedTower(TowerType selectedTower) {
        this.selectedTower = selectedTower;
    }


    private class MyMouseListener implements MouseListener {
        private Point click;
        @Override
        public void mouseClicked(MouseEvent e) {}

        //Registers point of first click, then builds tower on mouse release.
        @Override
        public void mousePressed(MouseEvent e) {
            if(click == null) {
                click = e.getPoint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(click != null){
                if(PointToPointDistance.withinDistance(e.getPoint(), click)) {
                    attemptToBuildTower(e.getPoint());
                }
                //Set to null regardless
                click = null;
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {
            mouse = null;
            SwingUtilities.invokeLater(()->GamePane.this.repaint());
        }
    }




    private class MyMouseMotionListener implements MouseMotionListener {
        @Override
        public void mouseDragged(MouseEvent e) {}

        //Creates a hover image of where to build the tower...
        @Override
        public void mouseMoved(MouseEvent e) {
            mouse = e.getPoint();
            SwingUtilities.invokeLater(()->GamePane.this.repaint());
        }
    }

}
