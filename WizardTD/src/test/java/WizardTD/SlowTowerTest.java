package WizardTD;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class SlowTowerTest {

    // test slow tower constructor
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
        SlowTower n = new SlowTower(towerimgs, coordinate, 90, 2, 1, fireball, 0.4);

        assertEquals(towerimgs.get(0), n.getType());
        assertEquals(coordinate, n.getCoordinate());
        assertEquals(90, n.getTower_range());
        assertEquals(2, n.getTower_firing_speed());
        assertEquals(1, n.getTower_damage());
        assertEquals(0.4, n.getSlowRate());
    }

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
        SlowTower n = new SlowTower(towerimgs, coordinate, 90, 2, 1, fireball, 0.4);

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
        SlowTower n = new SlowTower(towerimgs, coordinate, 90, 2, 1, fireball, 0.4);

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
        SlowTower n = new SlowTower(towerimgs, coordinate, 90, 2, 1, fireball, 0.4);

        List<Monster> m = new ArrayList<>();
        List<PImage> p = new ArrayList<>();
        p.add(new PImage());
        Monster son = new Monster(p,10.0, 5.0f, 0.2, 100);
        son.setX_Pos(10f);
        son.setY_Pos(20f);
        son.slowed = true;
        m.add(son);
        Monster mon = new Monster(p,10.0, 5.0f, 0.2, 100);
        mon.setX_Pos(50f);
        mon.setY_Pos(60f);
        m.add(mon);

        Fireball f = n.shootFireball(m);
        assertEquals(mon, f.getTarget());

    }
}
