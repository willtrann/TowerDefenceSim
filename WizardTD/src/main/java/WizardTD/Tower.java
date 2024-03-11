package WizardTD;

import processing.core.PImage;
import java.io.*;
import java.util.*;
import java.lang.*;

/**
 * The type Tower.
 */
public class Tower {
    /**
     * The Coordinate.
     */
    protected Coordinate coordinate;
    /**
     * The Initial tower range.
     */
    protected int initial_tower_range;
    /**
     * The Initial tower firing speed.
     */
    protected double initial_tower_firing_speed;
    /**
     * The Initial tower damage.
     */
    protected double initial_tower_damage;
    /**
     * The Tower range.
     */
    protected int tower_range;
    /**
     * The Tower firing speed.
     */
    protected double tower_firing_speed;
    /**
     * The Tower damage.
     */
    protected double tower_damage;
    /**
     * The Target.
     */
    protected Monster target = null;
    /**
     * The Fireball.
     */
    protected Fireball fireball = null;
    /**
     * The Towerimgs.
     */
    protected List<PImage> towerimgs;
    /**
     * The Type.
     */
    protected PImage type;
    /**
     * The Damage level.
     */
    public int damage_level,
    /**
     * The Speed level.
     */
    speed_level,
    /**
     * The Range level.
     */
    range_level = 0;
    /**
     * The Cost dmg.
     */
    public int cost_dmg = 10;
    /**
     * The Cost speed.
     */
    public int cost_speed = 10;
    /**
     * The Cost range.
     */
    public int cost_range = 10;
    /**
     * The Fireball img.
     */
    protected PImage fireballImg;
    /**
     * The Tower frame count.
     */
    public int towerFrameCount = 0;

    /**
     * Instantiates a new Tower.
     *
     * @param towerimgs                  the towerimgs
     * @param coordinate                 the coordinate
     * @param initial_tower_range        the initial tower range
     * @param initial_tower_firing_speed the initial tower firing speed
     * @param initial_tower_damage       the initial tower damage
     * @param fireballImg                the fireball img
     */
    public Tower(List<PImage> towerimgs, Coordinate coordinate, int initial_tower_range, double initial_tower_firing_speed, double initial_tower_damage, PImage fireballImg){
        this.towerimgs = towerimgs;
        this.coordinate = coordinate;
        this.initial_tower_range = tower_range = initial_tower_range;
        this.initial_tower_firing_speed = tower_firing_speed= initial_tower_firing_speed;
        this.initial_tower_damage = tower_damage = initial_tower_damage;
        this.type = towerimgs.get(0);
        this.fireballImg = fireballImg;
    }

    /**
     * Get coordinate coordinate.
     *
     * @return the coordinate
     */
    public Coordinate getCoordinate(){ return this.coordinate; }

    /**
     * Get tower range int.
     *
     * @return the int
     */
    public int getTower_range(){
        return tower_range;
    }

    /**
     * Get tower damage double.
     *
     * @return the double
     */
    public double getTower_damage(){
        return tower_damage;
    }

    /**
     * Get tower firing speed double.
     *
     * @return the double
     */
    public double getTower_firing_speed(){
        return tower_firing_speed;
    }

    /**
     * Gets fireball.
     *
     * @return the fireball
     */
    public Fireball getFireball() {
        return fireball;
    }

    /**
     * Get type p image.
     *
     * @return the p image
     */
    public PImage getType(){
        return this.type;
    }

    /**
     * Upgrade damage.
     */
    public void upgradeDamage(){
        tower_damage += initial_tower_damage/2;
        damage_level++;
        cost_dmg += 10;
    }

    /**
     * Upgrade range.
     */
    public void upgradeRange(){
        tower_range++;
        range_level++;
        cost_range += 10;
    }

    /**
     * Upgrade speed.
     */
    public void upgradeSpeed(){
        tower_firing_speed += 0.5;
        speed_level++;
        cost_speed += 10;
    }

    /**
     * Tower image.
     */
    public void towerImage(){
        if(damage_level == 1 && range_level == 1 && speed_level == 1){
            type = towerimgs.get(1); // set tower image to level 1 everything
        }
        else if(damage_level >= 2 && range_level >= 2 && speed_level >= 2){
            type = towerimgs.get(2); // set tower image to level 2 everything
        }
    }

    /**
     * Within tower range boolean.
     *
     * @param distance the distance
     * @return the boolean
     */
    protected boolean withinTowerRange(double distance){
        return distance <= tower_range;
    }

    /**
     * Shoot fireball.
     *
     * @param monsters the monsters
     * @return the fireball
     */
    public Fireball shootFireball(List<Monster> monsters) {
        double monsterDistance = 0.0;

        if (target != null) { //if target exists get distance to check
            monsterDistance = distance(coordinate.x, target.getX_Pos(), coordinate.y, target.getY_Pos());
        }

        if (target != null && ( !(withinTowerRange(monsterDistance)) || target.isDead())) {
            target = null; // if target exists and is dead or out of tower's range, set back to null
        }

        if(target == null) {
            double closestDistance = Double.MAX_VALUE;
            for (Monster monster : monsters) {
                double x = coordinate.x;
                double y = coordinate.y;
                double distance = distance(x, monster.getX_Pos(), y, monster.getY_Pos());
                if ((distance < closestDistance) && withinTowerRange(distance)) {
                    target = monster; // find closest target to tower's range to target
                    closestDistance = distance;
                }
            }
        }

        if (target != null && fireball == null) {
            return new Fireball(fireballImg, tower_damage, target, coordinate.x, coordinate.y, this); // if target is a monster and no fireball, make a new fireball
        }
        return null;
    }

    /**
     * Distance double.
     *
     * @param x1 the x 1
     * @param x2 the x 2
     * @param y1 the y 1
     * @param y2 the y 2
     * @return the double
     */
    protected double distance(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow((x2-x1),2) + Math.pow((y2-y1),2));
    }

}
