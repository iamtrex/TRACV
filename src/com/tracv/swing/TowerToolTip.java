package com.tracv.swing;

import com.tracv.gamecomponents.Tower;
import com.tracv.types.TowerType;
import com.tracv.util.Comp;

import java.awt.*;

public class TowerToolTip extends CustomToolTip {

    private Label[][] dataArray;


    public TowerToolTip(TowerType t, Tower selectedTower){
        super();

        dataArray = new Label[5][2]; //5 entries 2 per line
        /*
        for(Label[] l : dataArray){
            l = new Label[2];
        }
        */

        TowerType prev = selectedTower.getType(); //For now using TowerType, but in teh future
                                                // if there are towerspecific upgrades, will have to use
                                                // selected tower instead.

        dataArray[0][0] = new Label("Type ", Label.SMALL_BOLD_FONT);
        dataArray[0][1] = new Label(t.getName(), Label.SMALL);

        dataArray[1][0] = new Label("Range ", Label.SMALL_BOLD_FONT);
        dataArray[1][1] = new Label(String.valueOf(prev.getRange()) + " -> " +
                String.valueOf(t.getRange()), Label.SMALL);

        dataArray[2][0] = new Label("Cost ", Label.SMALL_BOLD_FONT);
        dataArray[2][1] = new Label(String.valueOf(prev.getCost()) + " -> " +
                String.valueOf(t.getCost()), Label.SMALL);

        dataArray[3][0] = new Label("Rate ", Label.SMALL_BOLD_FONT);
        dataArray[3][1] = new Label(String.valueOf(prev.getFireRate()) + " -> " +
                String.valueOf(t.getFireRate()), Label.SMALL);

        dataArray[4][0] = new Label("Dmg ", Label.SMALL_BOLD_FONT);
        dataArray[4][1] = new Label(String.valueOf(prev.getDmg()) + " -> " +
                String.valueOf(t.getDmg()), Label.SMALL);



        for(int i=0; i<dataArray.length; i++){
            for(int j=0; j<dataArray[i].length; j++){
                Comp.add(dataArray[i][j], content, j, i, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
            }
        }
        this.pack();
        //this.setSize(new Dimension(100, 100));
    }
}

