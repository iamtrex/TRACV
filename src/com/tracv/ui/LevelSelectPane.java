package com.tracv.ui;

import com.tracv.model.LevelParser;
import com.tracv.swing.Label;
import com.tracv.util.Comp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LevelSelectPane extends JPanel {

    private Label title;
    private TDFrame tdFrame;

    private List<LevelSelectButton> buttons;


    public LevelSelectPane(TDFrame tdFrame){
        this.tdFrame = tdFrame;

        title = new Label("Select Level", Label.LARGE, Label.MID);
        buttons = new ArrayList<>();

        Comp.add(title, this, 0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        List<String> names = LevelParser.parseLevels();



    }


}
