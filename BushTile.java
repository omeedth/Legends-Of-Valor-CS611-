/*
 *  Author: Alex Thomas
 *  Creation Date: 11/20/2020
 *  Purpose: Defines a Tile that gives players additional dexterity
 * 
 */

/* External Imports */
import java.util.List;
import java.util.Scanner;

/* Internal Imports */

public class BushTile extends Tile implements Interactable {
    
    /* Static Members */
    public static char TILE_CHAR = 'B';

    /* Data Members */

    /* Constructors */

    public BushTile(Coordinate2D coords,int laneIndex) {
        super(coords,TILE_CHAR,laneIndex);
    }

    /* Accessor Methods */

    /* Mutator Methods */

    /* Logic Methods */

    @Override
    public void interact(Object obj) {
        // TODO: Bush Code - Add additional Dexterity to the player while on this Tile
    }

}
