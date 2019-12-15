import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Configuration {
    public Integer mapWidth = 100;
    public Integer mapHeight = 30;
    public Integer startEnergy = 50;
    public Integer moveEnergy = 1;
    public Integer plantStartEnergy = 1;
    public Integer jungleWidth = 10;
    public Integer jungleHeight = 10;

    public Configuration() {
        try (Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("parameters.json"))) {
            this.parse(reader);
            System.out.println("Using default .json");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        applyCfg();
    }

    public Configuration(String fname) {
        try (Reader reader = new FileReader(fname)) {
            this.parse(reader);
            System.out.println("Using provided .json");
        } catch (IOException | ParseException e) {
            //what should i do?
        }

        applyCfg();
    }

    private void applyCfg() {
        Animal.COST_PER_MOVE = moveEnergy;
        Animal.INITIAL_ENERGY = startEnergy;
        Grass.DEFAULT_ENERGY_VALUE = plantStartEnergy;
    }

    private void parse(Reader reader) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(reader);

        mapWidth = Math.toIntExact((Long) obj.get("mapWidth"));
        mapHeight = Math.toIntExact((Long) obj.get("mapHeight"));
        startEnergy = Math.toIntExact((Long) obj.get("startEnergy"));
        moveEnergy = Math.toIntExact((Long) obj.get("moveEnergy"));
        plantStartEnergy = Math.toIntExact((Long) obj.get("plantStartEnergy"));
        jungleWidth = Math.toIntExact((Long) obj.get("jungleWidth"));
        jungleHeight = Math.toIntExact((Long) obj.get("jungleHeight"));
    }

    public String toString() {
        return (" mapWidth:" + this.mapWidth +
                "\n startEnergy: " + this.mapHeight +
                "\n moveEnergy: " + this.moveEnergy +
                "\n startEnergy: " + this.startEnergy +
                "\n plantStartEnergy: " + this.plantStartEnergy +
                "\n jungleWidth: " + this.jungleWidth +
                "\n jungleHeight: " + this.jungleHeight);
    }

}