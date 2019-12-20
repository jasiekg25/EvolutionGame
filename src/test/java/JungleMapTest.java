import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class JungleMapTest {
    JungleMap map;
    JungleMap map2;
    JungleMap map3;

    @Before
    public void prepare(){
        map = new JungleMap(new Vector2d(100,30), new Vector2d(10, 10));
        map2 = new JungleMap(new Vector2d(5,5), new Vector2d(2,2));
        map3 = new JungleMap(new Vector2d(5,5), new Vector2d(3,2));
    }


    private int countGrass(JungleMap map){
        int cnt = 0;
        Rectangle mapSize = map.getMapCorners();
        for(int x = mapSize.lowerLeft.x; x < mapSize.upperRight.x; x++){
            for(int y = mapSize.lowerLeft.y; y < mapSize.upperRight.y; y++){
                if(map.objectAt(new Vector2d(x,y)) != null){
                    cnt++;
                }
            }
        }
        return cnt;
    }

    @Test
    public void prepareGrassTest(){
        assertEquals(this.map.getGrassNumber(), this.countGrass(map));
        assertEquals(this.map2.getGrassNumber(), this.countGrass(map2));
        assertEquals(this.map3.getGrassNumber(), this.countGrass(map3));
    }


    @Test
    public void translatePositionTest(){
        Vector2d pos = new Vector2d(-1, -1);
        Vector2d pos2 = new Vector2d(1, -1);
        Vector2d pos3 = new Vector2d(-1, 0);
        Vector2d pos4 = new Vector2d(3, 3);
        pos = map.translatePosition(pos);
        pos2 = map.translatePosition(pos2);
        pos3 = map.translatePosition(pos3);
        pos4 = map.translatePosition(pos4);
        Vector2d ur = this.map.getMapCorners().upperRight;
        assertEquals(pos, new Vector2d(ur.x-1, ur.y-1));
        assertEquals(pos2, new Vector2d(1, ur.y-1));
        assertEquals(pos3, new Vector2d(ur.x-1, 0));
        assertEquals(pos4, new Vector2d(3,3));
    }

    @Test
    public void getFreeSpotTest(){
        Vector2d ur = this.map.getMapCorners().upperRight;
        Random rand = new Random();
        Vector2d selected = new Vector2d(2,2);
        while(map.objectAt(selected) != null){
            selected = new Vector2d(rand.nextInt(ur.x), rand.nextInt(ur.y));
        }
        Vector2d toCheck = selected.add(new Vector2d(-1, -1));
        toCheck = this.map.translatePosition(toCheck);

        assertNotNull(this.map.getFreePosition(toCheck));
        this.map.spawnGrassInArea(this.map.getMapCorners(), null, 1000000);
        assertNull(this.map.getFreePosition(toCheck));
        assertTrue(this.map.objectAt(toCheck) instanceof Grass);
        assertNotNull(this.map.grassAt(toCheck));
    }

    @Test
    public void runTest(){
        //TODO: implement me
    }
}
