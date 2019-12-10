import java.util.Random;

public enum MapDirection {
    DEG0,
    DEG45,
    DEG90,
    DEG135,
    DEG180,
    DEG225,
    DEG270,
    DEG315;

    private static MapDirection[] directionsArray = MapDirection.values();
    private static String[] directionsString = new String[]{"↑", "↗", "→", "↘", "↓", "↙", "←", "↖"};

    public static MapDirection getDirection(Integer x) {
        return directionsArray[x];
    }

    public static MapDirection getRandomDirection() {
        return getDirection(new Random().nextInt(directionsArray.length));
    }

    public MapDirection getNext() {
        return directionsArray[(this.ordinal() + 1) % directionsArray.length];
    }

    public MapDirection getPrevious() {
        return directionsArray[(this.ordinal() + values().length - 1) % directionsArray.length];
    }

    public Vector2d toUnitVector() {
        switch (this) {
            case DEG0:
                return new Vector2d(0, 1);
            case DEG45:
                return new Vector2d(1, 1);
            case DEG90:
                return new Vector2d(1, 0);
            case DEG135:
                return new Vector2d(1, -1);
            case DEG180:
                return new Vector2d(0, -1);
            case DEG225:
                return new Vector2d(-1, -1);
            case DEG270:
                return new Vector2d(-1, 0);
            case DEG315:
                return new Vector2d(-1, 1);
            default:
                throw new IllegalArgumentException("something went wrong!");
        }
    }

    public String toString() {
        return directionsString[this.ordinal()];
    }
}
