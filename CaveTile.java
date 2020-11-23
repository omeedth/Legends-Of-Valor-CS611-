/*
 *  Author: Alex Thomas
 *  Creation Date: 11/20/2020
 *  Purpose: Defines a Tile that gives players additional agility
 * 
 */

/* External Imports */
import java.util.List;
import java.util.Scanner;

/* Internal Imports */

public class CaveTile extends Tile implements Interactable {
    
    /* Static Members */
    public static char TILE_CHAR = 'C';

    /* Data Members */

    /* Constructors */

    public CaveTile(Coordinate2D coords, int landIndex) {
        super(coords,TILE_CHAR, landIndex);
    }

    /* Accessor Methods */

    /* Mutator Methods */

    /* Logic Methods */

    @Override
    public void interact(Object obj) {
        // TODO: Cave Code - Add additional Agility to the player while on this Tile
    }

}
