package WizardTD;

/**
 * The type Mana.
 */
public class Mana {
    /**
     * The Mana gained per second.
     */
    private double mana_gained_per_second;
    /**
     * The Mana.
     */
    private int mana;
    /**
     * The Mana cap.
     */
    private int mana_cap;
    /**
     * The Cost increment.
     */
    public final int costincrement;
    /**
     * The Cap multipler.
     */
    public final double capmultipler;
    /**
     * The Ratemultipler.
     */
    public final double ratemultipler;
    /**
     * The Cost.
     */
    private int cost;
    /**
     * The Initial cost.
     */
    public final int initial_cost;
    /**
     * The Initial mana cap.
     */
    public final int initial_mana_cap;
    /**
     * The Initial mana gained per second.
     */
    public final double initial_mana_gained_per_second;
    /**
     * The Initial mana.
     */
    public final int initial_mana;
    /**
     * The Kill multiplier.
     */
    public double killMultiplier = 1;

    /**
     * Instantiates a new Mana.
     *
     * @param cost                           the cost
     * @param initial_mana_gained_per_second the initial mana gained per second
     * @param initial_mana                   the initial mana
     * @param initial_mana_cap               the initial mana cap
     * @param costincrement                  the costincrement
     * @param capmultiplier                  the capmultiplier
     * @param ratemultipler                  the ratemultipler
     */
    public Mana(int cost, double initial_mana_gained_per_second, int initial_mana, int initial_mana_cap, int costincrement, double capmultiplier, double ratemultipler){
        this.cost = this.initial_cost = cost;
        this.mana = this.initial_mana = initial_mana;
        this.mana_cap = this.initial_mana_cap= initial_mana_cap;
        this.mana_gained_per_second = this.initial_mana_gained_per_second = initial_mana_gained_per_second;
        this.costincrement = costincrement;
        this.capmultipler = capmultiplier;
        this.ratemultipler = ratemultipler;

    }

    /**
     * Get mana gained per second double.
     *
     * @return the double
     */
    public double getMana_gained_per_second(){
        return this.mana_gained_per_second;
    }

    /**
     * Get mana int.
     *
     * @return the int
     */
    public int getMana(){
        return this.mana;
    }

    /**
     * Get mana cap int.
     *
     * @return the int
     */
    public int getMana_cap(){
        return this.mana_cap;
    }

    /**
     * Get cost int.
     *
     * @return the int
     */
    public int getCost(){
        return this.cost;
    }

    /**
     * Reset mana.
     */
    public void resetMana(){
        mana = initial_mana;  // reset mana to initial state
        cost = initial_cost;
        mana_cap = initial_mana_cap;
        mana_gained_per_second = initial_mana_gained_per_second;
        killMultiplier = 1;
    }

    /**
     * Mana upgrade.
     */
    public void manaUpgrade(){
        if(mana - cost > 0) {
            decreaseMana(cost); // upgrade mana attributes
            cost += costincrement;
            mana_cap = (int) (mana_cap * capmultipler);
            mana_gained_per_second *= ratemultipler;
            killMultiplier += 0.1;
        }
    }

    /**
     * Increase mana.
     */
    public void increaseMana(){
        if(mana + (int) mana_gained_per_second >= mana_cap){
            mana = mana_cap; // if mana increase greater than cap
        }
        else{
            mana += (int) mana_gained_per_second;
        }
    }

    /**
     * Increase mana on kill.
     *
     * @param increase the increase
     */
    public void increaseManaOnKill(int increase){
        if(mana + (int) (increase * killMultiplier) >= mana_cap){
            mana = mana_cap; // if mana increase greater than cap
        }
        else{
            mana += (int) (increase * killMultiplier) ;
        }
    }

    /**
     * Decrease mana, boolean determines if game over.
     *
     * @param dmg the dmg
     * @return the boolean
     */
    public boolean decreaseMana(int dmg){
        if(mana - dmg < 0){
            mana = 0; // if decrease mana <= 0
            return true;
        }
        else{
            mana -= dmg;
            return false;
        }
    }
}
