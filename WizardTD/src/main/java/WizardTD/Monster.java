package WizardTD;
import processing.core.PImage;

import java.util.*;
import java.io.*;

/**
 * The type Monster.
 */
public class Monster {
    /**
     * The Type.
     */
    private PImage type;
    /**
     * The Hp.
     */
    private double hp;
    /**
     * The Initial hp.
     */
    public double initial_hp;
    /**
     * The Speed.
     */
    private float speed;
    /**
     * The Armour.
     */
    private double armour;
    /**
     * The Mana gained on kill.
     */
    private int mana_gained_on_kill;
    /**
     * The X pos.
     */
    private float X_Pos;
    /**
     * The Y pos.
     */
    private float Y_Pos;
    /**
     * If monster has first spawned.
     */
    private boolean firstSpawned;

    /**
     * The Monster imgs.
     */
    private List<PImage> monster_imgs;
    /**
     * The Visited.
     */
    private boolean[][] visited;
    /**
     * The Path.
     */
    private List<Coordinate> path;
    /**
     * The Initial path.
     */
    private List<Coordinate> initial_path;
    /**
     * The Is dying.
     */
    private boolean isDying = false;

    /**
     * The Slowed.
     */
    public boolean slowed = false;

    /**
     * The Monster frame count.
     */
    public int MonsterFrameCount = 0;

    /**
     * Instantiates a new Monster.
     *
     * @param monster_imgs        the monster imgs
     * @param hp                  the hp
     * @param speed               the speed
     * @param armour              the armour
     * @param mana_gained_on_kill the mana gained on kill
     */
    public Monster(List<PImage> monster_imgs, double hp, float speed, double armour, int mana_gained_on_kill){
        this.monster_imgs = monster_imgs;
        if(monster_imgs.isEmpty()) {
            this.type = null;
        }
        else {
            this.type = monster_imgs.get(0);
        }
        this.hp = this.initial_hp = hp;
        this.speed = speed;
        this.armour = armour;
        this.mana_gained_on_kill = mana_gained_on_kill;
        this.firstSpawned = true;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public PImage getType() {
        return type;
    }

    /**
     * Gets hp.
     *
     * @return the hp
     */
    public double getHp() {
        return hp;
    }

    /**
     * Gets armour.
     *
     * @return the armour
     */
    public double getArmour() {
        return armour;
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Gets mana gained on kill.
     *
     * @return the mana gained on kill
     */
    public int getMana_gained_on_kill() {
        return mana_gained_on_kill;
    }

    /**
     * Get x pos float.
     *
     * @return the float
     */
    public float getX_Pos(){
        return this.X_Pos;
    }

    /**
     * Get y pos float.
     *
     * @return the float
     */
    public float getY_Pos(){
        return this.Y_Pos;
    }

    /**
     * Dying animation, returns determines if no more images to animate.
     *
     * @return the boolean
     */
    public boolean dyingAnimation(){
        if(monster_imgs.isEmpty()){
            return true; // if fully dead
        }
        else{
            monster_imgs.remove(0);
            if (monster_imgs.isEmpty()) {
                return true; // if fully dead
            } else {
                type = monster_imgs.get(0); // get new image of monster death animation
            }
        }
        return false;
    }

    /**
     * Update position of monster on map.
     */
    public void updatePosition() {
        if (path.isEmpty()) {
            return; // if path is empty, dont move
        }

        Coordinate target = path.get(0); // get the next target from path
        float targetX = target.x * 32 + 5; // get next coordinate monster moves to
        float targetY = target.y * 32 + 45;

        if(firstSpawned){
            X_Pos = targetX; // if first spawned, draw start position
            Y_Pos = targetY;
            firstSpawned = false;
            return;
        }

        int directionX;
        int directionY;

        if (X_Pos < targetX) {
            directionX = 1; // get direction monster should move in x or y direction
        } else {
            directionX = -1;
        }

        if (Y_Pos < targetY) {
            directionY = 1;
        } else {
            directionY = -1;
        }

        if (X_Pos != targetX) {
            X_Pos += directionX * speed; // if monster not reached target, move towards
            if ((directionX == 1 && X_Pos > targetX) || (directionX == -1 && X_Pos < targetX)) {
                X_Pos = targetX; // if monster pretty much at target, set to target
            }
        }

        else if (Y_Pos != targetY) {
            Y_Pos += directionY * speed; // if monster not reached target, move towards
            if ((directionY == 1 && Y_Pos > targetY) || (directionY == -1 && Y_Pos < targetY)) {
                Y_Pos = targetY;  // if monster pretty much at target, set to target
            }
        }

        if (X_Pos == targetX && Y_Pos == targetY) {
            removeFirstElement(); // if at x and y position of target coordinate, remove it from path list.
        }
    }

    /**
     * Reset path.
     */
    public void resetPath(){
        path = new ArrayList<>(initial_path); // reset path and set to initial entrance of path
        if(!initial_path.isEmpty()) {
            X_Pos = initial_path.get(0).x * 32 + 5;
            Y_Pos = initial_path.get(0).y * 32 + 45;
        }
    }

    /**
     * Take damage.
     *
     * @param dmg the dmg
     */
    public void takeDamage(double dmg){
        if(this.hp > 0){
            this.hp -= dmg * (1.0 - this.armour); // take damage based on armour
        }
    }

    /**
     * Get path of monster.
     *
     * @return the list
     */
    public List<Coordinate> getPath(){
        return this.path;
    }

    /**
     * Set x pos.
     *
     * @param x the x
     */
    public void setX_Pos(float x){
        this.X_Pos = x;
    }

    /**
     * Set y pos.
     *
     * @param y the y
     */
    public void setY_Pos(float y){
        this.Y_Pos = y;
    }

    /**
     * Is dead boolean.
     *
     * @return the boolean
     */
    public boolean isDead(){
        if (this.hp <= 0){
            isDying = true;
            return true; // returns if dead or not
        };
        return false;
    }

    /**
     * Slow monster.
     *
     * @param rate the rate
     */
    public void slowMonster(double rate){
        slowed = true;
        speed *= (float) (1-rate); // slows monster based on rate of slow
    }

    /**
     * Set path.
     *
     * @param entrance the entrance
     * @param f        the f
     */
    public void setPath(Coordinate entrance, File f){
        try {
            Scanner scan = new Scanner(f);
            ArrayList<String> map_points = new ArrayList<>();
            while (scan.hasNextLine()) {
                map_points.add(scan.nextLine());
            }

            int height = map_points.size();

            int maxWidth = 0;
            for (String line : map_points) {
                if (line.length() > maxWidth) {
                    maxWidth = line.length();
                }
            }
            // get map

            visited = new boolean[maxWidth][height]; // boolean of visited cells true or false

            initial_path = new ArrayList<>();
            dfs( (int) entrance.x, (int) entrance.y, map_points); // implement depth first search algorithm to find end
            path = new ArrayList<>(initial_path); // path = initial path;

        } catch(FileNotFoundException e){
            System.out.println("File not found!");
        }


    }

    /**
     * Dfs boolean through recursion.
     *
     * @param x          the x
     * @param y          the y
     * @param map_points the map points
     * @return the boolean value of path
     */
    public boolean dfs(int x, int y, List<String> map_points) {
        if(x < 0 || y < 0 || y >= map_points.size() || x >= map_points.get(0).length()) {
            return false; // Out of the map
        }

        char cell = map_points.get(y).charAt(x); // cell

        // Check if the cell is not 'X', or it's already visited
        if((cell != 'X' && cell != 'W') || visited[x][y]) {
            return false; // Not a valid cell to traverse or already visited, return false.
        }

        // Mark the cell as visited
        visited[x][y] = true;

        // Add the current cell to the path
        initial_path.add(new Coordinate(x, y));

        // Check if the current cell is 'W'
        if(cell == 'W') {
            return true; // 'W' found, return true.
        }

        // Perform the DFS in all valid directions and check if 'W' is found in any direction
        if(dfs(x + 1, y, map_points) // Right
                || dfs(x - 1, y, map_points) // Left
                || dfs(x, y + 1, map_points) // Down
                || dfs(x, y - 1, map_points)) { // Up
            return true; // 'W' found in one of the directions, return true.
        }

        // If 'W' is not found in any direction from the current cell, remove the current cell from the path and backtrack
        initial_path.remove(initial_path.size() - 1);

        return false; // Return false as 'W' is not found in any direction from the current cell.
    }

    /**
     * Remove first element of path.
     */
    public void removeFirstElement(){
        this.path.remove(0);
    }


}