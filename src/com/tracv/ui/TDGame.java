package com.tracv.ui;

import com.tracv.directional.PointToPointDistance;
import com.tracv.model.GameState;
import com.tracv.observerpattern.Observable;
import com.tracv.observerpattern.Observer;
import com.tracv.swing.IconButton;
import com.tracv.ui.game.GamePane;
import com.tracv.ui.game.HUDPane;
import com.tracv.util.Comp;
import com.tracv.util.Constants;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The Game.
 */
public class TDGame extends JLayeredPane implements ActionListener, Observer{

    private TDFrame tdf;

    private HUDPane hudPane;
    private GamePane gamePane;
    private MenuPane menuPane;

    private IconButton pause;

    public TDGame(TDFrame tdf){
        this.tdf = tdf;

        hudPane = new HUDPane();
        gamePane = new GamePane();
        GameState gs = gamePane.getGameState();
        gs.addObserver(this);
        gs.addObserver(hudPane);
        gs.addObserver(gamePane);

        menuPane = new MenuPane();

        Comp.add(gamePane, this, 0, 0, 0, 1, 1, 1, 1,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        //Align to bottom edge.
        Comp.add(hudPane, this, 0, 0, 1, 1, 1, 1, 1,
                GridBagConstraints.NONE, GridBagConstraints.BASELINE);

        //Align to top right corner with default sizes.
        Comp.add(menuPane, this, 1, 0, 0, 1, 1, 1, 1,
                GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END);

        gamePane.addMouseListener(new MyMouseListener());

    }

    @Override
    public void update(Observable o) {
        GameState gs = gamePane.getGameState();
        if(o == gs){
            if(gs.isGameOver()){
                tdf.toggleMenu(true);
            }
        }
    }


    private class MenuPane extends JPanel{
        public MenuPane(){
            this.setPreferredSize(Constants.ICON_SIZE); //TODO REMOVE TEMP.
            this.setBorder(new LineBorder(Color.BLUE, 3));
            pause = new IconButton("Pause"); //Show menu;
            pause.addActionListener(TDGame.this::actionPerformed);
            Comp.add(pause, this, 0, 0, 1, 1, 0, 0,
                    GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source == pause){
            gamePane.getGameState().pause();
            tdf.toggleMenu(true);
        }

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
                    gamePane.attemptToBuildTower(e.getPoint(), hudPane.getSelectedTowerType());
                }
                //Set to null regardless
                click = null;
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
}
