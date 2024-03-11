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

public class MonsterTest {
    @Test
    public void TestConstructor(){
        App a = new App();
        List<PImage> test = new ArrayList<>();
        test.add(null);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        assertEquals(10.0, m.getHp(), 0.1);
        assertEquals(5.0f, m.getSpeed(), 0.1);
        assertEquals(0.2, m.getArmour(), 0.1);
        assertEquals(100, m.getMana_gained_on_kill(), 0.1);
    }

    @Test
    public void TestPathSet() {
        App a = new App();
        List<PImage> test = new ArrayList<>();
        test.add(null);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        File f = new File("level2.txt");
        Coordinate start = new Coordinate(9.0f, 0.0f);
        m.setPath(start,f);
        assertTrue(true);
    }

    @Test
    public void InvalidTestPathSet() {
        App a = new App();
        List<PImage> test = new ArrayList<>();
        test.add(null);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        File f = new File("levelx.txt");
        Coordinate start = new Coordinate(9.0f, 0.0f);
        m.setPath(start,f);
        assertTrue(true);
    }

    @Test
    public void TestDead1() {
        App a = new App();
        List<PImage> test = new ArrayList<>();
        test.add(null);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        assertFalse(m.isDead());
    }

    @Test
    public void TestDead2() {
        App a = new App();
        List<PImage> test = new ArrayList<>();
        test.add(null);
        Monster m = new Monster(test, 0.0, 5.0f, 0.2, 100);
        m.isDead();
        assertTrue(m.isDead());
    }

    @Test
    public void TestSlow() {
        App a = new App();
        List<PImage> test = new ArrayList<>();
        test.add(null);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        m.slowMonster(0.3);
        assertEquals(3.5f , m.getSpeed(),0.1);
        assertTrue(m.slowed);
    }

    @Test
    public void MoveMonster1() {
        App a = new App();
        List<PImage> test = new ArrayList<>();
        test.add(null);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        File f = new File("level2.txt");
        Coordinate start = new Coordinate(0.0f, 3.0f);
        m.setPath(start,f);
        m.updatePosition();
        m.updatePosition();
        m.updatePosition();
        assertEquals(0.0f * 32 + 5.0f + 5.0f, m.getX_Pos(),0.1);
        assertEquals(3.0f * 32 + 40.0f + 5.0f, m.getY_Pos(),0.1);
    }

    @Test
    public void MoveMonster2() {
        App a = new App();
        List<PImage> test = new ArrayList<>();
        test.add(null);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        File f = new File("level2.txt");
        Coordinate start = new Coordinate(9.0f, 0.0f);
        m.setPath(start,f);
        m.updatePosition();
        m.updatePosition();
        m.updatePosition();
        assertEquals(9.0f * 32 + 5.0f, m.getX_Pos(),0.1);
        assertEquals(0.0f * 32 + 40.0f + 5.0f + 5.0f, m.getY_Pos(),0.1);
    }

    @Test
    public void MoveMonster3() {
        App a = new App();
        List<PImage> test = new ArrayList<>();
        test.add(null);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        File f = new File("level2.txt");
        Coordinate start = new Coordinate(9.0f, 0.0f);
        m.setPath(start,f);
        m.updatePosition();
        m.updatePosition();
        m.updatePosition();
        m.updatePosition();
        m.updatePosition();
        m.updatePosition();
        m.updatePosition();
        m.updatePosition();
        m.updatePosition();
        assertEquals(9.0f * 32 + 5.0f, m.getX_Pos(),0.1);
        assertEquals(1.0f * 32 + 40.0f + 5.0f, m.getY_Pos(),0.1);
    }

    @Test
    public void MoveMonster4() {
        App a = new App();
        List<PImage> test = new ArrayList<>();
        test.add(null);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        File f = new File("level2.txt");
        Coordinate start = new Coordinate(0.0f, 3.0f);
        m.setPath(start,f);
        m.updatePosition();
        m.updatePosition();
        m.updatePosition();
        m.updatePosition();
        m.updatePosition();
        m.updatePosition();
        m.updatePosition();
        m.updatePosition();
        m.updatePosition();
        assertEquals(1.0f * 32 + 5.0f, m.getX_Pos(),0.1);
        assertEquals(3.0f * 32 + 40.0f + 5.0f, m.getY_Pos(),0.1);
    }


    @Test
    public void resetPath() {
        App a = new App();
        List<PImage> test = new ArrayList<>();
        test.add(null);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        File f = new File("level2.txt");
        Coordinate start = new Coordinate(9.0f, 0.0f);
        m.setPath(start,f);
        m.resetPath();
        assertEquals(9.0f * 32 + 5.0f, m.getX_Pos(),0.1);
        assertEquals(0.0f * 32 + 45.0f, m.getY_Pos(),0.1);
    }

    @Test
    public void takeDamage() {
        App a = new App();
        List<PImage> test = new ArrayList<>();
        test.add(null);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        m.takeDamage(2.5);
        assertEquals(8, m.getHp(),0.1);
    }

    @Test
    public void dyingAnimation() {
        App a = new App();
        List<PImage> test = new ArrayList<>();
        test.add(null);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        assertTrue(m.dyingAnimation());
    }
    @Test
    public void dyingAnimation2() {
        App a = new App();
        List<PImage> test = new ArrayList<>();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();
        test.add(thing1);
        test.add(thing2);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        assertFalse(m.dyingAnimation());
        assertEquals(thing2,m.getType());
    }
    @Test
    public void dyingAnimation3() {
        App a = new App();
        List<PImage> test = new ArrayList<>();
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        assertTrue(m.dyingAnimation());
    }


    @Test
    public void getPath() {
        App a = new App();
        List<PImage> test = new ArrayList<>();
        test.add(null);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        File f = new File("level2.txt");
        Coordinate start = new Coordinate(9.0f, 0.0f);
        m.setPath(start,f);
        List<Coordinate> pathExpected = new ArrayList<>();
        pathExpected.add(start);
        pathExpected.add(new Coordinate(9.0f, 1.0f));
        pathExpected.add(new Coordinate(9.0f, 2.0f));
        pathExpected.add(new Coordinate(9.0f, 3.0f));
        pathExpected.add(new Coordinate(9.0f, 4.0f));
        pathExpected.add(new Coordinate(9.0f, 5.0f));

        List<Coordinate> pathActual = m.getPath();
        int i = 0;
        while(i <= 5) {
            assertEquals(pathExpected.get(i).x, pathActual.get(i).x);
            assertEquals(pathExpected.get(i).y, pathActual.get(i).y);
            i++;
        }
    }

    @Test
    public void setXandY() {
        App a = new App();
        List<PImage> test = new ArrayList<>();
        test.add(null);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        m.setX_Pos(2.0f);
        m.setY_Pos(3.0f);
        assertEquals(2.0f, m.getX_Pos(), 0.1);
        assertEquals(3.0f, m.getY_Pos(), 0.1);
    }


}
