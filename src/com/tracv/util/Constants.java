package com.tracv.util;

import java.awt.*;

/**
 * In future can make this read from a settings.ini
 */
public class Constants {


    public static final Double HUD_VERTICAL_SCALE = 0.125;
    public static final Double GAME_VERTICAL_SCALE = 1-HUD_VERTICAL_SCALE;

    public static final Dimension FRAME_DEFAULT_SIZE = new Dimension(1500, 750);

    public static final Dimension HUD_DIMENSION = new Dimension(FRAME_DEFAULT_SIZE.width,
            (int)(FRAME_DEFAULT_SIZE.height * HUD_VERTICAL_SCALE));

    public static final Dimension GAME_DIMENSION = new Dimension(FRAME_DEFAULT_SIZE.width,
            (int)(FRAME_DEFAULT_SIZE.height * GAME_VERTICAL_SCALE)); ;


    public static final Dimension HUD_TOWER_ICON_SIZE = new Dimension(35, 35+10+2); //Add HUD Font + spacing
    public static final Dimension ICON_SIZE = new Dimension(35, 35);

    public static final String GAME_NAME = "TRACV Tower Defense";
    public static final String TOWER_ICON_DIR = "/TowerIcons/";
    public static final String TOWER_ICON_FILETYPE = ".png";

    public static final Font DEFAULT_FONT = new Font("Calibri", Font.BOLD, 24);
    public static final Font HUD_TOWER_GOLD_FONT = new Font("Calibri", Font.PLAIN, 10);

    public static final Color BUTTON_SELECTED_COLOR = new Color(150, 150, 150, 150);
    public static final Color BUTTON_UNSELECTED_COLOR = new Color(1, 1, 1, 1.0f);


    public static final int HUD_BUTTONS_PER_ROW = 5;
}
