/*
 *  Author: Alex Thomas
 *  Creation Date: 10/29/2020
 *  Purpose: Defines a Board object containing Tile objects
 * 
 */

/* External Imports */
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.Queue;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import jcolor.*;

// TODO: Consider adding a Lane Object that the board has
public class Board {

    /* Final/Static Data Members */
    public static final char NULL_TILE_CHARACTER = '~';
    
    /* Data Members */
    private Tile[][] tiles;
    private int width, height;

    // Board Info - TODO: Consider making a class BoardInfo.java to contain all of this info and return those objects
    private List<Tile> disconnectedGraphs;

    /* Constructors */

    public Board(int width, int height) {
        /* Empty Board */
        this.setWidth(width);
        this.setHeight(height);
        tiles = new Tile[this.height][this.width];

        // Initializing BoardInfo Variables
        disconnectedGraphs = new ArrayList<>();
    }

    public Board(int dimensions) {
        this(dimensions,dimensions);
    }

    /* Accessor Methods */

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public List<Tile> getDisconnectedGraphs() {
        return disconnectedGraphs;
    }

    public Tile get(int x, int y) {
        assert (x >= 0 && x < this.width) && (y >= 0 && y < this.height);
        y = cartesianYToComputerY(y);
        return this.tiles[y][x]; // TODO: Check if this causes an error
    }

    public Tile get(Coordinate2D cartesianCoords) {
        return get(cartesianCoords.getX(),cartesianCoords.getY());
    }

    /* Aliases for "get()" methods so it works with preexisting code */

    public Tile getTile(int x, int y) {
        return get(x,y);
    }

    public Tile getTile(Coordinate2D cartesianCoords) {
        return get(cartesianCoords);
    }

    /*---------------------------------------------------------------*/

    /* Methods so it works with preexisting code */

    public boolean isOccupied(int x, int y) {
        assert (x >= 0 && x < this.getWidth()) && (y >= 0 && y < this.getHeight());
        return !get(x,y).isEmpty();
    }

    public boolean isOccupied(Coordinate2D cartesianCoords) {
        return isOccupied(cartesianCoords.getX(),cartesianCoords.getY());
    }

    /*-------------------------------------------*/

    public Coordinate2D cartesianCoordinatesToComputerCoordinates(Coordinate2D cartesianCoords) {
        return new Coordinate2D(cartesianCoords.getX(),cartesianYToComputerY(cartesianCoords.getY()));
    }

    public int cartesianYToComputerY(int y) {
        return this.height - (y + 1);
    }

    /* Mutator Methods */

    public boolean setWidth(int width) {
        if (width < 0) {
            this.width = 0;
            return false;
        }
        this.width = width;
        return true;
    }

    public boolean setHeight(int height) {
        if (height < 0) {
            this.height = 0;
            return false;
        }
        this.height = height;
        return true;
    }

    public boolean fillMatrixFromTileIdMatrix(int [][] tileIds) {
        // Dimensions of the two matrices match - Continue
        if (tileIds != null && this.height == tileIds.length && this.height != 0 && this.width == tileIds[0].length && this.width != 0) {
            for (int row = 0; row < this.height; row++) {
                for (int col = 0; col < this.width; col++) {
                    Coordinate2D cartesianCoords = new Coordinate2D(col, row);   // X: col, Y: row
                    Coordinate2D currCoords = cartesianCoordinatesToComputerCoordinates(cartesianCoords);   // TODO: Fix this 
                    Tile currTile = TileFactory.getTile(tileIds[row][col], currCoords);
                    this.tiles[row][col] = currTile;
                }
            }
            return true;
        } 
        
        // Dimensions don't match
        else {
            return false;
        }
    }

    /* Logic Methods */

    // Check whether or not the graph contains this specific Tile object with the memory address
    // TODO: Change this to BFS so that it works on disconnected graphs too!
    public boolean contains(Tile tile, Set<Class<? extends Tile>> blackListedTileTypes) {
        List<Tile> tiles = BFS(blackListedTileTypes, (otherTile) -> {
            return tile == otherTile;
        });
        return tiles.get(tiles.size() - 1) == tile; // Check if the memory address of the two Tile objects are the same
    }

    public List<Tile> getTilesAsList(Set<Class<? extends Tile>> blackListedTileTypes) {
        // System.out.println("Getting Tiles As List");
        return BFS(blackListedTileTypes);
    }

    // Coordinate2D of where the piece is on the board right now
    public List<Tile> getPossibleTeleportTiles(Coordinate2D coordinates) {
        // Get all Tile objects in:
        //  1. Different Lanes (Only one lane at a time AKA teleporting from TOP to BOT is NOT allowed) TODO: Incomplete
        //  2. Tile objects the below the farthest point a Hero object has explored in a lane           TODO: Incomplete
        //  3. Tile objects below ALL monsters or on the same level                                     TODO: Incomplete

        // TODO: add a variables or parameters for if you want to rerun BFS to update the Board info with certain blacklisted tiles
        Set<Class<? extends Tile>> blackListedTileTypes = new HashSet<>();
        blackListedTileTypes.add(InaccessibleTile.class);

        // Ensuring we know the disconnected graphs that exist
        if (disconnectedGraphs.size() == 0) {            
            BFS(blackListedTileTypes);
        }

        List<Tile> possibleTeleportTiles = new ArrayList<>();

        // Go through each disconnected graph and 
        for (Tile startTile : disconnectedGraphs) {

            // Figure out if the coordinates passed in exist in the sub graph
            List<Tile> exploredTiles = simpleBFS(startTile, blackListedTileTypes, (tile) -> {
                return tile.getCoords().equals(coordinates);
            });

            // The hero is in this current subgraph (The last explored tile would be the tile where we found the Tile at coordinates)
            if (exploredTiles.get(exploredTiles.size() - 1).getCoords().equals(coordinates)) {
                /* DEBUGGING */
                // System.out.println("Disconnected Graph: " + startTile + " contained the coordinate!");
                /*-----------*/
                continue;
            }

            /* DEBUGGING */
            // System.out.println("\n");
            // System.out.println(PrintUtility.listToString(exploredTiles,false));
            /*-----------*/

            // Otherwise add all the Tile objects in the subgraph as 
            possibleTeleportTiles.addAll(exploredTiles);

        }

        // TODO: Replace "farthestExplored" with the Hero object's actual farthest explored Coordinate2D
        // Coordinate2D farthestExplored = new Coordinate2D(0,4); // Hard Coded Variable
        // return possibleTeleportTiles.stream().filter(tile -> tile.getCoords().getY() <= farthestExplored.getY()).collect(Collectors.toList());

        return possibleTeleportTiles;
    }

    // Coordinate2D of where the piece is on the board right now
    public List<Tile> getPossibleTeleportTiles(Tile tile) {
        return getPossibleTeleportTiles(tile.getCoords());
    }

    // Does not account for disconnected graphs
    public List<Tile> simpleBFS(Tile startTile, Set<Class<? extends Tile>> blackListedTileTypes, Function<Tile,Boolean> stopCondition) {
        // Keep track of all visited Tile objects
        Set<Tile> visited = new HashSet<Tile>();
        return bfsHelper(startTile, visited, blackListedTileTypes, stopCondition);
    }

    public List<Tile> simpleBFS(Tile startTile, Set<Class<? extends Tile>> blackListedTileTypes) {
        return simpleBFS(startTile, blackListedTileTypes, null);
    }

    // Get's all disconnected graphs
    public List<Tile> BFS(Set<Class<? extends Tile>> blackListedTileTypes) {
        return BFS(blackListedTileTypes,null);
        // // Keep track of all visited Tile objects
        // Set<Tile> visited = new HashSet<Tile>();
        // List<Tile> exploredTiles = new ArrayList<>();

        // // Go through every Tile in the graph
        // for (int row = 0; row < this.width; row++) {
        //     for (int col = 0; col < this.height; col++) {
        //         Tile startTile = this.get(col,row);
        //         if (!blackListedTileTypes.contains(startTile.getClass()) && !visited.contains(startTile)) {
        //             disconnectedGraphs.add(startTile);
        //             List<Tile> exploredSegmentTiles = bfsHelper(startTile, visited, blackListedTileTypes, null);
        //             PrintUtility.listToString(exploredSegmentTiles);
        //             exploredTiles.addAll(exploredSegmentTiles);
        //         }
        //     }
        // }

        // return exploredTiles;
    }

    // Get's all disconnected graphs
    public List<Tile> BFS(Set<Class<? extends Tile>> blackListedTileTypes, Function<Tile,Boolean> stopCondition) {
        // Keep track of all visited Tile objects
        Set<Tile> visited = new HashSet<Tile>();
        List<Tile> exploredTiles = new ArrayList<>();

        // Go through every Tile in the graph
        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                Tile startTile = this.get(col,row);
                if (!blackListedTileTypes.contains(startTile.getClass()) && !visited.contains(startTile)) {
                    disconnectedGraphs.add(startTile);
                    List<Tile> exploredSegmentTiles = bfsHelper(startTile, visited, blackListedTileTypes, stopCondition);

                    /* DEBUGGING */
                    PrintUtility.listToString(exploredSegmentTiles);
                    /*-----------*/

                    exploredTiles.addAll(exploredSegmentTiles);
                    if (stopCondition != null && stopCondition.apply(exploredSegmentTiles.get(exploredSegmentTiles.size() - 1))) {
                        return exploredTiles;
                    }                    
                }
            }
        }

        return exploredTiles;
    }

    // Does not work for disconnected graphs
    public List<Tile> bfsHelper(Tile start, Set<Tile> visited, Set<Class<? extends Tile>> blackListedTileTypes, Function<Tile,Boolean> stopCondition) {

        // Create a list of explored Tile objects
        List<Tile> exploredTiles = new ArrayList<Tile>();        
        visited.add(start);

        // If there are no Tiles to avoid or prioritize then just return a random point on the board
        if (stopCondition != null && stopCondition.apply(start)) {
            exploredTiles.add(start);
            return exploredTiles;
        }

        // Run BFS until we have satisfied the stopCondition
        boolean stopConditionMet = false;
        // Set<Tile> visited = new HashSet<Tile>();
        Queue<Tile> toVisit = new LinkedList<Tile>();
        toVisit.add(start);        

        while (!toVisit.isEmpty() && !stopConditionMet) {
            
            // The current Tile we are visiting
            Tile tile = toVisit.poll();

            // Don't look through blacklisted tiles
            if (blackListedTileTypes.contains(tile.getClass())) {
                continue;
            }

            // Add the current tile as one we explored
            exploredTiles.add(tile);

            /* DEBUGGING */
            // System.out.println(tile);
            /*-----------*/

            // Check if current tile is a desired tile (in the set of tiles and we are NOT avoiding them) or it's not blackListed, and exit if so
            // (tileTypes.contains(tile.getClass()) && !avoidTileTypes) || (!tileTypes.contains(tile.getClass()) && avoidTileTypes)
            if (stopCondition != null && stopCondition.apply(tile)) {
                stopConditionMet = true;
                System.out.println("Tile: " + tile + ", is the same Tile!");
                continue;
            }

            // Get neighbors (valid coordinates up, right, down, left)
            List<Tile> neighbors = getNeighbors(tile);

            // Go through each unvisited neighbor and add it into the toVisit queue
            for (Tile neighbor : neighbors) {
                if (!visited.contains(neighbor)) {                    
                    toVisit.add(neighbor);
                    visited.add(neighbor);
                }
            }

        }

        return exploredTiles;

    }

    public Coordinate2D getLocationOnBoard(Set<Class<? extends Tile>> tileTypes, boolean avoidTileTypes) {
        
        // Create Coordinate2D to return later
        Coordinate2D ret = null;

        // Choose random coordinate 
        int x = (int) Math.floor(Math.random() * this.getWidth());
        int y = (int) Math.floor(Math.random() * this.getHeight());
        ret = new Coordinate2D(x,y);

        // If there are no Tiles to avoid or prioritize then just return a random point on the board
        if (tileTypes == null) {
            return ret;
        }

        // Run BFS until we find it's a tile of the type we desire
        boolean foundTile = false;
        Set<Tile> visited = new HashSet<Tile>();
        Queue<Tile> toVisit = new LinkedList<Tile>();
        toVisit.add(this.get(ret));  
        
        // Visit the first tile
        visited.add(this.get(ret));

        while (!toVisit.isEmpty() && !foundTile) {
            
            // The current Tile we are visiting
            Tile tile = toVisit.poll();
            visited.add(tile);

            // Check if current tile is a desired tile (in the set of tiles and we are NOT avoiding them) or it's not blackListed, and exit if so
            // (tileTypes.contains(tile.getClass()) && !avoidTileTypes) || (!tileTypes.contains(tile.getClass()) && avoidTileTypes)
            if (tileTypes.contains(tile.getClass()) ^ avoidTileTypes) {
                /* DEBUGGING */
                System.out.println(tile.getClass() + " at " + tile.getCoords() + " is (inside set: " + tileTypes + " AND a Desired Tile) OR (Not in the set: " + tileTypes + " AND an Undesirable Tile)!");
                /*-----------*/
                foundTile = true;
                ret = new Coordinate2D(tile.getCoords());
                continue;
            }

            /* DEBUGGING */
            System.out.println(tile.getClass() + " at " + tile.getCoords() + " is NOT inside set: " + tileTypes + " OR an UNDESIRED Tile!");
            /*-----------*/

            // Get neighbors (valid coordinates up, right, down, left)
            List<Tile> neighbors = getNeighbors(tile);

            // Go through each unvisited neighbor and add it into the toVisit queue
            for (Tile neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    toVisit.add(neighbor);
                    visited.add(neighbor);
                }
            }

        }

        if(!foundTile) {
            ret = null;
        }

        return ret;

    }

    public boolean insideBoard(Coordinate2D coords) {
        int x = coords.getX();
        int y = coords.getY();
        return (x >= 0 && x < this.width) && (y >= 0 && y < this.height);
    }

    // Coordinate within the board
    // TODO: Get Diagonals as well
    public List<Tile> getNeighbors(Coordinate2D coords) {
        List<Tile> neighbors = new ArrayList<Tile>(4);

        // Check for valid neighbors (if within the grid)
        Coordinate2D up = new Coordinate2D(coords.getX(),coords.getY() + 1);
        Coordinate2D right = new Coordinate2D(coords.getX() + 1,coords.getY());
        Coordinate2D down = new Coordinate2D(coords.getX(),coords.getY() - 1);
        Coordinate2D left = new Coordinate2D(coords.getX() - 1,coords.getY());
        // --- Diagonals Coordinates
        Coordinate2D upRight = new Coordinate2D(coords.getX() + 1, coords.getY() + 1);
        Coordinate2D upLeft = new Coordinate2D(coords.getX() - 1, coords.getY() + 1);
        Coordinate2D downRight = new Coordinate2D(coords.getX() + 1, coords.getY() - 1);
        Coordinate2D downLeft = new Coordinate2D(coords.getX() - 1, coords.getY() - 1);

        // Cardinal Directions
        if (insideBoard(up)) neighbors.add(this.get(up));
        if (insideBoard(right)) neighbors.add(this.get(right));
        if (insideBoard(down)) neighbors.add(this.get(down));
        if (insideBoard(left)) neighbors.add(this.get(left));

        // Diagonals
        if (insideBoard(upRight)) neighbors.add(this.get(upRight));
        if (insideBoard(upLeft)) neighbors.add(this.get(upLeft));
        if (insideBoard(downRight)) neighbors.add(this.get(downRight));
        if (insideBoard(downLeft)) neighbors.add(this.get(downLeft));

        return neighbors;
    }

    public void movePiece(TileRepresentable obj, Tile source, Tile destination) {
        if (source.removePiece(obj)) {
            obj.move(destination);
            destination.addPiece(obj);

            // TODO: update the farthestPoint for Heroes
            if (obj instanceof Hero) {
                // check if y is bigger than farthest point before

            }

        } else {
            throw new IllegalAccessError("This object does not exist on the source tile!");
        }
    }

    // Tile within the board
    public List<Tile> getNeighbors(Tile tile) {
        return getNeighbors(tile.getCoords());
    }

    /* Named displayMap so it fits with the preexisting code */
    public void displayMap() {

        // Format the String
        String res = "";

        // Top portion of the Board
        for (int col = 0; col < this.getWidth(); col++) {
            res += "+---------";
        }
        res += "+\n";

        for (int row = 0; row < this.getHeight(); row++) {

            res += "\n";

            // Middle portion of Board containing the actual tile values
            for (int col = 0; col < this.getWidth(); col++) {                

                Tile tile = this.tiles[row][col];
                res += "|    ";

                if (tile == null) {
                    res += NULL_TILE_CHARACTER; // Character for null Tiles
                }

                // Show the piece on the tile (First one)
                else if (!tile.isEmpty()) {
                    res += String.format(Ansi.colorize("%c", Attribute.GREEN_TEXT()), tile.getPiece().represent());
                }

                // If no pieces on the tiles, show the representation of the tile
                else {

                    if (tile instanceof PlainTile) {
                        res += String.format(Ansi.colorize("%c", Attribute.GREEN_TEXT()), tile.toCharacter());
                    } else if (tile instanceof InaccessibleTile) {
                        res += String.format(Ansi.colorize("%c", Attribute.RED_TEXT()), tile.toCharacter());
                    } else if (tile instanceof NexusTile) {
                        res += String.format(Ansi.colorize("%c", Attribute.YELLOW_TEXT()), tile.toCharacter());
                    } else {
                        res += String.format("%c", tile.toCharacter());
                    }

                }                

                res += "    ";

            }
            res += "|\n\n";

            // Bottom portion of the row
            for (int col = 0; col < this.getWidth(); col++) {
                res += "+---------";
            }
            res += "+\n";

        }

        // Print it out
        System.out.println(res);

    }

    @Override
    public String toString() {
        String res = "";

        // Top portion of the Board
        for (int col = 0; col < this.getWidth(); col++) {
            res += "+---------";
        }
        res += "+\n";

        for (int row = 0; row < this.getHeight(); row++) {

            res += "\n";

            // Middle portion of Board containing the actual tile values
            for (int col = 0; col < this.getWidth(); col++) {                

                Tile tile = this.tiles[row][col];
                res += "|    ";

                if (tile == null) {
                    res += NULL_TILE_CHARACTER; // Character for null Tiles
                }

                // Show the piece on the tile (First one)
                else if (!tile.isEmpty()) {
                    res += String.format(Ansi.colorize("%c", Attribute.GREEN_TEXT()), tile.getPiece().represent());
                }

                // If no pieces on the tiles, show the representation of the tile
                else {

                    if (tile instanceof PlainTile) {
                        res += String.format(Ansi.colorize("%c", Attribute.GREEN_TEXT()), tile.toCharacter());
                    } else if (tile instanceof InaccessibleTile) {
                        res += String.format(Ansi.colorize("%c", Attribute.RED_TEXT()), tile.toCharacter());
                    } else if (tile instanceof NexusTile) {
                        res += String.format(Ansi.colorize("%c", Attribute.YELLOW_TEXT()), tile.toCharacter());
                    } else {
                        res += String.format("%c", tile.toCharacter());
                    }

                }                

                res += "    ";

            }
            res += "|\n\n";

            // Bottom portion of the row
            for (int col = 0; col < this.getWidth(); col++) {
                res += "+---------";
            }
            res += "+\n";

        }

        return res;
    }

}
