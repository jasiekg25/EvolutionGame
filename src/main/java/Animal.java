import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Animal {

    public static int INITIAL_ENERGY = 100;
    public static int COST_PER_MOVE = 1;
    private static int animalCounter = 0;
    private final IWorldMap map;
    private Vector2d position;
    private Genotype genotype;
    private MapDirection direction;
    private int energy;
    private int age;
    private int animalID;
    private List<IPositionChangeObserver> observers = new LinkedList<>();

    public Animal(IWorldMap map) {
        this.map = map;
        this.direction = MapDirection.getRandomDirection();
        this.genotype = new Genotype();
        this.age = 0;
        animalID = animalCounter++;
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.position = initialPosition;
    }

    public Animal(IWorldMap map, Vector2d initialPosition, int energy) {
        this(map, initialPosition);
        this.energy = energy;
    }

    public Animal(IWorldMap map, Vector2d initialPosition, int energy, Genotype genotype) {
        this(map, initialPosition, energy);
        this.genotype = genotype;
    }

    public String toString() {
        return this.direction.toString();
    }

    public int getAnimalID() {
        return this.animalID;
    }

    public void chooseDirection() {
        MapDirection direction = MapDirection.getDirection(this.genotype.getRandom());
        this.direction = direction;
    }

    public Vector2d findNewPosition() {
        Vector2d unit = this.direction.toUnitVector();
        Vector2d newPosition = this.position.add(unit);
        return newPosition;
    }

    public void moveTo(Vector2d newPosition) {
        Vector2d oldPosition = this.position;
        this.position = newPosition;
        this.notifyObservers(oldPosition);
        this.addEnergy(-COST_PER_MOVE);
    }

    public void eat (Grass grass){
        this.addEnergy(grass.getEnergyValue());
        // Dodać usuwanie zwierząt
        //DELETE_GRASS!
    }

    public int getEnergy(){
        return this.energy;
    }

    public void addEnergy(int energy){
        this.energy += energy;
        if(this.energy < 0) this.energy = 0;
    }

    public void addAge (int age){
        this.age += age;
    }

    public int getAge() {
        return this.age;
    }

    public Vector2d getPosition() {
        return this.position;
    }

    public Genotype getGenotype() {
        return this.genotype;
    }

    private static int getMinimalEnergyToReproduce(){
        return INITIAL_ENERGY / 2;
    }

    public void addObserver (IPositionChangeObserver observer){
        this.observers.add(observer);
    }

    public void removeObserver (IPositionChangeObserver observer){
        this.observers.remove(observer);
    }


    // o co kaman
    private void notifyObservers (Vector2d oldPosition){
        this.observers.forEach(v -> v.positionChanged(oldPosition, this));
    }

// ROZMNAŻANIE SKOŃCZYĆ
    public Animal merge (Animal mum){

        Animal dad = this;

        if(dad.getEnergy() < Animal.getMinimalEnergyToReproduce() || mum.getEnergy() < Animal.getMinimalEnergyToReproduce()){
            return null;
        }
        // Good Bye 'NullPoint'
        Optional<Vector2d> kidPosition = Optional.ofNullable(dad.map.getFreePosition(dad.getPosition()));
        
        // Good Bye Kid
        if(kidPosition.isEmpty()){
            return null;
        }

        int kidEnergy = dad.getEnergy() / 4;
        dad.addEnergy(-kidEnergy);
        int mumKidEnergy = mum.getEnergy() / 4;
        mum.addEnergy(-mumKidEnergy);

        kidEnergy += mumKidEnergy;
        Animal kidAnimal = new Animal (dad.map, kidPosition.get(), kidEnergy, dad.genotype.merge(mum.genotype));
        return kidAnimal;
    }

}
