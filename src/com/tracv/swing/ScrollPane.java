package com.tracv.swing;

import javax.swing.*;

public class ScrollPane extends JScrollPane {
    public ScrollPane(){
        super();
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.getVerticalScrollBar().setUnitIncrement(10);


    }

    public void scrollDown(){
        this.getVerticalScrollBar().setValue((getVerticalScrollBar().getMaximum()));
        this.repaint();
    }
}
