/*
 *  Author: Alex Thomas
 *  Creation Date: 10/29/2020
 *  Purpose: Defines a Tile that allows a player to purchase from a market
 *              1. Players can stop here to buy/sell items
 * 
 */

/* External Imports */
import java.util.List;
import java.util.Scanner;

/* Internal Imports */

public class NexusTile extends Tile implements Interactable {
    
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

    @Override
    public void interact(Object obj) {
        // TODO: Nexus Code:
        //          1. (If Hero run Market Code)
        //          2. Spawn's Heroes and Monsters
    }

}
