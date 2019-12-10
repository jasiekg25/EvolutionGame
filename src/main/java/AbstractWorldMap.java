import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    private final MapVisualizer mapVisualizer = new MapVisualizer(this);
    protected Multimap<Vector2d, Animal> animalMap = LinkedListMultimap.create();

    @Override
    public boolean place(Animal animal) {
        if (this.objectAt(animal.getPosition()) != null) {
            return false;
        }

        // TODO: send to Harness
        animalMap.put(animal.getPosition(), animal);
        animal.addObserver(this);
        return true;
    }

    @Override
    public Vector2d getFreePosition(Vector2d centerPoint) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0) continue;
                Vector2d newPosition = new Vector2d(0, 0).add(centerPoint);
                if (this.objectAt(newPosition.add(new Vector2d(x, y))) != null) {
                    return newPosition;
                }
            }
        }
        return null;
    }

    @Override
    public void run() {
        Integer size = this.animalMap.size();
        if (size.equals(0)) return;

        List<Animal> animalListConverted = new LinkedList<>(this.animalMap.values());
        for (Animal animal : animalListConverted) {
            animal.moveTo(animal.findNewPosition());
        }
    }

    @Override // no i chuj zhakuje xD
    public boolean canMoveTo(Vector2d position) {
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {

        return this.animalMap.containsKey(position);
    }

    @Override
    public Object objectAt(Vector2d position) {

        return this.animalMap.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Animal a) {
        //TODO: send to Harness
        this.animalMap.remove(oldPosition, a);
        this.animalMap.put(a.getPosition(), a);
    }

    protected abstract Rectangle getMapCorners();

    public String toString() {
        Rectangle result = this.getMapCorners();
        return this.mapVisualizer.draw(result.lowerLeft, result.upperRight);

    }
}
