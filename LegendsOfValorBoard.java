/*
 *  Author: Alex Thomas
 *  Creation Date: 11/19/2020
 *  Purpose: Defines a Board object containing Tile objects, and special functionality to handle lanes
 * 
 */

/* External Imports */
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class LegendsOfValorBoard {
    
    /* Final/Static Data Members */
    public static final int DEFAULT_NUM_LANES = 1;
    public static final int DEFAULT_BUFFER_SIZE_BETWEEN_LANES = 1;
    public static final int DEFAULT_BUFFER_TILE_ID = TileFactory.INACCESSIBLE;

    public static final int DEFAULT_LANE_WIDTH = 2;
    public static final int DEFAULT_LANE_HEIGHT = 8;

    /* Data Members */
    private List<Lane> lanes;
    // private List<Tile[]> buffers;
    private int bufferSize;         // Number of Tile objects in between lanes
    private int bufferTileID;

    private int laneWidth;
    private int laneHeight;

    /* Constructors */

    public LegendsOfValorBoard(int numLanes, int laneWidth, int laneHeight/*, int sizeBufferBetweenLanes*/, int bufferTileID) {
        /* Empty Board */
        this.lanes = new ArrayList<Lane>(numLanes);
        setLaneWidth(laneWidth);
        setLaneHeight(laneHeight);

        // this.buffers = new ArrayList<Tile[]>(numLanes - 1);
        setBufferTileID(bufferTileID);
    }

    public LegendsOfValorBoard() {
        this(DEFAULT_NUM_LANES, DEFAULT_LANE_WIDTH, DEFAULT_LANE_HEIGHT/*, DEFAULT_BUFFER_SIZE_BETWEEN_LANES*/, DEFAULT_BUFFER_TILE_ID);
    }

    /* Accessor Methods */

    public List<Lane> getLanes() {
        return this.lanes;
    }

    // public List<Tile[]> getBuffers() {
    //     return buffers;
    // }

    public int getBufferTileID() {
        return bufferTileID;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public int getLaneWidth() {
        return laneWidth;
    }

    public int getLaneHeight() {
        return laneHeight;
    }

    /* Mutator Methods */

    public void setBufferTileID(int bufferTileID) {
        if (!TileFactory.tileIdExists(bufferTileID)) throw new IllegalArgumentException("Invalid Tile ID!");
        this.bufferTileID = bufferTileID;
    }

    public void setLaneWidth(int laneWidth) {
        if (laneWidth <= 0) throw new IllegalArgumentException("Invalid Lane Width!");
        this.laneWidth = laneWidth;
    }

    public void setLaneHeight(int laneHeight) {
        if (laneHeight <= 0) throw new IllegalArgumentException("Invalid Lane Height!");
        this.laneHeight = laneHeight;
    }

    public void addLane() {
        Lane l = new Lane(this.laneWidth, this.laneHeight);
        this.lanes.add(l);
    }

    public void addLane(Lane l) {
        this.lanes.add(l);
    }

    public boolean removeLane(Lane l) {
        return this.lanes.remove(l);
    }

    public Lane removeLane(int index) {
        return this.lanes.remove(index);
    }

    public void generateAllLanes(Function<Board,int[][]> tileIdMatrixFunction) {
        for (Lane lane : this.lanes) {
            lane.generate(tileIdMatrixFunction);
        }
    }

    /* Logic Methods */

    public void displayMap() {
        System.out.println(this.toString());
    }

    public List<Tile> getPossibleTeleportTiles(Tile herotile) {
        // Get all Tile objects in:
        //  1. Different Lanes (Only one lane at a time AKA teleporting from TOP to BOT is NOT allowed) TODO: Incomplete
        //  2. Tile objects the below the farthest point a Hero object has explored in a lane           TODO: Incomplete
        //  3. Tile objects below ALL monsters or on the same level                                     TODO: Incomplete

        // TODO: add a variables or parameters for if you want to rerun BFS to update the Board info with certain blacklisted tiles
        Set<Class<? extends Tile>> blackListedTileTypes = new HashSet<>();
        blackListedTileTypes.add(InaccessibleTile.class);

        List<Tile> possibleTeleportTiles = new ArrayList<>();

        // Go through each Lane
        for (Lane lane : this.lanes) {

            // The hero is in this lane
            if (lane.contains(herotile, blackListedTileTypes)) {    // AKA: lane.getHeroes().contains(hero)
                // System.out.println("Lane: " + lane + " Contains Hero Tile: " + herotile);
                continue;
            }

            /* DEBUGGING */
            // System.out.println("\n");
            // System.out.println(PrintUtility.listToString(exploredTiles,false));
            /*-----------*/

            // Otherwise add all the Tile objects in the subgraph as 
            List<Tile> exploredTiles = lane.getTilesAsList(blackListedTileTypes); // .stream().filter((tile) -> tile.getCoords().getY() <= lane.getFrontierCoordinate().getY()).collect(Collectors.toList())
            possibleTeleportTiles.addAll(exploredTiles);

        }        

        // TODO: Replace "farthestExplored" with the Hero object's actual farthest explored Coordinate2D
        // Coordinate2D farthestExplored = new Coordinate2D(0,4); // Hard Coded Variable
        // return possibleTeleportTiles.stream().filter(tile -> tile.getCoords().getY() <= farthestExplored.getY()).collect(Collectors.toList());

        return possibleTeleportTiles;
    }

    @Override
    public String toString() {

        String res = "";

        // Create LegendsOfValorBoard String

        return res;

    }

}
