package com.tracv.swing;

import java.awt.*;

public class BlankPane extends Pane{
    public BlankPane(Dimension d){
        this.setPreferredSize(d);
        this.setMinimumSize(d);
        this.setMaximumSize(d);

    }
}
