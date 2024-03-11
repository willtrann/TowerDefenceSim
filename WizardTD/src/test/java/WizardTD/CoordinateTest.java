package WizardTD;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class CoordinateTest {
    // test coordinate constructor
    @Test
    public void testConstructor(){
        Coordinate c = new Coordinate((float)1.0, (float)2.5);
        assertEquals((float)1.0, c.x, 0.01);
        assertEquals((float)2.5, c.y, 0.01);
    }

}
