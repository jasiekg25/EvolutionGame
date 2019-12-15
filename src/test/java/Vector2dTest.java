import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class Vector2dTest {
    @Test
    public void testEquals(){
        Vector2d a = new Vector2d(1, 2);
        Vector2d b = new Vector2d(1, 2);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testToString(){
        Vector2d a = new Vector2d(1,2);
        assertEquals(a.toString(), "(1,2)");
    }

    @Test
    public void testPrecedes(){
        Vector2d a = new Vector2d(1, 2);
        Vector2d b = new Vector2d(-1, -2);
        assertTrue(b.precedes(a));
    }

    @Test
    public void testFollows(){
        Vector2d a = new Vector2d(1, 2);
        Vector2d b = new Vector2d(-1, -2);
        assertTrue(a.follows(b));
    }

    @Test
    public void testUpperRight(){
        Vector2d a = new Vector2d(1, 2);
        Vector2d b = new Vector2d(-1, -2);
        Vector2d t = new Vector2d(1, 2);
        assertEquals(a.upperRight(b), t);
    }

    @Test
    public void testLowerLeft(){
        Vector2d a = new Vector2d(1, 2);
        Vector2d b = new Vector2d(-1, -2);
        Vector2d t = new Vector2d(-1, -2);
        assertEquals(a.lowerLeft(b), t);
    }

    @Test
    public void testAdd(){
        Vector2d a = new Vector2d(1, 2);
        Vector2d b = new Vector2d(-1, -2);
        Vector2d t = new Vector2d(0, 0);
        assertEquals(a.add(b), t);
    }

    @Test
    public void testSubtract(){
        Vector2d a = new Vector2d(1, 2);
        Vector2d b = new Vector2d(-1, -2);
        Vector2d t = new Vector2d(2, 4);
        assertEquals(a.subtract(b), t);
    }

    @Test
    public void testOpposite(){
        Vector2d a = new Vector2d(1, 2);
        Vector2d t = new Vector2d(-1, -2);
        assertEquals(a.opposite(), t);
    }
}