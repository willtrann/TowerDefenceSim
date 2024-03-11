package WizardTD;
import processing.core.PImage;
import java.io.*;
import java.util.*;
import java.lang.*;

/**
 * The type Slow tower.
 */
public class SlowTower extends Tower{
    /**
     * The Slow rate.
     */
    public double slowRate;

    /**
     * Instantiates a new Slow tower.
     *
     * @param towerimgs                  the towerimgs
     * @param coordinate                 the coordinate
     * @param initial_tower_range        the initial tower range
     * @param initial_tower_firing_speed the initial tower firing speed
     * @param initial_tower_damage       the initial tower damage
     * @param fireballImg                the fireball img
     * @param slowRate                   the slow rate
     */
    public SlowTower(List<PImage> towerimgs, Coordinate coordinate, int initial_tower_range, double initial_tower_firing_speed, int initial_tower_damage, PImage fireballImg, double slowRate) {
        super(towerimgs, coordinate, initial_tower_range, initial_tower_firing_speed, initial_tower_damage, fireballImg);
        this.slowRate = slowRate;
    }

    /**
     * Shoot slowball.
     *
     * @param monsters the monsters
     * @return the fireball of parentTower SlowTower
     */
    public Fireball shootFireball(List<Monster> monsters) {
        double monsterDistance = 0.0;

        target = null; // find new target since only can slow monster once

        double closestDistance = Double.MAX_VALUE;
        for (Monster monster : monsters) {
            double x = coordinate.x;
            double y = coordinate.y;
            double distance = distance(x, monster.getX_Pos(), y, monster.getY_Pos());
            if ((distance < closestDistance) && withinTowerRange(distance)) {
                target = monster;
                if(target.slowed){
                    target = null; // find closest unslowed monster to tower
                    closestDistance = Double.MAX_VALUE;
                }
                else{
                    closestDistance = distance;
                }
            }
        }

        if (target != null && fireball == null) {
            return new Fireball(fireballImg, tower_damage, target, coordinate.x, coordinate.y, this); // fire fireball of slow attributes to be added in App.java to activefireballs
        }
        return null;
    }


    /**
     * Gets slow rate.
     *
     * @return the slow rate
     */
    public double getSlowRate() {
        return slowRate;
    }
}
