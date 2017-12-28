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
 * TODO - May change implementation to draw the mouse overlay on a glasspane/layered pane on top of the game as to
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

        /* //TODO Awaiting implementation of getGameComponents();
        //TODO consider implementing iterator in gs.

        for(GameComponent gc : gs.getGameComponents()){
            gc.draw(g);
        }
        */


        //Draw Mouse hover.
        //TODO - Implement actual drawing... Right now draws a RED dot
        if(mouse != null) {
            //TODO - Ask GameState if the current position is legal. (Determine red or green shade on the object)

            g.setColor(Color.RED);
            int r = 5; //5 pixel radius.
            int x = (int) Math.round(this.mouse.getX() - r);
            int y = (int) Math.round(this.mouse.getY() - r);

            g.fillOval(x, y, 2*r, 2*r);
        }
    }

    @Override
    public void update(Observable o) {
        this.repaint();
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
