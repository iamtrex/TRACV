package com.tracv.util;

import com.tracv.model.Terrain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TerrainParser {


    public static Terrain[][] parseTerrainFile(String s){
        String[][] data = parseCSVFile(s);

        List<Terrain[]> terrains = new ArrayList<>();

        for(int i=0; i<data.length; i++){
            List<Terrain> tetPart = new ArrayList<>();

            for(int j=0; j<data[i].length; j++){
                for(Terrain t : Terrain.getTerrains()){
                    if(t.getType().equals(data[i][j])){
                        tetPart.add(t);
                    }
                }
            }
            terrains.add(tetPart.toArray(new Terrain[tetPart.size()]));
        }
        return terrains.toArray(new Terrain[terrains.size()][]);

    }
    private static String[][] parseCSVFile(String s){

        List<String[]> data = new ArrayList<>();

        try {
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
