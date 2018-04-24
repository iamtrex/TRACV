package com.tracv.ui.game;

import com.tracv.directional.Geometry;
import com.tracv.gamecomponents.Enemy;
import com.tracv.gamecomponents.GameComponent;
import com.tracv.gamecomponents.Terrain;
import com.tracv.model.*;
import com.tracv.observerpattern.Observable;
import com.tracv.observerpattern.Observer;
import com.tracv.swing.Pane;
import com.tracv.types.EnemyType;
import com.tracv.types.TerrainType;
import com.tracv.types.TowerType;
import com.tracv.util.Constants;
import com.tracv.util.Logger;
import com.tracv.util.LoggerLevel;

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

public class GamePane extends Pane implements Observer{

    private Point mouse;

    private GameState gs;
    private Evolver evolver;

    private TowerType selectedTower;

    private Rectangle selectedRegion;

    private boolean top, bot, left, right;

    public GamePane(){
        gs = new GameState();
        evolver = new Evolver(gs);

        resetRectangle();

        this.setPreferredSize(Constants.GAME_DIMENSION);
        this.setBackground(Color.BLACK);
        this.addMouseListener(new MyMouseListener());
        this.addMouseMotionListener(new MyMouseMotionListener());
        this.addKeyListener(new MyKeyListener());
        this.setFocusable(true);
    }

    public GameState getGameState(){
        return gs;
    }

    public void updateMapMove(boolean top, boolean bot, boolean left, boolean right) {
        this.top = top;
        this.bot = bot;
        this.left = left;
        this.right = right;
    }

    private void handleMapMove() {
        int speed = Constants.MAP_MOVE_SPEED / Constants.REFRESH_RATE;
        if (top) {
            selectedRegion.setLocation(
                    (int) selectedRegion.getX(), (int) selectedRegion.getY() - speed);

        } else if (bot) {
            selectedRegion.setLocation(
                    (int) selectedRegion.getX(), (int) selectedRegion.getY() + speed);
        }

        if (left) {
            selectedRegion.setLocation(
                    (int) selectedRegion.getX() - speed, (int) selectedRegion.getY());
        } else if (right) {
            selectedRegion.setLocation(
                    (int) selectedRegion.getX() + speed, (int) selectedRegion.getY());
        }

        int mapWidth = (int) gs.getMap().getMapDimensions().getWidth();
        int mapHeight = (int) gs.getMap().getMapDimensions().getHeight();

        if (selectedRegion.getX() + selectedRegion.getWidth() > mapWidth) {
            selectedRegion.setLocation(mapWidth - (int) selectedRegion.getWidth(), (int) selectedRegion.getY());
        }
        if (selectedRegion.getY() + selectedRegion.getHeight() > mapHeight) {
            selectedRegion.setLocation((int) selectedRegion.getX(), mapHeight - (int) selectedRegion.getHeight());
        }
        if (selectedRegion.getX() < 0) {
            selectedRegion.setLocation(0, (int) selectedRegion.getY());
        }
        if (selectedRegion.getY() < 0) {
            selectedRegion.setLocation((int) selectedRegion.getX(), 0);
        }
    }



    private boolean attemptToBuildTower(Point point) {
        if(selectedTower != null) {
            Logger.getInstance().log("Attempting to build tower of type " + selectedTower.getName() +
                    " on point " + point.getX() + "," + point.getY(), LoggerLevel.STATUS);
            point.setLocation((point.getX() + selectedRegion.getX()), (point.getY() + selectedRegion.getY()));

            return gs.attemptToBuildTower(point, selectedTower);
        } else{
            Logger.getInstance().log("No Tower selected, no build.", LoggerLevel.STATUS);
            return false;
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        handleMapMove();

        //DONE -- Awaiting implementation of getGameComponents();
        //DONE -- consider implementing iterator in gs.
        //DONE -- Awaiting Draw implementation of GameComponents, currently temporary

        //DISCONTINUED -- Switch to multilayered pane so that we don't have to redraw the terrainType each iteration
        //Draw TerrainType
        Terrain[][] terrains = gs.getTerrain();

        double blockSizeX = Constants.DEFAULT_BLOCK_SIZE;
        double blockSizeY = Constants.DEFAULT_BLOCK_SIZE;

        //Draw TerrainType
        for(int y = 0; y< terrains.length; y++){
            for(int x = 0; x< terrains[y].length; x++){
                if(!Geometry.isObjectInsideRegion(terrains[y][x], selectedRegion)){
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
                g.fillRect((int)(x*blockSizeX + 1 - selectedRegion.getX()),
                        (int)(y*blockSizeY + 1 - selectedRegion.getY()),
                        (int)blockSizeX,
                        (int)blockSizeY);

                //Draw Border if buildable
                if(terrains[y][x].getType() == TerrainType.BUILDABLE) {
                    g.setColor(Color.BLACK);
                    g.drawRect((int)(x * blockSizeX - selectedRegion.getX()),
                            (int)(y * blockSizeY - selectedRegion.getY()),
                            (int)blockSizeX,
                            (int)blockSizeY);
                }

            }
        }

        for(GameComponent gc : gs){
            if(Geometry.isObjectInsideRegion(gc, selectedRegion)) {
                gc.draw(g, selectedRegion);
            }
        }


        //Draw Mouse hover.
        if(mouse != null) {
            drawTowerHighlightOnMouse(g);
        }
        //System.out.println("Draw Time Taken " + (System.nanoTime() - curr)/1000000);
    }

    /*
    Draws an outline of the tower on the screen
    //DONE - Implement actual drawing... Right now draws a RED dot
                // -> Get the Sprite based off of TowerType that is currently selected.

     */
    private void drawTowerHighlightOnMouse(Graphics g){
        //DONE - Ask GameState if the current position is legal. (Determine red or green shade on the object)
        //DONE -- CURRENTLY DRAWS FULL SPRITE... NEED TO ADJUST...
        //TODO ALSO SHOULD DRAW TO THE GRID? idek anymore... maybe not draw this so early

        if(selectedTower != null) { // Only draw if currently has selected tower!
            Image img;
            Point point = mouse.getLocation();
            point.setLocation((point.getX() + selectedRegion.getX()), (point.getY() + selectedRegion.getY()));

            if(gs.isTowerBuildValid(point, selectedTower)){
                img = selectedTower.getSpriteActive();
            }else{
                img = selectedTower.getSpriteDeactive();
            }

            int x = (int) Math.round(this.mouse.getX() - selectedTower.getWidth()/2);
            int y = (int) Math.round(this.mouse.getY() - selectedTower.getHeight()/2);
            g.drawImage(img, x, y, null);
        }
    }

    @Override
    public void update(Observable o, String msg) {
        if(msg.equals(Constants.OBSERVER_GAME_TICK)){
            this.repaint();
        }else if(msg.equals(Constants.OBSERVER_GAME_PAUSED) || msg.equals(Constants.OBSERVER_GAME_OVER) || msg.equals(Constants.OBSERVER_LEVEL_COMPLETE)){
            resetRectangle();
        }else if(msg.equals(Constants.OBSERVER_NEW_GAME)){
            resetRectangle();
        }
    }
    
    private void resetRectangle(){
        selectedRegion = new Rectangle(0,0,
                (int)Constants.GAME_DIMENSION.getWidth(), (int)Constants.GAME_DIMENSION.getHeight());

        top = false;
        bot = false;
        left = false;
        right = false;
    }

    public void setSelectedTower(TowerType selectedTower) {
        this.selectedTower = selectedTower;
    }


    //Key and Mouse Listeners
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
                if(Geometry.withinDistance(p, click)) {
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
        public void mouseDragged(MouseEvent e) {
            GamePane.this.getParent().dispatchEvent(e);
        }

        //Creates a hover image of where to build the tower...
        @Override
        public void mouseMoved(MouseEvent e) {
            mouse = e.getPoint();
            GamePane.this.getParent().dispatchEvent(e);
            SwingUtilities.invokeLater(()->GamePane.this.repaint());
        }
    }

}
