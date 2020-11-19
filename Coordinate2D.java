/*
 *  Author: Alex Thomas
 *  Creation Date: 10/29/2020
 *  Purpose: Defines the coordinates on a 2D plane (x,y)
 * 
 */

public class Coordinate2D {
    
    /* Data Members */
    private int x;
    private int y;

    /* Constructors */

    public Coordinate2D(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public Coordinate2D(Coordinate2D coords) {
        this(coords.getX(),coords.getY());
    }

    public Coordinate2D() {
        this(0,0);
    }

    /* Accessor Methods */

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    /* Mutator Methods */

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void set(Coordinate2D coords) {
        setX(coords.getX());
        setY(coords.getY());
    }

    /* Additional Methods */

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

}
