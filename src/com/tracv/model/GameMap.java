package com.tracv.model;


import com.tracv.gamecomponents.*;
import com.tracv.types.TerrainType;
import com.tracv.util.Constants;
import com.tracv.util.TerrainParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a mutable Map of the game which contains terrain and
 * game components
 * Rep Invariant:
 */
public class GameMap {
    private List<GameComponent> gameComponents;

    private Terrain[][] terrains;
    private Terrain start, destination;


    private Double blockSize;
    //private Base base;

    //sublists of gameComponents.
    private List<Tower> towers;
    private List<Enemy> enemies;
    private List<Projectile> projectiles;
    private List<Base> bases;


    private PathBuilder pathBuilder;


    public void reset() {
        long startT = System.nanoTime();



        this.gameComponents = new ArrayList<>();
        towers = new ArrayList<>();
        enemies = new ArrayList<>();
        projectiles = new ArrayList<>();
        bases = new ArrayList<>();





        long endT = System.nanoTime();

        System.out.println("Time Taken to load map " + (endT-startT)/1000000.0 + " ms");
    }


    public void loadLevel(String levelTerrainFile) {
        TerrainType[][] terrainTypes = TerrainParser.parseTerrainFile(levelTerrainFile);
        buildTerrain(terrainTypes);
        pathBuilder = new PathBuilder(terrains);
        start = terrains[0][0];
        destination = terrains[terrains.length-1][0];
        this.blockSize = Constants.DEFAULT_BLOCK_SIZE;



    }

    /**
     * Constructor for GameMap, makes a new GameMap with the input terrainTypes containing
     * no GameComponents
     *
     */
    public GameMap () {
        reset();
    }

    private void buildTerrain(TerrainType[][] terrainTypes) {

        int width = Constants.GAME_DIMENSION.width / terrainTypes[0].length;
        int height = Constants.GAME_DIMENSION.height / terrainTypes.length;
        terrains = new Terrain[terrainTypes.length][terrainTypes[0].length];
        for(int i=0; i<terrainTypes.length; i++){
            for(int j=0; j<terrainTypes[i].length; j++){
                //terrains.add(new Terrain(terrainTypes[i][j], j, i));
                terrains[i][j] = new Terrain(terrainTypes[i][j], j, i, width, height);

                if(terrainTypes[i][j] == TerrainType.NEXUS){
                    //TODO do not use default base...
                    Base base = new Base(1000, terrains[i][j], null);
                    bases.add(base);
                    gameComponents.add(base);
                }
            }
        }


    }

    /**
     * Getter to return a list of the GameComponents in the GameMap
     * @return a list of GameComponents within the GameMap
     */
    public List<GameComponent> getGameComponents() {
        return gameComponents;
    }

    /**
     * Adds the specific component to the GameMap
     * @param gc the component to add
     * @return true if the add operation was successful
     */
    public boolean addComponent(GameComponent gc) {
        if(gc instanceof Projectile){
            projectiles.add((Projectile)gc);
        }else if(gc instanceof Enemy){
            Enemy e = (Enemy) gc;
            e.setPath(pathBuilder.generatePath(start, destination));
            enemies.add(e);
        }else if(gc instanceof Tower){
            towers.add((Tower)gc);
        }else if(gc instanceof Base){
            bases.add((Base)gc);
        }else{
            System.out.println("Wrong component type");
        }
        return gameComponents.add(gc);
    }

    /**
     * Removes teh specific component from the GameMap
     * @param gc the component to remove
     * @return true if the remove operation was successful
     */
    public boolean removeComponent(GameComponent gc) {
        if(gc instanceof Projectile){
            projectiles.remove(gc);
        }else if(gc instanceof Enemy){
            enemies.remove(gc);
        }else if(gc instanceof Tower){
            towers.remove(gc);
        }else if(gc instanceof Base){
            bases.remove(gc);
        }else{
            System.out.println("Wrong component type");
        }
        return gameComponents.remove(gc);
    }

    /**
     * Getter for the current terrainTypes of the GameMap
     * @return a 2D array representing the terrainTypes
     */
    public Terrain[][] getTerrains() {
        return terrains;
    }


    public Double getBlockSize(){
        return blockSize;
    }

    public void setBlockSize(Double blockSize){
        this.blockSize = blockSize;

    }


    //TODO Fix this implementation
    public Base getBase() {
        return bases.get(0);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public List<Tower> getTowers() {
        return towers;
    }

    public void addEnemies(List<Enemy> spawn) {
        //TODO FIX- TOO TIRED TO DO SO RN
        SpawnThread st = new SpawnThread(spawn);
        st.start();

    }


    public class SpawnThread extends Thread{
        private List<Enemy> spawn;
        public SpawnThread(List<Enemy> spawn){
            super();
            this.spawn = spawn;
            r = new Random();
        }

        private Random r;

        @Override
        public void run(){
            for(Enemy e : spawn){
                e.setPath(pathBuilder.generatePath(start, destination));
                enemies.add(e);
                gameComponents.add(e);

                try{
                    int randomFactor = r.nextInt(10);
                    Thread.sleep(100*randomFactor); //0.1 sec delay between spawns
                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}
