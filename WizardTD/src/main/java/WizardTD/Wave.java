package WizardTD;
import java.util.*;

/**
 * The type Wave.
 */
public class Wave {
    /**
     * The Pre wave pause.
     */
    public final int pre_wave_pause;
    /**
     * The Duration.
     */
    public final int duration;
    /**
     * The Monsters.
     */
    public final List<Monster> monsters;
    /**
     * The Size of list monsters.
     */
    public final int sizeMonsters;

    /**
     * Instantiates a new Wave.
     *
     * @param pre_wave_pause the pre wave pause
     * @param duration       the duration
     * @param monsters       the monsters
     */
    public Wave(int pre_wave_pause, int duration, List<Monster> monsters){
        this.pre_wave_pause = pre_wave_pause;
        this.duration = duration;
        this.monsters = monsters;
        this.sizeMonsters = monsters.size();
    }

    /**
     * Get size monsters int.
     *
     * @return the int
     */
    public int getSizeMonsters(){
        return this.sizeMonsters;
    }

    /**
     * Get pre wave pause int.
     *
     * @return the int
     */
    public int getPre_wave_pause(){
        return this.pre_wave_pause;
    }

    /**
     * Get duration int.
     *
     * @return the int
     */
    public int getDuration(){
        return this.duration;
    }

    /**
     * Get monsters list.
     *
     * @return the list of Monsters
     */
    public List<Monster> getMonsters(){
        return this.monsters;
    }

    /**
     * Time inteverals double.
     *
     * @return the double
     */
    public double timeInteverals(){
        return (double) duration / monsters.size();
    }

    /**
     * Spawn monster.
     *
     * @return the monster
     */
    public Monster spawnMonster(){
        if(monsters.isEmpty()){
            return null;
        }
        else{
            Random random = new Random();
            int monster_index = random.nextInt(this.monsters.size()); // gets random monster from wave
            Monster monsterSpawned = this.monsters.get(monster_index);
            this.monsters.remove(monster_index); // returns this monster

            return monsterSpawned;
        }
    }
}
