package com.tracv.model;

import java.util.ArrayList;
import java.util.List;

public class LevelParser {
    public static List<String> parseLevels() {
        List<String> levelNames = new ArrayList<>();
        levelNames.add("1");
        levelNames.add("2");
        levelNames.add("3"); //TODO GOTTA FIX THIS LOL.
        levelNames.add("4");
        return levelNames;
    }
}
