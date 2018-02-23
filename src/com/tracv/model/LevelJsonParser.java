package com.tracv.model;

import com.tracv.types.EnemyType;
import com.tracv.util.Constants;
import com.tracv.util.TerrainParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

public class LevelJsonParser {

    public LevelJsonParser(){
        spawnTimer = new HashMap<>();
        spawnQueue = new LinkedList<>();

    }


    public void readLevel(int level) {
        String contents = readFile(level);
        parseJson(contents);
    }

    private String readFile(int level) {

        String response = "";
        String fileName = Constants.LEVEL_DIR + String.valueOf(level) + ".json";

        try {
            InputStream in = getClass().getResourceAsStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = br.readLine()) != null){
                response += line;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Shouldn't happen");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Response " + response);
        return response;

    }


    public String getFile() {
        return map;
    }


    private void parseJson(String contents){
        try {
            JSONObject jo = new JSONObject(contents);
            JSONObject sub = jo.getJSONObject("level");

            this.name = sub.getString("name");
            this.map = sub.getString("map");

            JSONArray ja = sub.getJSONArray("enemy-spawn");
            for(int i=0; i<ja.length(); i++){
                JSONObject wave = ja.getJSONObject(i);
                List<EnemyType> waveSpawn = parseEnemyType(wave.getString("spawn"));

                spawnQueue.add(waveSpawn);
                spawnTimer.put(waveSpawn, wave.getInt("Duration"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private List<EnemyType> parseEnemyType(String spawn) {
        List<EnemyType> wave = new LinkedList<>();
        String[] names = spawn.split(",");
        for(String s : names){
            for(EnemyType e : EnemyType.values()){
                if(e.getShorthand().equalsIgnoreCase(s)) {
                    wave.add(e);
                }
            }
        }
        return wave;
    }

    String name, map;
    private Map<List<EnemyType>, Integer> spawnTimer;
    private Queue<List<EnemyType>> spawnQueue;

    public Map<List<EnemyType>,Integer> getSpawnTime() {
        return spawnTimer;
    }

    public Queue<List<EnemyType>> getSpawnQueue() {
        return spawnQueue;
    }

}
