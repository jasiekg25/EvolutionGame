public class Grass {

    public static Integer DEFAULT_ENERGY_VALUE = 1;
    private Vector2d position;
    private Integer energy;

    public Grass(Vector2d position){
        this.position = position;
        this.energy = DEFAULT_ENERGY_VALUE;
    }

    public Grass(Vector2d position, Integer energy){
        this(position);
        this.energy = energy;
    }

    public void grow(){
        energy += 1;
    }

    public Integer getEnergyValue(){
        return energy;
    }

    public Vector2d getPosition(){
        return position;
    }

    public String toString(){
        return "*";
    }


}
