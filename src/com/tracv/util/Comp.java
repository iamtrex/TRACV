package com.tracv.util;

import com.tracv.ui.TDFrame;
import com.tracv.ui.TDGame;
import com.tracv.ui.game.HUDPane;

import javax.swing.*;
import java.awt.*;

/*
    Static methods for adding components
 */
public class Comp {
    public static void add(JComponent toAdd, JComponent addTo, int x, int y, int width, int height,
                           double weightx, double weighty, int fill, int anchor,
                           Insets i){
        if (!(addTo.getLayout() instanceof GridBagLayout)) {
            addTo.setLayout(new GridBagLayout());
        }

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = width;
        c.gridheight = height;
        c.fill = fill;
        c.anchor = anchor;
        c.gridx = x;
        c.gridy = y;
        c.weightx = weightx;
        c.weighty = weighty;
        c.insets = i;

        addTo.add(toAdd, c);
    }
    /**
     * Adds component based off GridBagConstraints.
     * i,j,k,l represent the insets.
     */
    public static void add(JComponent toAdd, JComponent addTo, int x, int y, int width, int height,
                            double weightx, double weighty, int fill, int anchor,
                            int i, int j, int k, int l){
        add(toAdd, addTo, x, y, width, height, weightx, weighty, fill, anchor, new Insets(i, j, k, l));
    }

    //Overloaded add methods.
    public static void add(JComponent toAdd, JComponent addTo, int x, int y, int width, int height,
                           double weightx, double weighty, int fill, int anchor) {
        add(toAdd, addTo, x, y, width, height, weightx, weighty, fill, anchor, 0, 0, 0, 0);
    }

    public static void add(JComponent toAdd, JComponent addTo) {
        add(toAdd, addTo, 0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
    }

    //Adding to JLayeredPane - Includes parameter for layer
    public static void add(JComponent toAdd, JLayeredPane addTo, int i, int x, int y, int width, int height,
                           double weightx, double weighty, int fill, int anchor) {
        add(toAdd, addTo, i, x, y, width, height, weightx, weighty, fill, anchor, 0, 0, 0, 0);
    }

    public static void add(JComponent toAdd, JLayeredPane addTo, int i, int x, int y, int width, int height,
                           double weightx, double weighty, int fill, int anchor, int j, int k, int l, int m){
        if (!(addTo.getLayout() instanceof GridBagLayout)) {
            addTo.setLayout(new GridBagLayout());
        }

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = width;
        c.gridheight = height;
        c.fill = fill;
        c.anchor = anchor;
        c.gridx = x;
        c.gridy = y;
        c.weightx = weightx;
        c.weighty = weighty;
        Insets in = new Insets(j, k, l, m);
        c.insets = in;
        addTo.setLayer(toAdd, i);
        addTo.add(toAdd, c, i);
    }


    /**
     * Centers Frame
     * @param frame = frame to center.
     */
    public static void center(JFrame frame) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2 - frame.getSize().width/2,
                dim.height/2 - frame.getSize().height/2);
    }

}
