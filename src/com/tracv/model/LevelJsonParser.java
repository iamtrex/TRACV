package com.tracv.model;

import com.tracv.types.EnemyType;
import com.tracv.util.Constants;
import com.tracv.util.TextFileReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class LevelJsonParser {

    public LevelJsonParser(){
        spawnTimer = new HashMap<>();
        spawnQueue = new LinkedList<>();
    }


    public void readLevel(int level) {
        spawnTimer.clear();
        spawnQueue.clear();

        String contents = TextFileReader.readFile(Constants.LEVEL_DIR + String.valueOf(level) + ".json");
        parseJson(contents);
    }



    public String getFile() {
        return Constants.MAP_DIR + map;
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

    private String name, map;
    private Map<List<EnemyType>, Integer> spawnTimer;
    private Queue<List<EnemyType>> spawnQueue;

    public Map<List<EnemyType>,Integer> getSpawnTime() {
        return spawnTimer;
    }

    public Queue<List<EnemyType>> getSpawnQueue() {
        return spawnQueue;
    }

}
