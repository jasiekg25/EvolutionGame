
import java.sql.SQLOutput;
import java.util.Collections;
import java.util.List;

public class World {

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args){

        Configuration config;
        Integer howManyAnimals, iterLimit;

        if(args.length == 3){

            iterLimit = Integer.parseInt(args[0]);
            howManyAnimals = Integer.parseInt(args[1]);
            config = new Configuration(args[2]);
        }else{
            config = new Configuration();
            howManyAnimals = 300;
            iterLimit = 1000;
        }

        Vector2d jungleSize = new Vector2d(config.jungleWidth, config.jungleHeight);
        Vector2d mapsize = new Vector2d(config.mapWidth, config.mapHeight);


        System.out.println("Animal number: " + howManyAnimals);
        System.out.println("Iteration Limit: " + iterLimit);
        System.out.println(config.toString());

        try{
            int cnt = 0;

            IWorldMap map = new JungleMap(mapsize, jungleSize);
            System.out.println(map.toString());
            List<Vector2d> points = new PointGenerator(map).getPool(new Rectangle(new Vector2d(0,0), mapsize), null);
            Collections.shuffle(points);
            points.subList(0, howManyAnimals).forEach(v -> map.place(new Animal(map, v, config.startEnergy)));

            int iterations = 0;
            while(iterations < iterLimit){
                if(iterations % 10000 == 0) System.out.println(map.toString());

                map.run();
                cnt++;
                iterations++;
            }
        }catch (IllegalArgumentException | IllegalStateException ex){

            System.exit(666);
        }

    }
}
