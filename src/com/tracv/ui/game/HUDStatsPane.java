package com.tracv.ui.game;

import com.tracv.model.GameState;
import com.tracv.gamecomponents.Tower;
import com.tracv.observerpattern.Observable;
import com.tracv.observerpattern.Observer;
import com.tracv.swing.Button;
import com.tracv.swing.Label;
import com.tracv.swing.Pane;
import com.tracv.swing.TowerUpgradeButton;
import com.tracv.types.TowerType;
import com.tracv.util.Comp;
import com.tracv.util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 *  Holds stats of selected item.
 *
 */
public class HUDStatsPane extends Pane implements Observer, ActionListener {
    private HUDPane hudPane;
    private GameState gs;


    private List<TowerUpgradeButton> towerUpgrades;
    private Label type;
    private Button sellTower;
    private JPanel upgradesPane;



    private Tower selectedTower;



    public HUDStatsPane(HUDPane hudPane, GameState gs) {
        this.hudPane = hudPane;
        this.gs = gs;

        selectedTower = null;
        towerUpgrades = new ArrayList<>();
        type = new Label("", Label.MEDIUM, Label.CENTER);

        sellTower = new Button("Sell ()");
        sellTower.setFont(Constants.MEDIUM_LABEL_FONT); //Override fonta

        upgradesPane = new JPanel();

        sellTower.addActionListener(this);
        //this.setPreferredSize(Constants.HUD_STATS_SIZE);


        Comp.add(type, this, 0, 0, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        Comp.add(sellTower, this, 0, 1, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
        Comp.add(upgradesPane, this, 0, 2, 1, 1, 0, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        this.setPreferredSize(Constants.HUD_STATS_SIZE);
        upgradesPane.setPreferredSize(
                new Dimension((int)this.getPreferredSize().getWidth(), (int)(this.getPreferredSize().getHeight()*0.5)));

        this.setVisible(false);


    }


    @Override
    public void update(Observable o, String msg) {
        if(msg.equals(Constants.OBSERVER_TOWER_SELECTED) || msg.equals(Constants.OBSERVER_UPGRADED_TOWER)){
            displayTowerInfo(gs.getSelectedTower());
        }
    }

    private void displayTowerInfo(Tower selectedTower) {
        this.selectedTower = selectedTower;

        System.out.println("Updating Stats Pane");

        upgradesPane.removeAll();
        towerUpgrades.clear();

        if(selectedTower != null) {
            List<TowerType> upgradeTypes = selectedTower.getUpgrades();
            for (TowerType tt : upgradeTypes) {
                towerUpgrades.add(createUpgradeButton(tt));
            }

            type.setText(selectedTower.getTypeName());
            sellTower.setText("Sell " + selectedTower.getSellPrice() + " (g)");

            for (int i = 0; i < towerUpgrades.size(); i++) {
                Comp.add(towerUpgrades.get(i), upgradesPane, i % 3, i / 3, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);

            }
            this.setVisible(true);
        }else{
            this.setVisible(false);
        }
        SwingUtilities.invokeLater(()-> this.repaint());
    }

    private TowerUpgradeButton createUpgradeButton(TowerType tt) {
        TowerUpgradeButton b = new TowerUpgradeButton(tt);
        b.addActionListener(this);
        return b;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(TowerUpgradeButton b : towerUpgrades){
            if(e.getSource() == b){

                gs.attemptUpgradeTower(selectedTower, b.getUpgradeType());

                return;
            }
        }

        if(e.getSource() == sellTower){
            gs.attemptSellTower(selectedTower);

        }


    }
}
