public class Rectangle {
    public Vector2d lowerLeft;
    public Vector2d upperRight;

    public Rectangle(Vector2d l, Vector2d r){
        upperRight = r;
        lowerLeft = l;
    }

    public String toString(){
        return "ll: " + lowerLeft + " | ur: " + upperRight;
    }

    public boolean isInside(Vector2d v){
        return (v.x < upperRight.x && v.y < upperRight.y) && (v.x >= lowerLeft.x && v.y >= lowerLeft.y);
    }

    public Integer area(){
        return Math.abs((upperRight.x-lowerLeft.x)*(upperRight.y-lowerLeft.y));
    }

    public boolean equals(Object o){
        if(this == o){
            return true;
        }

        if(!(o instanceof Rectangle)){
            return false;
        }
        Rectangle r = (Rectangle) o;
        return r.upperRight.equals(this.upperRight) &&
                r.lowerLeft.equals(this.lowerLeft);
    }
}