package com.tracv.swing;

import com.tracv.swing.Button;
import com.tracv.types.TowerType;
import com.tracv.util.Constants;

import javax.swing.*;

public class TowerUpgradeButton extends Button {

    private TowerType upgradeType;



    public TowerUpgradeButton(TowerType tt){
        super();

        this.setIcon(new ImageIcon(tt.getSprite()));
        this.setText(tt.getCost() + " g");

        this.setVerticalTextPosition(SwingConstants.BOTTOM);
        this.setHorizontalTextPosition(SwingConstants.CENTER);

        this.setIconTextGap(3);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setFont(Constants.SMALL_LABEL_FONT);

        upgradeType = tt;

    }

    public TowerType getUpgradeType(){
        return upgradeType;
    }
}
