/*
 *  Author: Alex Thomas
 *  Creation Date: 10/29/2020
 *  Purpose: Defines a concrete Tile that:
 *              1. Is accessible by the player (enterable) 
 *              2. Has a chance of encountering monsters
 * 
 * 
 */

/* External Imports */
import jcolor.Ansi;
import jcolor.Attribute;

/* Internal Imports */

public class PlainTile extends Tile implements Interactable {
    
    /* Static Members */
    public static char TILE_CHAR = 'P';
    // public static double ENCOUNTER_CHANCE = .4;

    /* Data Members */

    /* Constructors */

    public PlainTile(Coordinate2D coords, int laneIndex) {
        super(coords,TILE_CHAR, laneIndex);
    }

    /* Accessor Methods */

    @Override
    public char toCharacter() {
        if (this.isEmpty()) return TILE_CHAR;
        return this.getPiece().represent();
    }

    /* Mutator Methods */

    /* Logic Methods */

    @Override
    public void interact(Object obj) {
        // TODO: Plain Tile Logic - No Special Attribute
    }

}
