import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PointGeneratorTest {
    JungleMap map;
    PointGenerator ps;

    @Before
    public void prepare(){
        map = new JungleMap(new Vector2d(10,10), new Vector2d(2,2));
        ps = new PointGenerator(map);
    }

    @Test
    public void getPoolTest(){
        int current = map.getGrassNum();
        int area = map.getMapCorners().area();
        assertEquals(area-current, ps.getPool(map.getMapCorners(), null).size());
        map.spawnGrassInArea(map.getMapCorners(), null, 3);
        current = map.getGrassNum();
        assertEquals(area-current, ps.getPool(map.getMapCorners(), null).size());
    }

    @Test
    public void getRandomPointsTest(){
        int current = map.getGrassNum();
        int mapArea = map.getMapCorners().area();
        int free = mapArea-current;
        int calc = free * JungleMap.GRASS_JUNGLE_PERCENT /100;
        int generatedSize = ps.getRandomPoints(map.getMapCorners(), null, JungleMap.GRASS_JUNGLE_PERCENT).size();
        System.out.println(map);
        assertEquals(generatedSize, calc);
    }
}