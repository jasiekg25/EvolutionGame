import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RectangleTest {
    Rectangle r;
    Rectangle r2;

    @Before
    public void prepare(){
        r = new Rectangle(new Vector2d(0,0), new Vector2d(4,3));
        r2 = new Rectangle(new Vector2d(0,0), new Vector2d(4,3));
    }

    @Test
    public void isInsideTest(){
        assertTrue(r.isInside(new Vector2d(0,0)));
        assertFalse(r.isInside(new Vector2d(4,3)));
        assertTrue(r.isInside(new Vector2d(1,1)));
        assertFalse(r.isInside(new Vector2d(1,12)));
    }

    @Test
    public void areaTest(){
        assertEquals(12, r.area().intValue());
    }

    @Test
    public void equalsTest(){
        assertEquals(r, r2);
    }
}