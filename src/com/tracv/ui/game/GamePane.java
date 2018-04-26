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
import com.tracv.ui.TDGame;
import com.tracv.util.Constants;
import com.tracv.util.Logger;
import com.tracv.util.LoggerLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static com.tracv.model.State.PLAYING;

/**
 * The 'game' portion of the interface (not including hud).
 * <p>
 * TODO - May change implementation to have 3 layers: Background (TerrainType), moveable sprites, and mouse... and only redraw as needed
 * a. Not interfere
 * b. Redraw faster?
 */

public class GamePane extends Pane implements Observer {

    private Point mouse;

    private GameProcess game;

    private Rectangle selectedRegion;

    private boolean top, bot, left, right;

    private MapMoveThread mapMoveThread;


    public GamePane() {
        game = new GameProcess();

        resetRectangle();

        this.setPreferredSize(Constants.GAME_DIMENSION);
        this.setBackground(Color.BLACK);
        this.addMouseListener(new MyMouseListener());
        this.addMouseMotionListener(new MyMouseMotionListener());
        this.addKeyListener(new MyKeyListener());
        this.setFocusable(true);
    }


    /**
     * Draw the game.
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        synchronized (selectedRegion) {
            //System.out.println("Update Draw");
            Terrain[][] terrains = game.getTerrain();

            double blockSizeX = Constants.DEFAULT_BLOCK_SIZE;
            double blockSizeY = Constants.DEFAULT_BLOCK_SIZE;

            //Draw TerrainType
            for (int y = 0; y < terrains.length; y++) {
                for (int x = 0; x < terrains[y].length; x++) {
                    if (!Geometry.isObjectInsideRegion(terrains[y][x], selectedRegion)) {
                        continue; // Skip object.
                    }
                    g.setColor(terrains[y][x].getType().getColor());
                    g.fillRect((int) (x * blockSizeX + 1 - selectedRegion.getX()),
                            (int) (y * blockSizeY + 1 - selectedRegion.getY()),
                            (int) blockSizeX,
                            (int) blockSizeY);

                    //Draw Border if buildable
                    if (terrains[y][x].getType() == TerrainType.BUILDABLE) {
                        g.setColor(Color.BLACK);
                        g.drawRect((int) (x * blockSizeX - selectedRegion.getX()),
                                (int) (y * blockSizeY - selectedRegion.getY()),
                                (int) blockSizeX,
                                (int) blockSizeY);
                    }
                }
            }
            List<GameComponent> gcs = game.getMap().getGameComponents();

            synchronized (gcs) {
                for (GameComponent gc : gcs) {
                    if (Geometry.isObjectInsideRegion(gc, selectedRegion)) {
                        gc.draw(g, selectedRegion);
                    }
                }
            }

            //Draw Mouse hover.
            if (mouse != null) {
                drawTowerHighlightOnMouse(g);
            }
        }
    }

    /*
    Draws an outline of the tower on the screen
    //DONE - Implement actual drawing... Right now draws a RED dot
                // -> Get the Sprite based off of TowerType that is currently selected.

     */
    private void drawTowerHighlightOnMouse(Graphics g) {
        //DONE - Ask GameState if the current position is legal. (Determine red or green shade on the object)
        //DONE -- CURRENTLY DRAWS FULL SPRITE... NEED TO ADJUST...
        //TODO ALSO SHOULD DRAW TO THE GRID? idek anymore... maybe not draw this so early

        TowerType selectedTower = game.getBuildTowerType();
        if (selectedTower != null) { // Only draw if currently has selected tower!
            Image img;
            Point point = mouse.getLocation();
            convertToGamePoint(point);

            if (game.isTowerLocationValid(point)) {
                img = selectedTower.getSpriteActive();
            } else {
                img = selectedTower.getSpriteDeactive();
            }

            int x = (int) Math.round(this.mouse.getX() - selectedTower.getWidth() / 2);
            int y = (int) Math.round(this.mouse.getY() - selectedTower.getHeight() / 2);
            g.drawImage(img, x, y, null);
        }
    }

    @Override
    public void update(Observable o, String msg) {
        if (msg.equals(Constants.OBSERVER_GAME_TICK)) {
            this.repaint();
        } else if (msg.equals(Constants.OBSERVER_GAME_PAUSED) || msg.equals(Constants.OBSERVER_LEVEL_FAILED) || msg.equals(Constants.OBSERVER_LEVEL_COMPLETE)) {
            resetRectangle();
        } else if (msg.equals(Constants.OBSERVER_NEW_GAME)) {
            resetRectangle();
        } else if (msg.equals(Constants.OBSERVER_STATE_RUNNING)) {
            startMapThread();
        } else if (msg.equals(Constants.OBSERVER_STATE_PAUSED)) {

        } else if (msg.equals(Constants.OBSERVER_STATE_TERMINATED)) {

        }
    }


    private void resetRectangle() {
        selectedRegion = new Rectangle(0, 0,
                (int) Constants.GAME_DIMENSION.getWidth(), (int) Constants.GAME_DIMENSION.getHeight());
        top = false;
        bot = false;
        left = false;
        right = false;
    }

    public GameProcess getGameProcess() {
        return game;
    }


    //Key and Mouse Listeners
    private class MyKeyListener implements KeyListener {

        //TODO REMOVE GOD MODE HACKS... HACKS
        @Override
        public void keyTyped(KeyEvent e) {
            if (Constants.DEBUGGING_MODE) {
                if (e.getKeyChar() == 'e') {
                    game.getMap().addComponent(new Enemy(EnemyType.BASIC, 0, 0));
                } else if (e.getKeyChar() == 'm') {
                    System.out.println("Gold!");
                    game.gainGold(1000); //Money maker!
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    private class MyMouseListener implements MouseListener {
        private Point down;

        @Override
        public void mouseClicked(MouseEvent e) {
            GamePane.this.grabFocus();
        }

        //Registers point of first down, then builds tower on mouse release.
        @Override
        public void mousePressed(MouseEvent e) {
            if (down == null) {
                down = e.getPoint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (down != null) {
                Point p = e.getPoint();
                if (Geometry.withinDistance(p, down)) {
                    //Try to build tower first:
                    convertToGamePoint(p);
                    game.mouseClick(p);
                }
                //Set to null regardless
                down = null;
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            GamePane.this.grabFocus();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            mouse = null;
            //SwingUtilities.invokeLater(()->GamePane.this.repaint());
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
            Point point = e.getPoint();
            convertToGamePoint(point);
            mouse = point;
            GamePane.this.getParent().dispatchEvent(e);
            //SwingUtilities.invokeLater(()->GamePane.this.repaint());
        }
    }

    private void convertToGamePoint(Point point) {
        point.setLocation((point.getX() + selectedRegion.getX()), (point.getY() + selectedRegion.getY()));
    }

    private void startMapThread() {
        System.out.println("Creating Map Move Thread");
        if (mapMoveThread == null || mapMoveThread.getState() == Thread.State.TERMINATED) { //Reset thread if needed.
            mapMoveThread = new MapMoveThread();
        }

        if (mapMoveThread.isAlive()) {
            Logger.getInstance().log("Map Thread is somehow still alive... zz", LoggerLevel.WARNING);
            mapMoveThread.interrupt();
            try {
                mapMoveThread.join(1000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                Logger.getInstance().log("Map Thread now " + mapMoveThread.isAlive(), LoggerLevel.WARNING);
            }
        }
        mapMoveThread.start();
    }

    /**
     * Deals with moving the Selected Region of the map.
     */
    private class MapMoveThread extends Thread {
        @Override
        public void run() {
            while(game.getState() != PLAYING){
                try{
                    Thread.sleep(100);
                    System.out.println(game.getState().toString());
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            while (game.getState() == PLAYING) {
                System.out.println("Refresh Map");
                long start = System.nanoTime();
                Point mouse = MouseInfo.getPointerInfo().getLocation();
                Point p = GamePane.this.getLocationOnScreen();
                Rectangle r = new Rectangle((int) p.getX(), (int) p.getY(),
                        (int) Constants.GAME_DIMENSION.getWidth(), (int) Constants.GAME_DIMENSION.getHeight());

                top = Geometry.isPointInTop(mouse, r, Constants.MOVEMENT_PIXEL_PADDING);
                bot = Geometry.isPointInBot(mouse, r, Constants.MOVEMENT_PIXEL_PADDING);
                left = Geometry.isPointInLeft(mouse, r, Constants.MOVEMENT_PIXEL_PADDING);
                right = Geometry.isPointInRight(mouse, r, Constants.MOVEMENT_PIXEL_PADDING);
                handleMapMove();

                long delay = Math.round((System.nanoTime() - start) / 1000000.0);
                if (delay >= Constants.MAP_MOVE_REFRESH_DELAY) {
                    Logger.getInstance().log("Map move took too long, not sleeping", LoggerLevel.WARNING);
                    continue; //don't sleep.
                }
                try {
                    Thread.sleep(Constants.MAP_MOVE_REFRESH_DELAY - delay);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
            System.out.println("Terminating Map Thread");
        }

        private void handleMapMove() {
            System.out.println("Move map");
            synchronized (selectedRegion) {
                System.out.println("Actually Moving");
                int mapWidth = (int) game.getMap().getMapDimensions().getWidth();
                int mapHeight = (int) game.getMap().getMapDimensions().getHeight();
                int speed = Constants.MAP_MOVE_SPEED / Constants.REFRESH_RATE;
                if (top) {
                    selectedRegion.setLocation(
                            (int) selectedRegion.getX(), (int) selectedRegion.getY() - speed);

                    if (selectedRegion.getY() < 0) {
                        selectedRegion.setLocation((int) selectedRegion.getX(), 0);
                    }
                } else if (bot) {
                    selectedRegion.setLocation(
                            (int) selectedRegion.getX(), (int) selectedRegion.getY() + speed);

                    if (selectedRegion.getY() + selectedRegion.getHeight() > mapHeight) {
                        selectedRegion.setLocation((int) selectedRegion.getX(), mapHeight - (int) selectedRegion.getHeight());
                    }
                }
                if (left) {
                    selectedRegion.setLocation(
                            (int) selectedRegion.getX() - speed, (int) selectedRegion.getY());

                    if (selectedRegion.getX() < 0) {
                        selectedRegion.setLocation(0, (int) selectedRegion.getY());
                    }
                } else if (right) {
                    selectedRegion.setLocation(
                            (int) selectedRegion.getX() + speed, (int) selectedRegion.getY());
                    if (selectedRegion.getX() + selectedRegion.getWidth() > mapWidth) {
                        selectedRegion.setLocation(mapWidth - (int) selectedRegion.getWidth(), (int) selectedRegion.getY());
                    }
                }
            }
        }
    }
}
