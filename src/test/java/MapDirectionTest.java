import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapDirectionTest {
    MapDirection dir;
    MapDirection dir2;
    MapDirection dir3;
    @Before
    public void prepare(){
        dir = MapDirection.DEG90;
        dir2 = MapDirection.DEG315;
        dir3 = MapDirection.DEG180;
    }

    @Test
    public void getNextTest(){
        assertEquals(dir.getNext(), MapDirection.DEG135);
        assertEquals(dir2.getNext(), MapDirection.DEG0);
        assertEquals(dir3.getNext(), MapDirection.DEG225);
    }

    @Test
    public void getPreviousTest(){
        assertEquals(dir.getPrevious(), MapDirection.DEG45);
        assertEquals(dir2.getPrevious(), MapDirection.DEG270);
        assertEquals(dir3.getPrevious(), MapDirection.DEG135);
    }

    @Test
    public void getDirectionTest(){
        assertEquals(MapDirection.getDirection(0), MapDirection.DEG0);
        assertEquals(MapDirection.getDirection(2), MapDirection.DEG90);
        assertEquals(MapDirection.getDirection(7), MapDirection.DEG315);
    }
}
