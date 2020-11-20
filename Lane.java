/*
 *  Author: Alex Thomas
 *  Creation Date: 11/19/2020
 *  Purpose: Defines a Lane object which contains a 2D array of Tiles and is associated to a map that contains Lane objects
 * 
 */

/* External Imports */

public class Lane extends Board {
    
    /* Final/Static Data Members */

    /* Data Members */
    private String alias;

    /* Constructors */

    public Lane(String alias, int width, int height) {
        super(width,height);     
        this.alias = alias;   
    }

    public Lane(String alias, int dimensions) {
        this(alias, dimensions,dimensions);
    }

    /* Accessor Methods */

    /* Mutator Methods */

    /* Logic Methods */

    @Override
    public String toString() {
        return "TODO: Make special Lane String";
    }

}

