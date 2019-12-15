import java.util.*;
import java.util.stream.Collectors;

public class JungleMap extends AbstractWorldMap {

    public static final Integer GRASS_STEP_PERCENT = 5;
    public static final Integer GRASS_JUNGLE_PERCENT = 60;
    private final PointGenerator pointGenerator;
    private final HashMap<Vector2d, Grass> grassList = new LinkedHashMap<>();
    private Rectangle mapSize;
    private final Rectangle jungleSize;
    private Integer maxAge = 0;

    public JungleMap (Vector2d mapSize, Vector2d jungleSize){
        this.mapSize = new Rectangle(new Vector2d(0, 0), mapSize);

        Vector2d jungleLowerLeft = new Vector2d(0,0);
        jungleLowerLeft.x = (mapSize.x - jungleSize.x) / 2;
        jungleLowerLeft.y = (mapSize.y - jungleSize.y) / 2;

        this.jungleSize = new Rectangle(jungleLowerLeft, jungleLowerLeft.add(jungleSize));
        pointGenerator = new PointGenerator(this);
        this.prepareArea(this.jungleSize, null, GRASS_JUNGLE_PERCENT);
        this.prepareArea(this.mapSize, this.jungleSize, GRASS_STEP_PERCENT);
    }

    private void prepareArea(Rectangle area, Rectangle exclude, Integer percent){
        pointGenerator.getRandomPoints(area, exclude, percent).forEach(v -> {
            Grass grass = new Grass(v);
            grassList.put(v, grass);
        });
    }

    public void spawnGrassInArea (Rectangle area, Rectangle exclude, Integer howMany){
        List<Vector2d> pool = pointGenerator.getPool(area, exclude);
        Collections.shuffle(pool);

        while (!(howMany--).equals(0) && pool.size() > 0){
            Vector2d grassPosition = pool.get(0);
            Grass grass = new Grass(grassPosition);
            grassList.put(grassPosition, grass);
            pool.remove(0);

        }
    }

    public int getGrassNum(){
        return grassList.size();
    }

    // Mapa się zawija
    public Vector2d translatePosition (Vector2d position){
        Integer x = position.x;
        Integer y = position.y;

        if(x < 0){
            x = this.mapSize.upperRight.x - 1;
        }
        if(y < 0){
            y = this.mapSize.upperRight.y - 1;
        }

        return new Vector2d(x % this.mapSize.upperRight.x, y % this.mapSize.upperRight.y);
    }

    @Override
    public Vector2d getFreePosition (Vector2d origin){
        List<Vector2d> freePositions = new ArrayList<>();
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if (x == 0 && y == 0) {
                    continue;
                }
                Vector2d newPosition = origin.add(new Vector2d(x, y));
                newPosition = this.translatePosition(newPosition);
                if (this.objectAt(newPosition) == null) {
                    freePositions.add(newPosition);
                }
            }
        }
        if( !freePositions.isEmpty()){
            Collections.shuffle(freePositions);
            return freePositions.get(0);
        }
        return null;
    }

    @Override
    protected Rectangle getMapCorners() {
        return this.mapSize;
    }

    @Override
    public Object objectAt (Vector2d position){
        Object object = super.objectAt(position);
        if(object instanceof Collection <?>){
            LinkedList <Animal> animalList = new LinkedList<Animal>((Collection) object);
            if(animalList.size() > 0){
                return object;
            }
        }
        return grassAt(position);
    }

    public Grass grassAt(Vector2d position){
        return grassList.get(position);
    }

    public void removeGrass(Grass grass){
        grassList.remove(grass.getPosition());
    }


    @Override
    public void run(){
        if(animalMap.size() < 2){
            System.out.println("Game Over, less then 2 animals. The world is dead!");
            System.exit(0);
        }

        // time to clean corpses
        animalMap.values().removeIf(a -> a.getEnergy() <= 0);

        LinkedList<Animal> animals = new LinkedList<>(animalMap.values());
        for (Animal animal: animals) {
            animal.chooseDirection();

            Vector2d newPosition = animal.findNewPosition();
            newPosition = this.translatePosition(newPosition);
            animal.moveTo(newPosition);

            LinkedList <Animal> list = new LinkedList<>(animalMap.get(newPosition));
            Grass grass = grassAt(newPosition);
            if(grass != null){
                if(list.size() > 1){
                    Animal strongest = list.stream().max(Comparator.comparing(Animal::getEnergy)).orElseThrow(NoSuchElementException::new);
                    List<Animal> strongestList = list.stream().filter(anim -> anim.getEnergy().equals(strongest.getEnergy())).collect(Collectors.toList());
                    if(strongestList.size() > 1){
                        int toEat = (int) Math.ceil(grass.getEnergyValue() / (double) strongestList.size());
                        for (Animal a : strongestList) {
                            eatGrass(new Grass(new Vector2d(0,0), toEat), a);
                        }
                    } else {
                        eatGrass(grass, animal);
                    }
                } else {
                    eatGrass(grass, animal);
                }
                removeGrass(grass);
            }
            if(list.size() > 1){
                list.sort(Comparator.comparingInt(Animal :: getEnergy));
                Animal a1 = list.get(0);
                Animal a2 = list.get(1);

                Optional<Animal> a3 = Optional.ofNullable(a1.merge(a2));
                a3.ifPresent(this :: place);
            }
        }
        grassList.values().forEach(Grass::grow);
        animalMap.values().forEach(a -> a.addAge(1));
        spawnGrassInArea(this.mapSize, this.jungleSize, 1);
        spawnGrassInArea(this.jungleSize, null, 1);
    }

    public void eatGrass(Grass grass, Animal animal){
        int newEnergy = animal.getEnergy() + grass.getEnergyValue();
        animal.setEnergy(newEnergy);
        this.grassList.remove(grass.getPosition());

    }
}