package WizardTD;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class TowerTest {
    // tests tower construcotr
    @Test
    public void testConstructor(){
        List<PImage> towerimgs = new ArrayList<>();
        PImage thing = new PImage();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();
        PImage fireball = new PImage();
        towerimgs.add(thing);
        towerimgs.add(thing1);
        towerimgs.add(thing2);
        Coordinate coordinate = new Coordinate(1.0f, 2.0f);
        Tower n = new Tower(towerimgs, coordinate, 90, 2, 1, fireball);

        assertEquals(towerimgs.get(0), n.getType());
        assertEquals(coordinate, n.getCoordinate());
        assertEquals(90, n.getTower_range());
        assertEquals(2, n.getTower_firing_speed());
        assertEquals(1, n.getTower_damage());
        assertNull(n.getFireball());
    }

    // tests image is correct when tower is level 1
    @Test
    public void testLevel1(){
        List<PImage> towerimgs = new ArrayList<>();
        PImage thing = new PImage();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();
        PImage fireball = new PImage();
        towerimgs.add(thing);
        towerimgs.add(thing1);
        towerimgs.add(thing2);
        Coordinate coordinate = new Coordinate(1.0f, 2.0f);
        Tower n = new Tower(towerimgs, coordinate, 90, 2, 1, fireball);

        n.range_level = 1;
        n.speed_level = 1;
        n.damage_level = 1;
        n.towerImage();
        assertEquals(thing1, n.getType());
    }
    // tests image is correct when tower is level 2
    @Test
    public void testLevel2(){
        List<PImage> towerimgs = new ArrayList<>();
        PImage thing = new PImage();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();
        PImage fireball = new PImage();
        towerimgs.add(thing);
        towerimgs.add(thing1);
        towerimgs.add(thing2);
        Coordinate coordinate = new Coordinate(1.0f, 2.0f);
        Tower n = new Tower(towerimgs, coordinate, 90, 2, 1, fireball);

        n.range_level = 3;
        n.speed_level = 2;
        n.damage_level = 3;
        n.towerImage();
        assertEquals(thing2, n.getType());
    }

    // test if upgrade damages increase damage
    @Test
    public void upgradeDamage(){
        List<PImage> towerimgs = new ArrayList<>();
        PImage thing = new PImage();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();
        PImage fireball = new PImage();
        towerimgs.add(thing);
        towerimgs.add(thing1);
        towerimgs.add(thing2);
        Coordinate coordinate = new Coordinate(1.0f, 2.0f);
        Tower n = new Tower(towerimgs, coordinate, 90, 2, 1, fireball);

        n.upgradeDamage();
        assertEquals(1,n.damage_level);
        assertEquals(1.5, n.getTower_damage());
        assertEquals(20, n.cost_dmg);
    }
    // test if upgrade range increase range
    @Test
    public void upgradeRange(){
        List<PImage> towerimgs = new ArrayList<>();
        PImage thing = new PImage();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();
        PImage fireball = new PImage();
        towerimgs.add(thing);
        towerimgs.add(thing1);
        towerimgs.add(thing2);
        Coordinate coordinate = new Coordinate(1.0f, 2.0f);
        Tower n = new Tower(towerimgs, coordinate, 90, 2, 1, fireball);

        n.upgradeRange();
        assertEquals(1,n.range_level);
        assertEquals(91, n.getTower_range());
        assertEquals(20, n.cost_range);
    }

    // test if upgrade speed increases speed
    @Test
    public void upgradeSpeed(){
        List<PImage> towerimgs = new ArrayList<>();
        PImage thing = new PImage();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();
        PImage fireball = new PImage();
        towerimgs.add(thing);
        towerimgs.add(thing1);
        towerimgs.add(thing2);
        Coordinate coordinate = new Coordinate(1.0f, 2.0f);
        Tower n = new Tower(towerimgs, coordinate, 90, 2, 1, fireball);

        n.upgradeSpeed();
        assertEquals(1,n.speed_level);
        assertEquals(2.5, n.getTower_firing_speed());
        assertEquals(20, n.cost_speed);
    }


    // test if when tower shoots fireball, fireball comes out when monster in range
    @Test
    public void testFireballFire(){
        List<PImage> towerimgs = new ArrayList<>();
        PImage thing = new PImage();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();
        PImage fireball = new PImage();
        towerimgs.add(thing);
        towerimgs.add(thing1);
        towerimgs.add(thing2);
        Coordinate coordinate = new Coordinate(1.0f, 2.0f);
        Tower n = new Tower(towerimgs, coordinate, 90, 2, 1, fireball);

        List<Monster> m = new ArrayList<>();
        List<PImage> p = new ArrayList<>();
        p.add(new PImage());
        Monster mon = new Monster(p,10.0, 5.0f, 0.2, 100);
        mon.setX_Pos(60f);
        mon.setY_Pos(50f);
        m.add(mon);

        Fireball f = n.shootFireball(m);
        assertEquals(fireball, f.getType());

    }

    // test if when tower shoots fireball, fireball comes out but monster is not in range
    @Test
    public void testFireballFire2(){
        List<PImage> towerimgs = new ArrayList<>();
        PImage thing = new PImage();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();
        PImage fireball = new PImage();
        towerimgs.add(thing);
        towerimgs.add(thing1);
        towerimgs.add(thing2);
        Coordinate coordinate = new Coordinate(1.0f, 2.0f);
        Tower n = new Tower(towerimgs, coordinate, 90, 2, 1, fireball);

        List<Monster> m = new ArrayList<>();
        List<PImage> p = new ArrayList<>();
        p.add(new PImage());
        Monster mon = new Monster(p,10.0, 5.0f, 0.2, 100);
        mon.setX_Pos(100f);
        mon.setY_Pos(300f);
        m.add(mon);

        Fireball f = n.shootFireball(m);
        assertNull(f);

    }

    // test if when tower shoots fireball, fireball comes out but target monster already identified but out of range
    @Test
    public void testFireballFire3(){
        List<PImage> towerimgs = new ArrayList<>();
        PImage thing = new PImage();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();
        PImage fireball = new PImage();
        towerimgs.add(thing);
        towerimgs.add(thing1);
        towerimgs.add(thing2);
        Coordinate coordinate = new Coordinate(1.0f, 2.0f);
        Tower n = new Tower(towerimgs, coordinate, 90, 2, 1, fireball);

        List<Monster> m = new ArrayList<>();
        List<PImage> p = new ArrayList<>();
        p.add(new PImage());
        Monster son = new Monster(p,10.0, 5.0f, 0.2, 100);
        son.setX_Pos(10f);
        son.setY_Pos(20f);
        m.add(son);
        Monster mon = new Monster(p,10.0, 5.0f, 0.2, 100);
        mon.setX_Pos(200f);
        mon.setY_Pos(100f);
        m.add(mon);
        n.target = mon;

        Fireball f = n.shootFireball(m);
        assertEquals(son, f.getTarget());

    }

    // test if when tower shoots fireball, fireball comes out but target monster already identified and in range
    @Test
    public void testFireballFire4(){
        List<PImage> towerimgs = new ArrayList<>();
        PImage thing = new PImage();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();
        PImage fireball = new PImage();
        towerimgs.add(thing);
        towerimgs.add(thing1);
        towerimgs.add(thing2);
        Coordinate coordinate = new Coordinate(1.0f, 2.0f);
        Tower n = new Tower(towerimgs, coordinate, 90, 2, 1, fireball);

        List<Monster> m = new ArrayList<>();
        List<PImage> p = new ArrayList<>();
        p.add(new PImage());
        Monster son = new Monster(p,10.0, 5.0f, 0.2, 100);
        son.setX_Pos(10f);
        son.setY_Pos(20f);
        m.add(son);
        Monster mon = new Monster(p,10.0, 5.0f, 0.2, 100);
        mon.setX_Pos(30f);
        mon.setY_Pos(20f);
        m.add(mon);
        n.target = mon;

        Fireball f = n.shootFireball(m);
        assertEquals(mon, f.getTarget());

    }
}
