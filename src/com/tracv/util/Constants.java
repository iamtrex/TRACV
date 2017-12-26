package com.tracv.util;

import java.awt.*;

public class Constants {

    public static final Double HUD_VERTICAL_SCALE = 0.125;
    public static final Double GAME_VERTICAL_SCALE = 1-HUD_VERTICAL_SCALE;

    public static final Dimension FRAME_DEFAULT_SIZE = new Dimension(1500, 750);

    public static final Dimension HUD_DIMENSION = new Dimension(FRAME_DEFAULT_SIZE.width,
            (int)(FRAME_DEFAULT_SIZE.height * HUD_VERTICAL_SCALE));

    public static final Dimension GAME_DIMENSION = new Dimension(FRAME_DEFAULT_SIZE.width,
            (int)(FRAME_DEFAULT_SIZE.height * GAME_VERTICAL_SCALE)); ;


    public static final Dimension HUD_TOWER_ICON_SIZE = new Dimension(35, 35);
    public static final Dimension ICON_SIZE = new Dimension(35, 35);

    public static final String GAME_NAME = "TRACV Tower Defense";
    public static final String RES_DIR = "/res/";

    public static final Font DEFAULT_FONT = new Font("Calibri", Font.BOLD, 24);

}
