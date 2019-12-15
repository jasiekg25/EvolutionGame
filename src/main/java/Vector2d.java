public class Vector2d {

    public int x;
    public int y;

    public Vector2d() {
        this.x = 2;
        this.y = 2;
    }

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return ("(" + this.x + "," + this.y + ")");
    }


    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other) {
        Vector2d upperRight = new Vector2d();
        upperRight.x = Math.max(this.x, other.x);
        upperRight.y = Math.max(this.y, other.y);
        return upperRight;
    }

    public Vector2d lowerLeft(Vector2d other) {
        Vector2d lowerLeft = new Vector2d();
        lowerLeft.x = Math.min(this.x, other.x);
        lowerLeft.y = Math.min(this.y, other.y);
        return lowerLeft;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d((this.x + other.x), (this.y + other.y));
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d((this.x - other.x), (this.y - other.y));
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        return other instanceof Vector2d && this.x == ((Vector2d) other).x && this.y == ((Vector2d) other).y;
    }

    public Vector2d opposite() {
        return new Vector2d(-this.x, -this.y);
    }


    @Override
    public int hashCode() {
        int tmp = (y + ((x + 1) / 2));
        return x + (tmp * tmp);
    }
}
