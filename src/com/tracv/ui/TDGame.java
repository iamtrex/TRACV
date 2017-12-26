package com.tracv.ui;

import com.tracv.swing.Button;
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

/**
 * The Game.
 */
public class TDGame extends JLayeredPane implements ActionListener{

    private TDFrame tdf;

    private HUDPane hudPane;
    private GamePane gamePane;
    private MenuPane menuPane;

    private IconButton pause;

    public TDGame(TDFrame tdf){
        this.tdf = tdf;

        hudPane = new HUDPane();
        gamePane = new GamePane();
        menuPane = new MenuPane();

        Comp.add(gamePane, this, 0, 0, 0, 1, 1, 1, 1,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        //Align to bottom edge.
        Comp.add(hudPane, this, 0, 0, 1, 1, 1, 1, 1,
                GridBagConstraints.NONE, GridBagConstraints.BASELINE);

        //Align to top right corner with default sizes.
        Comp.add(menuPane, this, 1, 0, 0, 1, 1, 1, 1,
                GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END);


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
            //TODO Pause state of game and show menu.

            tdf.toggleMenu(true);
        }

    }
}
