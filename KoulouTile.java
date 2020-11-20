/*
 *  Author: Alex Thomas
 *  Creation Date: 11/20/2020
 *  Purpose: Defines a Tile that gives players additional strength
 * 
 */

/* External Imports */
import java.util.List;
import java.util.Scanner;

/* Internal Imports */

public class KoulouTile extends Tile implements Interactable {
    
    /* Static Members */
    public static char TILE_CHAR = 'K';

    /* Data Members */

    /* Constructors */

    public KoulouTile(Coordinate2D coords) {
        super(coords,TILE_CHAR);
    }

    /* Accessor Methods */

    /* Mutator Methods */

    /* Logic Methods */

    @Override
    public void interact(Object obj) {
        // TODO: Koulou Code - Add additional Strength to the player while on this Tile
    }

}
