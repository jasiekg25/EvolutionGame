import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PointGenerator {

    private final IWorldMap map;

    public PointGenerator(IWorldMap map) {
        this.map = map;
    }

    public List<Vector2d> getPool(Rectangle area, Rectangle exclude) {
        List<Vector2d> pool = new LinkedList<>();
        Vector2d lowerLeft = area.lowerLeft;
        Vector2d upperRight = area.upperRight;

        for (int i = lowerLeft.x; i < upperRight.x; i++)
            for (int j = lowerLeft.y; j < upperRight.y; j++) {
                Vector2d position = new Vector2d(i, j);

                if (exclude != null) {
                    if (exclude.isInside(position)) {
                        continue;
                    }
                }
                if (map.objectAt(position) == null) {
                    pool.add(position);
                }
            }
        return pool;
    }

    public List<Vector2d> getRandomPoints(Rectangle area, Rectangle exclude, Integer percent) {
        if (percent > 100) {
            throw new IllegalArgumentException("PERCENT must be < 100");
        }

        List<Vector2d> res = new ArrayList<>();
        List<Vector2d> pool = this.getPool(area, exclude);

        Integer howManyGrass = (percent * pool.size()) / 100;
        Random gen = new Random();
        while (!(howManyGrass--).equals(0) && pool.size() > 0){
            int randomIdx = gen.nextInt(pool.size());
            res.add(pool.get(randomIdx));
            pool.remove(randomIdx);
        }
        return res;
    }

}
