package com.tracv.util;

import javax.swing.*;
import java.awt.*;

/*
    Static methods for adding components
 */
public class Comp {

    public static void add(JComponent toAdd, JComponent addTo, int x, int y, int width, int height,
                           double weightx, double weighty, int fill, int anchor,
                           int i, int j, int k, int l){
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
        c.insets = new Insets(i, j, k, l);

        addTo.add(toAdd, c);
    }
    public static void add(JComponent toAdd, JComponent addTo, int x, int y, int width, int height,
                           double weightx, double weighty, int fill, int anchor) {
        add(toAdd, addTo, x, y, width, height, weightx, weighty, fill, anchor, 0, 0, 0, 0);
    }

    public static void add(JComponent toAdd, JComponent addTo) {
        add(toAdd, addTo, 0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
    }

    public static void add(JComponent toAdd, JLayeredPane addTo, int i) {
        add(toAdd, addTo, i, 0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
    }
    public static void add(JComponent toAdd, JLayeredPane addTo, int i, int x, int y, int width, int height,
                           double weightx, double weighty, int fill, int anchor) {
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

        addTo.setLayer(toAdd, i);
        addTo.add(toAdd, c, i);
    }
}
