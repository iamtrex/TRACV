package com.tracv.swing;

import com.tracv.types.TowerType;

import javax.swing.*;

public class TowerToolTip extends CustomToolTip {
    public TowerToolTip(TowerType t){
        super();
        content.add(new JLabel(t.getName()));
        this.pack();
    }
}
