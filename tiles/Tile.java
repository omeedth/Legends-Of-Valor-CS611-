package tiles;

/*
 *  Author: Alex Thomas
 *  Creation Date: 10/29/2020
 *  Purpose: Defines a Tile object to be used in a board
 * 
 */

/* External Imports */
import java.util.List;
import java.util.ArrayList;

public abstract class Tile {
    
    /* Static Members */
    public static char DEFAULT_TILE_CHAR = 'D';

    /* Data Members */
    private static int counter = 0;     // If we decide to use Singleton Pattern

    private Coordinate2D coords;
    private List<TileRepresentable> placedPieces;
    private char tileChar;
    private int id;                     // Unique ID for the concrete Tile objects (we can tie this in using static counter variable)

    /* Constructors */

    public Tile(Coordinate2D coords, char tileChar) {
        this.coords = coords;
        this.tileChar = tileChar;
        this.placedPieces = new ArrayList<TileRepresentable>();
    }

    // Require all tiles to define coordinates
    public Tile(Coordinate2D coords) {
        this(coords,DEFAULT_TILE_CHAR);
    }

    /* Accessor Methods */

    public Coordinate2D getCoords() {
        return this.coords;
    }

    public TileRepresentable getPieceAt(int index) {
        return this.placedPieces.get(index);
    }

    public TileRepresentable getPiece() {
        if (isEmpty()) return null;
        return this.getPieceAt(0);
    }

    public boolean isEmpty() {
        return this.placedPieces.isEmpty();
    }

    /* Mutator Methods */

    public void setPiece(TileRepresentable obj) {
        this.placedPieces.clear();
        this.placedPieces.add(obj);
    }

    public void addPiece(TileRepresentable obj) {
        this.placedPieces.add(obj);
    }

    public boolean removePiece(TileRepresentable obj) {
        return this.placedPieces.remove(obj);
    }

    public TileRepresentable removePiece() {
        if (isEmpty()) return null;
        return this.placedPieces.remove(0);
    }

    public TileRepresentable removePieceAt(int index) {
        return this.placedPieces.remove(index);
    }
    
    /* Additional Methods */

    @Override
    public String toString() {
        return String.format("<%s: Top Piece: %s, Coordinates: %s>",this.getClass().getSimpleName(),this.getPiece(),this.coords);
    }

    public char toCharacter() {
        return this.tileChar;
    }

}
