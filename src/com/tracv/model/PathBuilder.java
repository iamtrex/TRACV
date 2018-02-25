package com.tracv.model;

import com.tracv.gamecomponents.Terrain;
import com.tracv.types.TerrainType;

import java.util.*;

/**
 * Given terrain, can be used to create paths between certain nodes.
 */
public class PathBuilder {
    private Terrain[][] terrains;

    public PathBuilder(Terrain[][] terrains) {
        this.terrains = terrains;

    }

    /**
     * //TODO alternatively generate a set of paths when the map is loaded, then based off where the enemy
     * //spawns, give them a particular path.
     *
     * @param start       - the start location
     * @param destination - The end location.
     * @return
     */
    public List<Terrain> generatePath(Terrain start, Terrain destination) {
        //TODO Remove debugging lines

        Set<Terrain> open = new HashSet<>();
        Set<Terrain> closed = new HashSet<>();
        Map<Terrain, Terrain> cameFrom = new HashMap<>();
        Map<Terrain, Integer> gScore = new HashMap<>();
        Map<Terrain, Integer> fScore = new HashMap<>();

        for (int i = 0; i < terrains.length; i++) {
            for (int j = 0; j < terrains[i].length; j++) {
                Terrain t = terrains[i][j];
                gScore.put(t, Integer.MAX_VALUE);
                fScore.put(t, Integer.MAX_VALUE);
            }
        }

        gScore.put(start, 0);
        fScore.put(start, estimateDistanceToEnd(start, destination));
        open.add(start);

        Terrain curr = start;

        while (open.size() != 0) {
            curr = getLowestInSet(gScore.get(curr), curr, open, destination);

            if (curr == destination) {
                //System.out.println("Proper Path found");
                return generatePath(cameFrom, start, destination);
            }

            Set<Terrain> neigh = getNearTerrain(curr.getX(), curr.getY());
            //System.out.println("Neighs " + neigh.size());
            for (Terrain tmp : neigh) {
                if (!open.contains(tmp) && !closed.contains(tmp)) {
                    //System.out.println("Checking neigh " + tmp.getX() + " " + tmp.getY());
                    double tmpG = gScore.get(curr) + getDistanceBetween(curr, tmp);

                    if (tmpG >= gScore.get(tmp)) {
                        continue;
                    }

                    int cost = (int) tmpG + estimateDistanceToEnd(tmp, destination);
                    cameFrom.put(tmp, curr);
                    gScore.put(tmp, new Integer((int) tmpG));
                    fScore.put(tmp, cost);
                    open.add(tmp);

                }
            }

            open.remove(curr);
            closed.add(curr);

        }

        System.out.println("No path");
        return null;
    }

    private List<Terrain> generatePath(Map<Terrain, Terrain> cameFrom, Terrain start, Terrain destination) {
        Stack<Terrain> stack = new Stack<>();

        Terrain curr = destination;
        while (curr != start) {
            stack.push(curr);
            curr = cameFrom.get(curr);
        }

        List<Terrain> path = new ArrayList<>();
        while (!stack.empty()) {
            path.add(stack.pop());
        }
        return path;
    }

    private Integer getDistanceBetween(Terrain n, Terrain n2) {
        return new Integer(
                (int) Math.round(Math.sqrt(
                        Math.pow(Math.abs(n.getX() - n2.getX()), 2) +
                                Math.pow(Math.abs(n.getY() - n2.getY()), 2))));
    }


    private Integer estimateDistanceToEnd(Terrain n, Terrain destination) {
        return new Integer(
                (int) Math.round(Math.sqrt(
                        Math.pow(Math.abs(n.getX() - destination.getX()), 2) +
                                Math.pow(Math.abs(n.getY() - destination.getY()), 2))));
    }

    private Terrain getLowestInSet(Integer curG, Terrain curr, Set<Terrain> toSearch, Terrain destination) {
        double min = Integer.MAX_VALUE;
        Terrain lowest = null;
        for (Terrain tt : toSearch) {
            double h = estimateDistanceToEnd(tt, destination);
            double g = getDistanceBetween(curr, tt) + curG;

            if (g + h < min) {
                min = g + h;
                lowest = tt;
            }
        }
        return lowest;
    }


    //Adds up to the nearest 8 terrainTypes.
    private Set<Terrain> getNearTerrain(int x, int y) {
        int ySize = terrains.length;
        int xSize = terrains[0].length;

        Set<Terrain> queue = new HashSet<>();

        //Removed slashes are for diagonal movement..
        if (x > 0) {
            addIf(queue, terrains[y][x - 1]);
            if (y > 0) {
                // addIf(queue, terrains[y-1][x-1]);
            }

            if (y < ySize - 1) { //Assume rectangular map...
                //addIf(queue, terrains[y+1][x-1]);
            }
        }

        if (y > 0) {
            addIf(queue, terrains[y - 1][x]);
            if (x < xSize - 1) {
                //addIf(queue, terrains[y-1][x+1]);
            }
        }

        if (y < ySize - 1) {
            addIf(queue, terrains[y + 1][x]);
            if (x < xSize - 1) {
                //addIf(queue, terrains[y+1][x+1]);
            }
        }
        if (x < xSize - 1) {
            addIf(queue, terrains[y][x + 1]);
        }

        return queue;


    }

    private static void addIf(Set<Terrain> queue, Terrain terrain) {
        if (terrain.getType() == TerrainType.MOVEABLE || terrain.getType() == TerrainType.NEXUS) {
            queue.add(terrain);
        }
    }
}
