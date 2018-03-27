package com.tracv.ui.game;

import com.tracv.directional.PointToPointDistance;
import com.tracv.gamecomponents.Enemy;
import com.tracv.gamecomponents.GameComponent;
import com.tracv.gamecomponents.Terrain;
import com.tracv.model.*;
import com.tracv.observerpattern.Observable;
import com.tracv.observerpattern.Observer;
import com.tracv.types.EnemyType;
import com.tracv.types.TerrainType;
import com.tracv.types.TowerType;
import com.tracv.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The 'game' portion of the interface (not including hud).
 *
 * TODO - May change implementation to have 3 layers: Background (TerrainType), moveable sprites, and mouse... and only redraw as needed
 *      a. Not interfere
 *      b. Redraw faster?
 */
public class GamePane extends JPanel implements Observer{


    private Rectangle selectedRegion;

    private Point mouse;

    private GameState gs;
    private TowerType selectedTower;

    // Acts as layers, so not the entire UI has to refresh on each iteration
    //private Rectangle mobLayer, towerLayer, projectileLayer, terrainLayer;

    public GamePane(){
        gs = new GameState();

        this.setPreferredSize(Constants.GAME_DIMENSION);
        this.setBackground(Color.BLUE);
        //this.setOpaque(false);
        this.addMouseListener(new MyMouseListener());
        this.addMouseMotionListener(new MyMouseMotionListener());

        this.setFocusable(true);
        this.addKeyListener(new MyKeyListener());
        //this.add(new JTextField("Hello World"));
        //setupKeyListeners(this);


    }

    public GameState getGameState(){
        return gs;
    }



    private boolean attemptToBuildTower(Point point) {
        if(selectedTower != null) {
            System.out.println("Attempting to build tower of type " + selectedTower.getName() +
                    " on point " + point.getX() + "," + point.getY());

            return gs.attemptToBuildTower(point, selectedTower);

        } else{
            System.out.println("No Tower selected, no build.");
            return false;
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        //TODO -- Remove this hack
        selectedRegion = new Rectangle(0,0,
                (int)Constants.GAME_DIMENSION.getWidth(), (int)Constants.GAME_DIMENSION.getHeight());

        //DONE -- Awaiting implementation of getGameComponents();
        //DONE -- consider implementing iterator in gs.

        //TODO -- Awaiting Draw implementation of GameComponents, currently temporary
        //TODO -- Switch to multilayered pane so that we don't have to redraw the terrainType each iteration


        //Draw TerrainType

        Terrain[][] terrains = getGameState().getTerrain();

        //int blockSizeX = this.getWidth() / terrains[0].length;
        //int blockSizeY = this.getHeight() / terrains.length;
        double blockSizeX = Constants.DEFAULT_BLOCK_SIZE;
        double blockSizeY = Constants.DEFAULT_BLOCK_SIZE;


        //double blockSize;

        //Draw TerrainType
        for(int y = 0; y< terrains.length; y++){
            for(int x = 0; x< terrains[y].length; x++){
                if(!PointToPointDistance.isObjectInsideRegion(terrains[y][x], selectedRegion)){
                    continue; // Skip object.
                }

                for(TerrainType t : TerrainType.getTerrains()){
                    if(t.equals(terrains[y][x].getType())){
                        g.setColor(t.getColor());
                        break;
                    }
                }

                //TODO perhaps change implementation of how we draw TerrainType
                //  Either use preloaded image? or fix how the border looks
                //  The +1 works because drawn from top left to bottom right, so the top and left borders
                //      Are already fine, but the bottom and right borders may be overwritten, however the +1 fixes that

                g.fillRect((int)(x*blockSizeX+1), (int)(y*blockSizeY+1), (int)blockSizeX, (int)blockSizeY);


                //Draw Border if buildable
                if(terrains[y][x].getType() == TerrainType.BUILDABLE) {
                    g.setColor(Color.BLACK);
                    g.drawRect((int)(x * blockSizeX),(int)(y * blockSizeY), (int)blockSizeX, (int)blockSizeY);
                }

            }
        }


        for(GameComponent gc : gs){
            if(PointToPointDistance.isObjectInsideRegion(gc, selectedRegion)) {
                gc.draw(g, selectedRegion);
            }
        }


        //Draw Mouse hover.
        if(mouse != null) {
            drawTowerHighlightOnMouse(g);
        }
    }

    /*
    Draws an outline of the tower on the screen
    //DONE - Implement actual drawing... Right now draws a RED dot
                // -> Get the Sprite based off of TowerType that is currently selected.

     */
    private void drawTowerHighlightOnMouse(Graphics g){
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
            if(gs.isTowerBuildValid(mouse.getLocation(), selectedTower)){
                g.setColor(Color.BLUE);
            }else{
                g.setColor(Color.RED);
            }
            int x = (int) Math.round(this.mouse.getX() - selectedTower.getWidth()/2);
            int y = (int) Math.round(this.mouse.getY() - selectedTower.getHeight()/2);
            //g.drawImage(selectedTower.getSprite(), x, y, null);
            g.fillRect(x, y, selectedTower.getWidth(), selectedTower.getHeight());
        }
    }

    @Override
    public void update(Observable o, String msg) {
        if(msg.equals(Constants.OBSERVER_GAME_TICK)){
            this.repaint();
        }
    }

    public void setSelectedTower(TowerType selectedTower) {
        this.selectedTower = selectedTower;
    }


    private class MyKeyListener implements KeyListener{

        //TODO REMOVE GOD MODE HACKS... HACKS
        @Override
        public void keyTyped(KeyEvent e) {
            //System.out.println("Key typed " + e.getKeyChar());
            if(Constants.DEBUGGING_MODE){
                if(e.getKeyChar() == 'e'){
                    //Spawn enemy.
                    //SwingUtilities.invokeLater(()-> gs.spawnEnemy(0, 0));
                    gs.getMap().addComponent(new Enemy(EnemyType.BASIC, 0, 0));
                }else if(e.getKeyChar() == 'm'){
                    System.out.println("Gold!");
                    gs.gainGold(1000); //Money maker!
                }
            }
        }
        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}
    }
    private class MyMouseListener implements MouseListener {
        private Point click;
        @Override
        public void mouseClicked(MouseEvent e) {
            GamePane.this.grabFocus();
        }

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
                Point p = e.getPoint();
                if(PointToPointDistance.withinDistance(p, click)) {
                    boolean b = attemptToBuildTower(p);

                    if(!b){ //Attempt to select tower if applicable
                        gs.attemptToSelectTower(p);
                    }
                }
                //Set to null regardless
                click = null;
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            GamePane.this.grabFocus();
        }

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
