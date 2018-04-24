package com.tracv.ui;

import com.tracv.model.LevelParser;
import com.tracv.swing.Label;
import com.tracv.swing.Pane;
import com.tracv.swing.Button;
import com.tracv.util.Comp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class LevelSelectPane extends JPanel implements ActionListener {
    private TDFrame tdf;
    private TDGame tdg;

    private Label title;
    private List<LevelSelectButton> buttons;
    private Pane buttonsPane;
    private Button back;

    public LevelSelectPane(TDFrame tdf, TDGame tdg){
        this.tdf = tdf;
        this.tdg = tdg;

        title = new Label("Select Level", Label.LARGE, Label.MID);
        buttons = new ArrayList<>();

        Comp.add(title, this, 0, 0, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);


        back = new Button("Back");
        back.addActionListener(this);
        Comp.add(back, this, 0, 1, 1, 1, 1, 0, GridBagConstraints.BOTH, GridBagConstraints.CENTER);


        buttonsPane = new Pane();

        List<String> names = LevelParser.parseLevels();
        for(int i=0; i<names.size(); i++) {
            LevelSelectButton button = new LevelSelectButton(names.get(i));
            buttons.add(button);
            Comp.add(button, buttonsPane, i % 3, i / 3, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
            button.addActionListener(this);
        }

        Comp.add(buttonsPane, this, 0, 2, 1, 1, 1, 10, GridBagConstraints.BOTH, GridBagConstraints.CENTER);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        for(LevelSelectButton b : buttons){
            if(b == source){
                //Load level
                //TODO - Change how level name is read in case we want to change that in the future

                tdg.startNewGame(Integer.parseInt(b.getText()));
                return;
            }
        }
        if(source == back){
            tdf.switchToMainPanel();
        }
    }
}
