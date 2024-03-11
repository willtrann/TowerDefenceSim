package WizardTD;

import processing.core.PApplet;
import processing.core.PImage;
import org.junit.jupiter.api.Test;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class FireballTest {
    @Test
    public void testConstructor(){
        List<PImage> towerimgs = new ArrayList<>();
        PImage thing = new PImage();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();

        List<PImage> test = new ArrayList<>();
        PImage m = new PImage();
        PImage m1 = new PImage();
        test.add(m);
        test.add(m1);

        PImage fireballimg = new PImage();
        towerimgs.add(thing);
        towerimgs.add(thing1);
        towerimgs.add(thing2);

        Coordinate coordinate = new Coordinate(1.0f, 2.0f);
        Tower n = new Tower(towerimgs, coordinate, 90, 2, 1, fireballimg);
        Monster mon = new Monster(test, 10.0, 40.0f, 30.2, 100);
        Fireball f = new Fireball(fireballimg, 3.4, mon, coordinate.x, coordinate.y, n);

        assertEquals(fireballimg, f.getType());
        assertEquals(3.4, f.getDmg());
        assertEquals(mon, f.getTarget());
        assertEquals(coordinate.x ,f.getX());
        assertEquals(coordinate.y ,f.getY());
        assertEquals(n ,f.getParentTower());
    }

    @Test
    public void moveFireball(){
        List<PImage> towerimgs = new ArrayList<>();
        PImage thing = new PImage();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();

        List<PImage> test = new ArrayList<>();
        PImage m = new PImage();
        PImage m1 = new PImage();
        test.add(m);
        test.add(m1);

        PImage fireballimg = new PImage();
        towerimgs.add(thing);
        towerimgs.add(thing1);
        towerimgs.add(thing2);

        Coordinate coordinate = new Coordinate(1.0f, 2.0f);
        Tower n = new Tower(towerimgs, coordinate, 90, 2, 1, fireballimg);
        Monster mon = new Monster(test, 10.0, 40.0f, 30.2, 100);
        mon.setY_Pos(20f);
        mon.setX_Pos(20f);
        Fireball f = new Fireball(fireballimg, 3.4, mon, coordinate.x, coordinate.y, n);

        f.moveFireball();
        assertEquals(4.629f, f.getX(), 0.001);
        assertEquals(5.439f, f.getY(), 0.001);
    }

    @Test
    public void reachedTarget1(){
        List<PImage> towerimgs = new ArrayList<>();
        PImage thing = new PImage();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();

        List<PImage> test = new ArrayList<>();
        PImage m = new PImage();
        PImage m1 = new PImage();
        test.add(m);
        test.add(m1);

        PImage fireballimg = new PImage();
        towerimgs.add(thing);
        towerimgs.add(thing1);
        towerimgs.add(thing2);

        Coordinate coordinate = new Coordinate(20f, 20f);
        Tower n = new Tower(towerimgs, coordinate, 90, 2, 1, fireballimg);
        Monster mon = new Monster(test, 10.0, 40.0f, 30.2, 100);
        mon.setY_Pos(20f);
        mon.setX_Pos(20f);
        Fireball f = new Fireball(fireballimg, 3.4, mon, coordinate.x, coordinate.y, n);

        assertTrue(f.reachedTarget());
    }

    @Test
    public void reachedTarget2(){
        List<PImage> towerimgs = new ArrayList<>();
        PImage thing = new PImage();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();

        List<PImage> test = new ArrayList<>();
        PImage m = new PImage();
        PImage m1 = new PImage();
        test.add(m);
        test.add(m1);

        PImage fireballimg = new PImage();
        towerimgs.add(thing);
        towerimgs.add(thing1);
        towerimgs.add(thing2);

        Coordinate coordinate = new Coordinate(20f, 20f);
        Tower n = new Tower(towerimgs, coordinate, 90, 2, 1, fireballimg);
        Monster mon = new Monster(test, 10.0, 40.0f, 30.2, 100);
        mon.setY_Pos(30f);
        mon.setX_Pos(30f);
        Fireball f = new Fireball(fireballimg, 3.4, mon, coordinate.x, coordinate.y, n);

        assertFalse(f.reachedTarget());
    }

    @Test
    public void reachedTarget3(){
        List<PImage> towerimgs = new ArrayList<>();
        PImage thing = new PImage();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();

        List<PImage> test = new ArrayList<>();
        PImage m = new PImage();
        PImage m1 = new PImage();
        test.add(m);
        test.add(m1);

        PImage fireballimg = new PImage();
        towerimgs.add(thing);
        towerimgs.add(thing1);
        towerimgs.add(thing2);

        Coordinate coordinate = new Coordinate(20f, 20f);
        Tower n = new Tower(towerimgs, coordinate, 90, 2, 1, fireballimg);
        Monster mon = new Monster(test, 10.0, 40.0f, 30.2, 100);
        mon.setY_Pos(20f);
        mon.setX_Pos(20f);
        Fireball f = new Fireball(fireballimg, 3.4, mon, coordinate.x, coordinate.y, n);
        f.hasDamaged = true;

        assertFalse(f.reachedTarget());
    }
}
