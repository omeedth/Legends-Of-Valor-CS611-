/*
 *  Author: Alex Thomas
 *  Creation Date: 10/29/2020
 *  Purpose: Using a Creational Design Pattern to generate the Tile objects necessary for the game
 * 
 *  Sources:
 *      1. https://www.journaldev.com/1827/java-design-patterns-example-tutorial#creational-patterns - Design Patterns
 *      2. https://dzone.com/articles/design-patterns-for-beginners-with-java-examples - Design Patterns
 * 
 *  Pattern: Factory Pattern
 * 
 */

import java.util.HashMap;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

public class TileFactory {

    /* Data Members */

    /* This is the Singleton Pattern of defining the Object (Already has the object initialized) 
       This would be the Prototype Pattern if I had the Tile class implement a copy() method or clone() method to return copies
    */
    // private static final HashMap<String,Tile> TILES = new HashMap<String,Tile>();

    /* Holds references to Tile classes and we will initialize them on runtime (We don't know the constructor has the required parameters) */
    private static final HashMap<Integer, Class<? extends Tile>> TILES;

    /* TODO: Add all values for the concrete Tile objects */
    public static final int PLAIN = 0;
    public static final int INACCESSIBLE = 1;
    public static final int MONSTER_NEXUS = 2;
    public static final int HERO_NEXUS = 3;
    public static final int BUSH = 4;
    public static final int CAVE = 5;
    public static final int KOULOU = 6;

    /* Constructors */

    /* Logic Method */

    /**
     * NOTE: This is a static block and will run ONCE when the class
     *       is stored into memory. This initializes the static variables
     * 
     * 1. put() all the unique Tile objects in the map 
     * 2. Make the map unmodifiable
     * 
     */
    static {
        /* Initialize the set of tiles */
        TILES = new HashMap<Integer, Class<? extends Tile>>();

        /* TODO: Add all Tile classes */
        TILES.put(PLAIN, PlainTile.class);
        TILES.put(INACCESSIBLE, InaccessibleTile.class);
        TILES.put(MONSTER_NEXUS, MonsterNexusTile.class);
        TILES.put(HERO_NEXUS, HeroNexusTile.class);
        TILES.put(BUSH, BushTile.class);
        TILES.put(CAVE, CaveTile.class);
        TILES.put(KOULOU, KoulouTile.class);

        /* Make unmodifiable */
        Collections.unmodifiableMap(TILES);
    }

    public static boolean tileIdExists(int tileId) {
        return TILES.containsKey(tileId);
    }

    /**
     * 
     * @param tileName - The name of the Tile you would like to instantiate
     * @return - The Tile object associated with that tile name
     */
    public static Tile getTile(int tileId, Coordinate2D coords, int laneIndex) {

        /* Using the HashMap and Reflection to instantiate objects at runtime */
        Class<? extends Tile> tile = TILES.getOrDefault(tileId, null);
        try {
            return (tile != null ? tile.getConstructor(Coordinate2D.class, int.class).newInstance(coords, laneIndex) : null);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }
        return null;

        /* Alternatively, we can have a switch statement that initializes the specific Tile object with the Coordinate2D object */

        /* For the semi-Singelton Pattern method where we have a HashMap of the actual tile objects */
        // return TILES.getOrDefault(tileName, null);
    }

}
