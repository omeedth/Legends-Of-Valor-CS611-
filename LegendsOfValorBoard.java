/*
 *  Author: Alex Thomas
 *  Creation Date: 11/19/2020
 *  Purpose: Defines a Board object containing Tile objects, and special functionality to handle lanes
 * 
 */

/* External Imports */
import java.util.List;
import java.util.ArrayList;

public class LegendsOfValorBoard {
    
    /* Final/Static Data Members */
    public static final int DEFAULT_NUM_LANES = 1;
    public static final int DEFAULT_BUFFER_SIZE_BETWEEN_LANES = 1;
    public static final int DEFAULT_BUFFER_TILE_ID = TileFactory.INACCESSIBLE;

    public static final int DEFAULT_LANE_WIDTH = 2;
    public static final int DEFAULT_LANE_HEIGHT = 8;

    /* Data Members */
    private List<Lane> lanes;
    private List<Tile[]> buffers;
    private int bufferSize;         // Number of Tile objects in between lanes
    private int bufferTileID;

    private int laneWidth;
    private int laneHeight;

    /* Constructors */

    public LegendsOfValorBoard(int numLanes, int laneWidth, int laneHeight, int sizeBufferBetweenLanes, int bufferTileID) {
        /* Empty Board */
        this.lanes = new ArrayList<Lane>(numLanes);
        setLaneWidth(laneWidth);
        setLaneHeight(laneHeight);

        this.buffers = new ArrayList<Tile[]>(numLanes - 1);
        setBufferTileID(bufferTileID);
    }

    public LegendsOfValorBoard() {
        this(DEFAULT_NUM_LANES, DEFAULT_LANE_WIDTH, DEFAULT_LANE_HEIGHT, DEFAULT_BUFFER_SIZE_BETWEEN_LANES, DEFAULT_BUFFER_TILE_ID);
    }

    /* Accessor Methods */

    public List<Lane> getLanes() {
        return this.lanes;
    }

    public List<Tile[]> getBuffers() {
        return buffers;
    }

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

    /* Logic Methods */

    @Override
    public String toString() {

        String res = "";

        return res;

    }

}
