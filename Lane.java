import java.util.function.Function;

/*
 *  Author: Alex Thomas
 *  Creation Date: 11/19/2020
 *  Purpose: Defines a Lane object which contains a 2D array of Tiles and is associated to a map that contains Lane objects
 * 
 */

/* External Imports */
import java.util.List;
import java.util.ArrayList;

public class Lane extends Board {
    
    /* Final/Static Data Members */
    public static final String DEFAULT_ALIAS = "Default Name";

    /* Data Members */
    private String alias;
    private List<Hero> heroes;
    private Coordinate2D frontierCoordinate;

    /* Constructors */

    public Lane(String alias, int width, int height) {
        super(width,height);     
        this.alias = alias;   
        this.heroes = new ArrayList<>();
        this.frontierCoordinate = new Coordinate2D();
    }

    public Lane(String alias, int dimensions) {
        this(alias, dimensions, dimensions);
    }

    public Lane(int width, int height) {
        this(DEFAULT_ALIAS, width, height);   
    }

    public Lane(int dimensions) {
        this(dimensions, dimensions);   
    }

    /* Accessor Methods */

    public String getAlias() {
        return alias;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public Coordinate2D getFrontierCoordinate() {
        return frontierCoordinate;
    }

    /* Mutator Methods */

    public void generate(Function<Board,int[][]> tileIdMatrixFunction) {
        int[][] tileIds = tileIdMatrixFunction.apply(this);
        // PrintUtility.printMatrix(tileIds);
        boolean dimensionsMatch = this.fillMatrixFromTileIdMatrix(tileIds);
        // System.out.println("Dimensions Match: " + dimensionsMatch);
    }

    /* Logic Methods */

    // @Override
    // public String toString() {
    //     return String.format("<Lane: Width: %d, Height: %d>",this.getWidth(),this.getHeight());
    // }

}

