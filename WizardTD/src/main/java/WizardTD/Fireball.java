package WizardTD;
import processing.core.PApplet;
import processing.core.PImage;
import static processing.core.PApplet.dist;

/**
 * The type Fireball.
 */
public class Fireball {
    /**
     * The Type.
     */
    private PImage type;
    /**
     * The X.
     */
    private float x, /**
     * The Y.
     */
    y;
    /**
     * The Target.
     */
    private Monster target;
    /**
     * The Speed.
     */
    private final int speed = 5;
    /**
     * The Dmg.
     */
    private double dmg;
    /**
     * If fireball damaged target.
     */
    public boolean hasDamaged = false;
    /**
     * The Parent tower.
     */
    private Tower parentTower;

    /**
     * Instantiates a new Fireball.
     *
     * @param type        the type
     * @param dmg         the dmg
     * @param target      the target
     * @param x           the x
     * @param y           the y
     * @param parentTower the parent tower
     */
    public Fireball(PImage type, double dmg, Monster target, float x, float y, Tower parentTower){
        this.type = type;
        this.dmg =dmg;
        this.target = target;
        this.x = x;
        this.y = y;
        this.parentTower = parentTower;
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
     * Gets parent tower.
     *
     * @return the parent tower
     */
    public Tower getParentTower() {
        return parentTower;
    }

    /**
     * Gets dmg.
     *
     * @return the dmg
     */
    public double getDmg() {
        return this.dmg;
    }

    /**
     * Get target monster.
     *
     * @return the monster
     */
    public Monster getTarget(){
        return this.target;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public float getX() {
        return this.x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public float getY() {
        return this.y;
    }

    /**
     * Move fireball towards target.
     */
    public void moveFireball(){
        double dx = target.getX_Pos() - this.x; //get x and y distance between fireball and target
        double dy = target.getY_Pos() - this.y;

        double magnitude = Math.sqrt(dx * dx + dy * dy);
        double dirX = dx / magnitude;
        double dirY = dy / magnitude; // get direction of each x and y

        double moveX = dirX * this.speed; // move direction so that it moves 5 pixels per second towards target
        double moveY = dirY * this.speed;

        this.x += (float) moveX; // set fireball to new location
        this.y += (float) moveY;
    }


    /**
     * Reached target boolean.
     *
     * @return the boolean
     */
    public boolean reachedTarget(){
        if (hasDamaged) {
            return false; // if already damaged target, return false
        }
        float distance = dist(this.x, this.y, target.getX_Pos(), target.getY_Pos());
        boolean atTarget = distance < 3;
        if (atTarget) {
            hasDamaged = true; // if in bounds of target fireball has now damaged target
        }
        return atTarget; // returns true or false depending if in range
    }

}
