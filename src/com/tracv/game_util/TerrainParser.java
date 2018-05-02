package com.tracv.game_util;

import com.tracv.types.TerrainType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TerrainParser {


    public static TerrainType[][] parseTerrainFile(String s){
        //System.out.println("Parsed TerrainType File");
        String[][] data = parseCSVFile(s);

        List<TerrainType[]> terrainTypes = new ArrayList<>();

        for(int i=0; i<data.length; i++){
            List<TerrainType> tetPart = new ArrayList<>();

            for(int j=0; j<data[i].length; j++){
                for(TerrainType t : TerrainType.getTerrains()){
                    if(t.getType().equals(data[i][j])){
                        tetPart.add(t);
                        //System.out.print(t.getType());
                        break;
                    }
                }
            }
            //System.out.println();
            terrainTypes.add(tetPart.toArray(new TerrainType[tetPart.size()]));
        }
        return terrainTypes.toArray(new TerrainType[terrainTypes.size()][]);

    }
    private static String[][] parseCSVFile(String s){

        List<String[]> data = new ArrayList<>();

        try {
            System.out.println("Loading file " + s);
            InputStream in = TerrainParser.class.getClassLoader().getResourceAsStream(s);

            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = br.readLine()) != null){
                String[] temp = line.split(",");


                data.add(temp);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Shouldn't happen");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data.toArray(new String[data.size()][]);
    }
}
