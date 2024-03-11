package WizardTD;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ManaTest {
    @Test
    public void testConstructor(){
        Mana m  = new Mana(100, 2.3, 100, 1000, 150, 1.5, 1.3);
        assertEquals(100, m.getCost());
        assertEquals(2.3, m.getMana_gained_per_second());
        assertEquals(100, m.getMana());
        assertEquals(1000, m.getMana_cap());
    }

    @Test
    public void decreaseMana1(){
        Mana m  = new Mana(100, 2.3, 100, 1000, 150, 1.5, 1.3);
        m.decreaseMana(50);
        assertEquals(50,m.getMana());
    }

    @Test
    public void decreaseMana2(){
        Mana m  = new Mana(100, 2.3, 100, 1000, 150, 1.5, 1.3);
        m.decreaseMana(120);
        assertEquals(0,m.getMana());
    }

    @Test
    public void increaseMana1(){
        Mana m  = new Mana(100, 2.3, 100, 1000, 150, 1.5, 1.3);
        m.increaseMana();
        assertEquals(102,m.getMana());
    }

    @Test
    public void increaseMana2(){
        Mana m  = new Mana(100, 2.3, 1000, 1000, 150, 1.5, 1.3);
        m.increaseMana();
        assertEquals(1000,m.getMana());
    }

    @Test
    public void manaUpgrade1(){
        Mana m  = new Mana(100, 5, 110, 1000, 150, 1.5, 2);
        m.manaUpgrade();
        assertEquals(250, m.getCost());
        assertEquals(10, m.getMana_gained_per_second());
        assertEquals(1500, m.getMana_cap());
        assertEquals(1.1, m.killMultiplier);
    }

    @Test
    public void manaUpgrade2(){
        Mana m  = new Mana(100, 5, 50, 1000, 150, 1.5, 2);
        m.manaUpgrade();
        assertEquals(100, m.getCost());
        assertEquals(5, m.getMana_gained_per_second());
        assertEquals(1000, m.getMana_cap());
        assertEquals(1, m.killMultiplier);
    }

    @Test
    public void increaseKill1(){
        Mana m  = new Mana(100, 5, 100, 1000, 150, 1.5, 2);
        m.increaseManaOnKill(100);
        assertEquals(200, m.getMana());
        m.manaUpgrade();
        m.increaseManaOnKill(100);
        assertEquals(210, m.getMana());
    }

    @Test
    public void increaseKill2(){
        Mana m  = new Mana(100, 5, 1000, 1000, 150, 1.5, 2);
        m.increaseManaOnKill(100);
        assertEquals(1000, m.getMana());
    }

    @Test
    public void resetMana(){
        Mana m  = new Mana(100, 5, 110, 1000, 150, 1.5, 2);
        m.manaUpgrade();
        assertEquals(250, m.getCost());
        assertEquals(10, m.getMana_gained_per_second());
        assertEquals(1500, m.getMana_cap());
        assertEquals(1.1, m.killMultiplier);

        m.resetMana();

        assertEquals(100, m.getCost());
        assertEquals(5, m.getMana_gained_per_second());
        assertEquals(1000, m.getMana_cap());
        assertEquals(1, m.killMultiplier);
    }
}
