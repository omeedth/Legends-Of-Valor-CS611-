package tiles;

/*
 *  Author: Alex Thomas
 *  Creation Date: 10/29/2020
 *  Purpose: Defines a Tile that is inaccessible by entities
 *              1. No Players or Monsters can come here
 * 
 */

public class InaccessibleTile extends Tile {
    
    /* Static Members */
    public static char TILE_CHAR = '&';

    /* Data Members */

    /* Constructors */

    public InaccessibleTile(Coordinate2D coords) {
        super(coords,TILE_CHAR);
    }

    /* Accessor Methods */

    /* Mutator Methods */

    /* Logic Methods */

}
