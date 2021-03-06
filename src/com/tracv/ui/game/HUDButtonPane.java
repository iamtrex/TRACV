package com.tracv.ui.game;

import com.tracv.controller.GameProcess;
import com.tracv.swing.HUDTowerButton;
import com.tracv.swing.Label;
import com.tracv.swing.Pane;
import com.tracv.types.TowerType;
import com.tracv.util.Comp;
import com.tracv.util.Constants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Holds buildable item buttons.
 */
public class HUDButtonPane extends Pane
                                implements ActionListener{
    private HUDPane hudPane;
    public static final String TOWER_CHANGED = "Tower Changed"; //TODO move these to constants?


    private List<HUDTowerButton> buttons;

    private Pane buttonsPane;
    private Label lTower;

    public HUDButtonPane(HUDPane hudPane, GameProcess gs) {
        this.hudPane = hudPane;
        buttonsPane = new Pane();

        lTower = new Label("Towers", Label.MEDIUM, Label.CENTER, Label.INVISIBLE);


        Comp.add(lTower, this,  0, 0, 1, 1, 1, 0,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 10, 0, 0, 0);
        Comp.add(buttonsPane, this, 0, 1, 1, 1, 1, 1,
                GridBagConstraints.BOTH, GridBagConstraints.CENTER);

        buttons = new ArrayList<>();
        makeAndAddButtons();

        this.setPreferredSize(Constants.HUD_BUTTON_SIZE);
        this.setMinimumSize(Constants.HUD_BUTTON_SIZE);
    }

    /**
     * Makes and adds all the buttons to the UI based off number per row in Constants.
     * @see Constants : HUD_BUTTONS_PER_ROW for the number per row.
     */
    private void makeAndAddButtons(){
        //TODO read buttons from some sort of file or lazily just make them staticlaly in some other file and copy them over

        TowerType[] basicTowers = TowerType.BASIC_TOWERS;
        for(TowerType tt : basicTowers){
            HUDTowerButton temp = new HUDTowerButton(tt);
            buttons.add(temp);
        }


        int x = Constants.HUD_BUTTONS_PER_ROW;
        for(int i=0; i<buttons.size(); i++){
            Insets insets = getInsetsFor(i);
            Comp.add(buttons.get(i), buttonsPane,  i%x, i/x, 1, 1, 1, 1,
                       GridBagConstraints.NONE, GridBagConstraints.CENTER, insets);
            buttons.get(i).addActionListener(this);
        }

    }

    /**
     * Makes an appropriate Insets spacing for the current position i based off of the total number of buttons and the
     *      number of buttons per row
     *
     * @param i - the position to calculate the insets for.
     * @return - Inset containing the spacing.
     */
    private Insets getInsetsFor(int i){
        int x = Constants.HUD_BUTTONS_PER_ROW;
        int max = (buttons.size()-1)/x;

        int top = (i/x == 0) ? 2 : 1;
        int bottom = (i/x == max) ? 2 : 1;

        int left = (i%x == 0) ? 2 : 1;
        int right = (i%x == x-1) ? 2 : 1;

        //Exception for bottom right tower...
        right = (i == buttons.size()-1) ? 2 : right;
        return new Insets(top, left, bottom, right);
    }

    // TODO - May have issue with deselecting if another button is pressed.
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        for(HUDTowerButton b : buttons){
            if(b == source){
                if(!b.isSelected()) {
                    firePropertyChange(TOWER_CHANGED, null, b.getType());
                    b.setSelected(true);
                }else{
                    firePropertyChange(TOWER_CHANGED, null, null);
                    b.setSelected(false);
                }
            }else{
                if(b.isSelected()) {
                    b.setSelected(false);
                }

            }
        }
    }
}
