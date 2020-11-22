/*
 *  Author: Alex Thomas
 *  Creation Date: 10/29/2020
 *  Purpose: Defines a Tile that allows a player to purchase from a market
 *              1. Players can stop here to buy/sell items
 * 
 */

/* External Imports */

/* Internal Imports */

public abstract class NexusTile extends Tile {
    
    /* Static Members */
    public static char TILE_CHAR = 'N';

    /* Data Members */

    /* Constructors */

    public NexusTile(Coordinate2D coords) {
        super(coords,TILE_CHAR);
    }

    /* Accessor Methods */

    /* Mutator Methods */

    /* Logic Methods */

}
