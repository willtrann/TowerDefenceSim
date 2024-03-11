package WizardTD;

import org.checkerframework.checker.units.qual.A;
import processing.core.PApplet;
import processing.core.PImage;
import java.io.File;
import java.util.*;
import java.lang.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class WaveTest {
    // test wave constructor
    @Test
    public void TestConstructor(){
        List<Monster> m = new ArrayList<>();
        m.add(null);
        Wave wave = new Wave(10, 4, m);
        assertEquals(10, wave.getPre_wave_pause());
        assertEquals(4, wave.getDuration());
        assertEquals(m, wave.getMonsters());
    }

    // test size of monster arraylist matches
    @Test
    public void sizeMonsters(){
        List<Monster> m = new ArrayList<>();
        m.add(null);
        m.add(null);
        m.add(null);
        Wave wave = new Wave(10, 4, m);
        assertEquals(3, wave.getSizeMonsters());
    }

    // test timeduration is expected
    @Test
    public void timeDurations(){
        List<Monster> m = new ArrayList<>();
        m.add(null);
        m.add(null);
        m.add(null);
        m.add(null);
        m.add(null);
        m.add(null);
        m.add(null);
        m.add(null);
        Wave wave = new Wave(10, 4, m);
        assertEquals(0.5, wave.timeInteverals());
    }


    // test if monster object comes out of spawn monster
    @Test
    public void spawnMonster(){
        List<Monster> m = new ArrayList<>();
        List<PImage> p = new ArrayList<>();
        p.add(null);
        Monster mon = new Monster(p,10.0, 5.0f, 0.2, 100);
        m.add(mon);
        Wave wave = new Wave(10, 4, m);
        assertEquals(mon, wave.spawnMonster());
    }

    // test spawn monster when monster list empty
    @Test
    public void spawnMonsterEmpty(){
        List<Monster> m = new ArrayList<>();
        List<PImage> p = new ArrayList<>();
        p.add(null);
        Monster mon = new Monster(p,10.0, 5.0f, 0.2, 100);
        Wave wave = new Wave(10, 4, m);
        assertEquals(null, wave.spawnMonster());
    }
}
