package WizardTD;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import processing.core.PImage;
import processing.event.MouseEvent;

import java.awt.*;
import java.util.*;
import java.lang.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    // Test if draw and setup works as normal. normal scenario
    @Test
    public void TestDraw(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.draw();
        assert(true);
    }

    // Test if draw and setup works with Lose condition
    @Test
    public void TestAnimateLose(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.gamePhase = App.GamePhase.Lose;
        a.draw();
        assertSame(a.gamePhase, App.GamePhase.Lose);
    }


    // Test if when gameLose R restarts gamePhase
    @Test
    public void keyPressedR() {
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.gamePhase = App.GamePhase.Lose;
        a.key = 'r';
        a.keyPressed();
        assertSame(a.gamePhase, App.GamePhase.Normal);
    }

    // See if pressing F makes doubleSpeed
    @Test
    public void keyPressedFF() {
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.key = 'F';
        a.keyPressed();
        assertSame(a.gamePhase, App.GamePhase.DoubleSpeed);
    }

    // See if pressing T makes TowerPlacing
    @Test
    public void keyPressedT() {
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.key = 'T';
        a.keyPressed();
        assertSame(a.towerOperation, App.TowerOperation.TowerPlacing);
    }

    // See if pressing T makes normal when already tower placing
    @Test
    public void keyPressedT2() {
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.TowerPlacing;
        a.key = 'T';
        a.keyPressed();
        assertSame(a.towerOperation, App.TowerOperation.Normal);
    }

    // See if pressing S makes SlowTowerPlacing
    @Test
    public void keyPressedS() {
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.key = 'S';
        a.keyPressed();
        assertSame(a.towerOperation, App.TowerOperation.SlowTowerPlacing);
    }

    // See if pressing S makes Normal when already Slow Tower PLacing
    @Test
    public void keyPressedS2() {
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.SlowTowerPlacing;
        a.key = 'S';
        a.keyPressed();
        assertSame(a.towerOperation, App.TowerOperation.Normal);
    }

    // See if pressing Range upgrade given when placing tower
    @Test
    public void keyPressedU1Place() {
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.TowerPlacing;
        a.key = '1';
        a.keyPressed();
        assert (a.upgradeOnSpot.contains(("R")));
    }
    // See if pressing Speed upgrade given when placing tower
    @Test
    public void keyPressedU2Place() {
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.TowerPlacing;
        a.key = '2';
        a.keyPressed();
        assert (a.upgradeOnSpot.contains(("S")));
    }
    // See if pressing Damage upgrade given when placing tower
    @Test
    public void keyPressedU3Place() {
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.TowerPlacing;
        a.key = '3';
        a.keyPressed();
        assert (a.upgradeOnSpot.contains(("D")));
    }

    // See if pressing P pauses game
    @Test
    public void keyPressedP() {
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.key = 'P';
        a.keyPressed();
        assertSame(a.gamePhase, App.GamePhase.Paused);
    }

    // See if pressing P makes GamePhase Normal when already paused
    @Test
    public void keyPressedP2() {
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.gamePhase = App.GamePhase.Paused;
        a.key = 'P';
        a.keyPressed();
        assertSame(a.gamePhase, App.GamePhase.Normal);
    }

    // See if pressing F make game normal when already double speed
    @Test
    public void keyPressedF2() {
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.gamePhase = App.GamePhase.DoubleSpeed;
        a.key = 'F';
        a.keyPressed();
        assertSame(a.gamePhase, App.GamePhase.Normal);
    }

    // See if pressing M upgrades mana
    @Test
    public void keyPressedM() {
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.key = 'M';
        a.keyPressed();
        assertEquals(a.gameMana.initial_mana - a.gameMana.initial_cost, a.gameMana.getMana());
        assertEquals(a.gameMana.initial_mana_cap * a.gameMana.capmultipler, a.gameMana.getMana_cap());
        assertEquals(a.gameMana.initial_cost + a.gameMana.costincrement, a.gameMana.getCost());
        assertEquals(a.gameMana.initial_mana_gained_per_second * a.gameMana.ratemultipler, a.gameMana.getMana_gained_per_second());
    }

    // see if upgrading range on selected tower increases range level
    @Test
    public void Upgrade1(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.TowerSelected;
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
        a.selectedTower = n;
        a.key = '1';
        a.keyPressed();
        assertEquals(1, n.range_level);
    }

    // see if upgrading speed on selected tower increases speed level
    @Test
    public void Upgrade2(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.TowerSelected;
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
        a.selectedTower = n;
        a.key = '2';
        a.keyPressed();
        assertEquals(1, n.speed_level);
    }

    // see if upgrading damage on selected tower increases damage level
    @Test
    public void Upgrade3(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.TowerSelected;
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
        a.selectedTower = n;
        a.key = '3';
        a.keyPressed();
        assertEquals(1, n.damage_level);
    }

    // see if drawing tower upgrade icons on selected tower doesn't crash
    @Test
    public void DrawTowerUpgradeIcons(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
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
        a.selectedTower = n;
        a.towerOperation = App.TowerOperation.TowerSelected;
        n.speed_level = 1;
        n.damage_level = 2;
        n.range_level = 1;
        a.activeTowers.add(n);
        a.draw();
        assertTrue(true);
    }

    // see if drawing tower upgrade prices on selected tower doesn't crash
    @Test
    public void DrawSelectedTowerPricings(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
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
        n.speed_level = 2;
        n.damage_level = 3;
        n.range_level = 1;
        a.activeTowers.add(n);
        a.draw();
        assertTrue(true);
    }

    // see if when last wave, lastwavespawned turns true in order to end game
    @Test
    public void LastWave(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.lastWave = true;
        a.draw();
        assertTrue(a.lastWaveSpawned);
    }

    // see if fireball can interact with arraylist of active fireballs, including with moving
    @Test
    public void FireballTest(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();


        PImage fireballimg = new PImage();

        List<PImage> test = new ArrayList<>();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();
        test.add(thing1);
        test.add(thing2);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        m.setX_Pos(9.0f);
        m.setY_Pos(9.0f);

        Coordinate coordinate = new Coordinate(1.0f, 2.0f);
        Fireball f = new Fireball(fireballimg, 3.4, m, coordinate.x, coordinate.y, null);
        a.activeFireballs.add(f);
        assertTrue(a.activeFireballs.contains(f));

    }

    // see if fireball can interact with arraylist of active fireballs on double speed, including with moving.
    @Test
    public void FireballTestWithDoubleSpeed(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.gamePhase = App.GamePhase.DoubleSpeed;

        PImage fireballimg = new PImage();

        List<PImage> test = new ArrayList<>();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();
        test.add(thing1);
        test.add(thing2);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        m.setX_Pos(9.0f);
        m.setY_Pos(9.0f);

        Coordinate coordinate = new Coordinate(1.0f, 2.0f);
        Fireball f = new Fireball(fireballimg, 3.4, m, coordinate.x, coordinate.y, null);
        a.activeFireballs.add(f);
        assertTrue(a.activeFireballs.contains(f));

    }

    // see if fireball damages target monster
    @Test
    public void FireballDamage(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();

        PImage fireballimg = new PImage();

        List<PImage> test = new ArrayList<>();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();
        test.add(thing1);
        test.add(thing2);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        m.setX_Pos(9.0f);
        m.setY_Pos(9.0f);

        Coordinate coordinate = new Coordinate(9.0f, 9.0f);
        Fireball f = new Fireball(fireballimg, 5, m, coordinate.x, coordinate.y, null);
        a.activeFireballs.add(f);
        a.fireBallManagement();
        assertEquals(6, m.getHp());
    }


    // see if fireball can slow monster
    @Test
    public void FireballSlowDamage(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();

        List<PImage> towerimgs = new ArrayList<>();
        PImage tower = new PImage();
        PImage tower1 = new PImage();
        PImage tower2 = new PImage();
        PImage fireball = new PImage();
        towerimgs.add(tower);
        towerimgs.add(tower1);
        towerimgs.add(tower2);
        Coordinate coordinate = new Coordinate(1.0f, 2.0f);
        SlowTower n = new SlowTower(towerimgs, coordinate, 90, 2, 1, fireball, 0.4);

        PImage fireballimg = new PImage();

        List<PImage> test = new ArrayList<>();
        PImage thing1 = new PImage();
        PImage thing2 = new PImage();
        test.add(thing1);
        test.add(thing2);
        Monster m = new Monster(test, 10.0, 5.0f, 0.2, 100);
        m.setX_Pos(9.0f);
        m.setY_Pos(9.0f);

        Coordinate c = new Coordinate(9.0f, 9.0f);
        Fireball f = new Fireball(fireballimg, 5, m, c.x, c.y, n);
        a.activeFireballs.add(f);
        a.fireBallManagement();
        assertEquals(6, m.getHp());
        assert(m.slowed);
        assertEquals(3,m.getSpeed());
    }

    // see if game double speed if click fast forward button
    @Test
    public void MouseIconFF1(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        MouseEvent mockedMouseEvent = new MouseEvent(a, System.currentTimeMillis(), 2, 0, 660, 70, 37, 1);

        a.mouseReleased(mockedMouseEvent);
        assertEquals(App.GamePhase.DoubleSpeed, a.gamePhase);
        assertFalse(a.iconSelected.contains("P"));
    }

    // see if game normal if click fast forward button & already fast forward
    @Test
    public void MouseIconFF2(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.gamePhase = App.GamePhase.DoubleSpeed;
        MouseEvent mockedMouseEvent = new MouseEvent(a, System.currentTimeMillis(), 2, 0, 660, 70, 37, 1);

        a.mouseReleased(mockedMouseEvent);
        assertEquals(App.GamePhase.Normal, a.gamePhase);
    }


    // see if game pauses if click pause button
    @Test
    public void MouseIconP1(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        MouseEvent mockedMouseEvent = new MouseEvent(a, System.currentTimeMillis(), 2, 0, 660, 110, 37, 1);
        a.mouseReleased(mockedMouseEvent);
        assertEquals(App.GamePhase.Paused, a.gamePhase);
        assertFalse(a.iconSelected.contains("FF"));
    }

    // see if game goes normal if click pause button & game is paused
    @Test
    public void MouseIconP2(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.gamePhase = App.GamePhase.Paused;
        MouseEvent mockedMouseEvent = new MouseEvent(a, System.currentTimeMillis(), 2, 0, 660, 110, 37, 1);

        a.mouseReleased(mockedMouseEvent);
        assertEquals(App.GamePhase.Normal, a.gamePhase);
    }

    // see if toweroperation allows placing tower when click T button
    @Test
    public void MouseIconT1(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        MouseEvent mockedMouseEvent = new MouseEvent(a, System.currentTimeMillis(), 2, 0, 660, 160, 37, 1);

        a.mouseReleased(mockedMouseEvent);
        assertEquals(App.TowerOperation.TowerPlacing, a.towerOperation);
    }

    // see if toweroperation is normal when click T button and toweroperation already placing tower
    @Test
    public void MouseIconT2(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.TowerPlacing;
        MouseEvent mockedMouseEvent = new MouseEvent(a, System.currentTimeMillis(), 2, 0, 660, 160, 37, 1);

        a.mouseReleased(mockedMouseEvent);
        assertEquals(App.TowerOperation.Normal, a.towerOperation);
    }

    // see if toweroperation allows placing slow tower when click S button
    @Test
    public void MouseIconS1(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        MouseEvent mockedMouseEvent = new MouseEvent(a, System.currentTimeMillis(), 2, 0, 660, 210, 37, 1);

        a.mouseReleased(mockedMouseEvent);
        assertEquals(App.TowerOperation.SlowTowerPlacing, a.towerOperation);
    }

    // see if toweroperation is normal when click S button and slowtoweroperation already placing tower
    @Test
    public void MouseIconS2(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.SlowTowerPlacing;
        MouseEvent mockedMouseEvent = new MouseEvent(a, System.currentTimeMillis(), 2, 0, 660, 210, 37, 1);

        a.mouseReleased(mockedMouseEvent);
        assertEquals(App.TowerOperation.Normal, a.towerOperation);
    }

    //see if mouse click upgrade range button upgrades selected tower
    @Test
    public void MouseIconTU1(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.TowerSelected;
        MouseEvent mockedMouseEvent = new MouseEvent(a, System.currentTimeMillis(), 2, 0, 660, 260, 37, 1);
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
        a.selectedTower = n;
        a.mouseReleased(mockedMouseEvent);
        assertEquals(1, n.range_level);
    }

    //see if mouse click upgrade speed button upgrades selected tower
    @Test
    public void MouseIconTU2(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.TowerSelected;
        MouseEvent mockedMouseEvent = new MouseEvent(a, System.currentTimeMillis(), 2, 0, 660, 310, 37, 1);
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
        a.selectedTower = n;
        a.mouseReleased(mockedMouseEvent);
        assertEquals(1, n.speed_level);
    }

    //see if mouse click upgrade damage button upgrades selected tower
    @Test
    public void MouseIconTU3(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.TowerSelected;
        MouseEvent mockedMouseEvent = new MouseEvent(a, System.currentTimeMillis(), 2, 0, 660, 360, 37, 1);
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
        a.selectedTower = n;
        a.mouseReleased(mockedMouseEvent);
        assertEquals(1, n.damage_level);
    }

    //see if mouse click upgrade range button adds range upgrades when tower eventually placed
    @Test
    public void MouseIconT2U1(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.TowerPlacing;
        MouseEvent mockedMouseEvent = new MouseEvent(a, System.currentTimeMillis(), 2, 0, 660, 260, 37, 1);
        a.mouseReleased(mockedMouseEvent);
        assert (a.upgradeOnSpot.contains(("R")));
    }

    //see if mouse click upgrade speed button adds speed upgrades when tower eventually placed
    @Test
    public void MouseIconT2U2(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.TowerPlacing;
        MouseEvent mockedMouseEvent = new MouseEvent(a, System.currentTimeMillis(), 2, 0, 660, 310, 37, 1);
        a.mouseReleased(mockedMouseEvent);
        assert (a.upgradeOnSpot.contains(("S")));
    }

    //see if mouse click upgrade damage button adds damage upgrades when tower eventually placed
    @Test
    public void MouseIconT2U3(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.TowerPlacing;
        MouseEvent mockedMouseEvent = new MouseEvent(a, System.currentTimeMillis(), 2, 0, 660, 360, 37, 1);
        a.mouseReleased(mockedMouseEvent);
        assert (a.upgradeOnSpot.contains(("D")));
    }

    // see if mouse click mana button upgrades mana
    @Test
    public void MouseIconM(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        MouseEvent mockedMouseEvent = new MouseEvent(a, System.currentTimeMillis(), 2, 0, 660, 410, 37, 1);
        a.mouseReleased(mockedMouseEvent);
        assertEquals(a.gameMana.initial_mana - a.gameMana.initial_cost, a.gameMana.getMana());
        assertEquals(a.gameMana.initial_mana_cap * a.gameMana.capmultipler, a.gameMana.getMana_cap());
        assertEquals(a.gameMana.initial_cost + a.gameMana.costincrement, a.gameMana.getCost());
        assertEquals(a.gameMana.initial_mana_gained_per_second * a.gameMana.ratemultipler, a.gameMana.getMana_gained_per_second());
    }

    // see if mouse clicks when tower placing on a valid spot places a tower.
    @Test
    public void placeTower(){
        App a = new App();
        App.runSketch(new String[] {"App"}, a);
        a.setup();
        a.towerOperation = App.TowerOperation.TowerPlacing;
        MouseEvent mockedMouseEvent = new MouseEvent(a, System.currentTimeMillis(), 2, 0, 50, 50, 37, 1);
        a.mousePressed(mockedMouseEvent);
        assertFalse(a.activeTowers.isEmpty());
    }

}
