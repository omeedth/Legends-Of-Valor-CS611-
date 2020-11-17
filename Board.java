/*
 *  Author: Alex Thomas
 *  Creation Date: 10/29/2020
 *  Purpose: Defines a Board object containing Tile objects
 * 
 */

/* External Imports */
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import jcolor.*;

public class Board {
    
    /* Data Members */
    private Tile[][] tiles;
    private int width, height;

    /* Constructors */

    public Board(int width, int height) {
        /* Empty Board */
        this.setWidth(width);
        this.setHeight(height);
        tiles = new Tile[this.width][this.height];
    }

    public Board(int dimensions) {
        this(dimensions,dimensions);
    }

    /* Accessor Methods */

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Tile get(int x, int y) {
        assert (x >= 0 && x < this.width) && (y >= 0 && y < this.height);
        y = cartesianYToComputerY(y);
        return this.tiles[y][x]; // TODO: Check if this causes an error
    }

    public Tile get(Coordinate2D cartesianCoords) {
        return get(cartesianCoords.getX(),cartesianCoords.getY());
    }

    /* Aliases for "get()" methods so it works with preexisting code */

    public Tile getTile(int x, int y) {
        return get(x,y);
    }

    public Tile getTile(Coordinate2D cartesianCoords) {
        return get(cartesianCoords);
    }

    /*---------------------------------------------------------------*/

    /* Methods so it works with preexisting code */

    public boolean isOccupied(int x, int y) {
        assert (x >= 0 && x < this.getWidth()) && (y >= 0 && y < this.getHeight());
        return !get(x,y).isEmpty();
    }

    public boolean isOccupied(Coordinate2D cartesianCoords) {
        return isOccupied(cartesianCoords.getX(),cartesianCoords.getY());
    }

    /*-------------------------------------------*/

    public Coordinate2D cartesianCoordinatesToComputerCoordinates(Coordinate2D cartesianCoords) {
        return new Coordinate2D(cartesianCoords.getX(),cartesianYToComputerY(cartesianCoords.getY()));
    }

    public int cartesianYToComputerY(int y) {
        return this.height - (y + 1);
    }

    /* Mutator Methods */

    public boolean setWidth(int width) {
        if (width < 0) {
            this.width = 0;
            return false;
        }
        this.width = width;
        return true;
    }

    public boolean setHeight(int height) {
        if (height < 0) {
            this.height = 0;
            return false;
        }
        this.height = height;
        return true;
    }

    public boolean fillMatrixFromTileIdMatrix(int [][] tileIds) {
        // Dimensions of the two matrices match - Continue
        if (tileIds != null && this.width == tileIds.length && this.width != 0 && this.height == tileIds[0].length && this.height != 0) {
            for (int row = 0; row < this.height; row++) {
                for (int col = 0; col < this.width; col++) {
                    Coordinate2D cartesianCoords = new Coordinate2D(col, row);   // X: col, Y: row
                    Coordinate2D currCoords = cartesianCoordinatesToComputerCoordinates(cartesianCoords);   // TODO: Fix this 
                    Tile currTile = TileFactory.getTile(tileIds[row][col], currCoords);
                    this.tiles[row][col] = currTile;
                }
            }
            return true;
        } 
        
        // Dimensions don't match
        else {
            return false;
        }
    }

    // TODO: retrieve the nexus tile for the Heroes
    //       1. Check if the nexus tile is empty
    //       2. Check if this nexus is the same lane as the hero
    public void getHeroNexus(Hero hero) {
        /*
         * Given the Hero object (Contains Coordinate2D)
         * So figure out Nexus Tile on the lane
         */
        
    }

    /* Logic Methods */

    public Coordinate2D getLocationOnBoard(Set<Class<? extends Tile>> desiredTileTypes) {
        
        // Create Coordinate2D to return later
        Coordinate2D ret = null;

        // Choose random coordinate 
        int x = (int) Math.floor(Math.random() * this.getWidth());
        int y = (int) Math.floor(Math.random() * this.getHeight());
        ret = new Coordinate2D(x,y);

        // Run BFS until we find it's a tile of the type we desire
        boolean foundTile = false;
        Set<Tile> visited = new HashSet<Tile>();
        Queue<Tile> toVisit = new LinkedList<Tile>();
        toVisit.add(this.get(ret));        

        while (!toVisit.isEmpty() && !foundTile) {
            
            // The current Tile we are visiting
            Tile tile = toVisit.poll();
            visited.add(tile);

            // Check if current tile is a desired tile, and exit if so
            if (desiredTileTypes.contains(tile.getClass())) {
                System.out.println(tile.getClass() + " at " + tile.getCoords() + " is inside set: " + desiredTileTypes);
                foundTile = true;
                ret = new Coordinate2D(tile.getCoords());
                continue;
            }

            System.out.println(tile.getClass() + " at " + tile.getCoords() + " is NOT inside set: " + desiredTileTypes);

            // Get neighbors (valid coordinates up, right, down, left)
            List<Tile> neighbors = getNeighbors(tile);

            // Go through each unvisited neighbor and add it into the toVisit queue
            for (Tile neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    toVisit.add(neighbor);
                }
            }

        }

        if(!foundTile) {
            ret = null;
        }

        return ret;

    }

    public boolean insideBoard(Coordinate2D coords) {
        int x = coords.getX();
        int y = coords.getY();
        return (x >= 0 && x < this.width) && (y >= 0 && y < this.height);
    }

    // Coordinate within the board
    public List<Tile> getNeighbors(Coordinate2D coords) {
        List<Tile> neighbors = new ArrayList<Tile>(4);

        // Check for valid neighbors (if within the grid)
        Coordinate2D up = new Coordinate2D(coords.getX(),coords.getY() + 1);
        Coordinate2D right = new Coordinate2D(coords.getX() + 1,coords.getY());
        Coordinate2D down = new Coordinate2D(coords.getX(),coords.getY() - 1);
        Coordinate2D left = new Coordinate2D(coords.getX() - 1,coords.getY());

        if (insideBoard(up)) neighbors.add(this.get(up));
        if (insideBoard(right)) neighbors.add(this.get(right));
        if (insideBoard(down)) neighbors.add(this.get(down));
        if (insideBoard(left)) neighbors.add(this.get(left));

        return neighbors;
    }

    public void movePiece(TileRepresentable obj, Tile source, Tile destination) {
        if (source.removePiece(obj)) {
            obj.move(destination);
            destination.addPiece(obj);
        } else {
            throw new IllegalAccessError("This object does not exist on the source tile!");
        }
    }

    // Tile within the board
    public List<Tile> getNeighbors(Tile tile) {
        return getNeighbors(tile.getCoords());
    }

    /* Named displayMap so it fits with the preexisting code */
    public void displayMap() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        String res = "";

        // Top portion of the Board
        for (int col = 0; col < this.getWidth(); col++) {
            res += "+---------";
        }
        res += "+\n";

        for (int row = 0; row < this.getHeight(); row++) {

            res += "\n";

            // Middle portion of Board containing the actual tile values
            for (int col = 0; col < this.getWidth(); col++) {                

                Tile tile = this.tiles[row][col];
                res += "|    ";

                // Show the piece on the tile (First one)
                if (!tile.isEmpty()) {
                    res += String.format(Ansi.colorize("%c", Attribute.GREEN_TEXT()), tile.getPiece().represent());
                }

                // If no pieces on the tiles, show the representation of the tile
                else {

                    if (tile instanceof CommonTile) {
                        res += String.format(Ansi.colorize("%c", Attribute.GREEN_TEXT()), tile.toCharacter());
                    } else if (tile instanceof InaccessibleTile) {
                        res += String.format(Ansi.colorize("%c", Attribute.RED_TEXT()), tile.toCharacter());
                    } else if (tile instanceof MarketTile) {
                        res += String.format(Ansi.colorize("%c", Attribute.YELLOW_TEXT()), tile.toCharacter());
                    }

                }                

                res += "    ";

            }
            res += "|\n\n";

            // Bottom portion of the row
            for (int col = 0; col < this.getWidth(); col++) {
                res += "+---------";
            }
            res += "+\n";

        }

        return res;
    }

}
