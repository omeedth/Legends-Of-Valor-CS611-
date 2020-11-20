
/* External Imports */
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class BoardTest {

    /* Data Members */
    private Board world;

    /* Constructors */
    
    public BoardTest(Board board) {
        this.world = board;
    }
    
    public static void main(String args[]) {

        /* Create a Board Object */
        int dimensions = 8;
        Board board = new Board(dimensions);

        /* Create a Board Test using the Board Object */
        BoardTest test = new BoardTest(board);

        /* Testing */
        int[][] tileIds = test.generateTileIdMatrix();
        board.fillMatrixFromTileIdMatrix(tileIds);

        PrintUtility.printMatrix(tileIds);
        board.displayMap();

    }

    /* Generate Tile ID Matrix */
    // TODO: Generate 3 Lanes (Top, Mid, and Bot) where each lane has randomly
    //       assigned Tiles
    //       NOTE: The first row is the Monster's Nexus, The last row is the Hero's Nexus,
    //             and there are inaccessible tiles in between each lane
    public int[][] generateTileIdMatrix() {

        // Variables for the matrix of tileIds
        int w = this.world.getWidth();
        int h = this.world.getHeight();
        int[][] tileIds = new int[w][h];

        // Make mapping of probabilities for possible tiles
        // ArrayList<Float> probabilities = new ArrayList<Float>(); // Probabilities of Tiles
        // probabilities.add(0.2f);
        // probabilities.add(0.2f);
        // probabilities.add(0.2f);
        // probabilities.add(0.4f);

        Map<Float,Integer> probabilityMapping = new HashMap<Float,Integer>();
        probabilityMapping.put(0.2f,TileFactory.BUSH);
        probabilityMapping.put(0.2f,TileFactory.CAVE);
        probabilityMapping.put(0.2f,TileFactory.KOULOU);
        probabilityMapping.put(0.4f,TileFactory.PLAIN);

        /* Lane Variables */
        int laneWidth = 2;
        int laneBufferWidth = 1;
        int numLanes = ((w + laneBufferWidth) / (laneWidth + laneBufferWidth));

        // System.out.println("Number of Lanes: " + numLanes); // CORRECT

        for (int lane = 0; lane < numLanes; lane++) {
            int offset = (laneWidth * lane) + (lane * laneBufferWidth);
            // System.out.printf("Lane %d: Offset = %d\n",lane,offset); // CORRECT
            generateTileIdMatrixForLane(laneWidth, offset, tileIds);

            if (lane == numLanes - 1) {
                continue;
            }

            int laneOffset = offset + laneWidth;
            for (int buffer = 0; buffer < laneBufferWidth; buffer++) {             
                // System.out.printf("Lane %d: Buffer Offset = %d\n",lane, laneOffset); // CORRECT
                addBufferColumn(buffer + laneOffset, TileFactory.INACCESSIBLE, tileIds);
            }            
        }

        return tileIds;
    }

    public int[][] addBufferColumn(int column, int bufferTileId, int[][] tileIds) {

        assert tileIds != null;
        assert tileIds.length > 0 && column < tileIds[0].length;

        for (int row = 0; row < this.world.getHeight(); row++) {
            tileIds[row][column] = bufferTileId;
        }

        return tileIds;

    }

    /* Generate Tile ID Matrix For a single Lane */
    // TODO: Generate a Lane (Top, Mid, and Bot) where each lane has randomly
    //       assigned Tiles
    //       NOTE: The first row is the Monster's Nexus, The last row is the Hero's Nexus,
    //             and there are inaccessible tiles in between each lane
    public int[][] generateTileIdMatrixForLane(int laneWidth, int horizontalOffset, int[][] tileIds) {

        assert this.world.getHeight() > 0;
        assert laneWidth >= 0 && (laneWidth + horizontalOffset) < this.world.getWidth();
        assert tileIds != null && tileIds.length > 0 && laneWidth < tileIds[0].length;

        // Variables for the matrix of tileIds
        int w = laneWidth;
        int h = this.world.getHeight();

        // Make mapping of probabilities for possible tiles
        // ArrayList<Float> probabilities = new ArrayList<Float>(); // Probabilities of Tiles
        // probabilities.add(0.5f);
        // probabilities.add(0.2f);
        // probabilities.add(0.3f);

        Map<Integer,Float> probabilityMapping = new HashMap<Integer,Float>();
        probabilityMapping.put(TileFactory.BUSH,0.2f);
        probabilityMapping.put(TileFactory.CAVE,0.2f);
        probabilityMapping.put(TileFactory.KOULOU,0.2f);
        probabilityMapping.put(TileFactory.PLAIN,0.4f);

        /* Nexus Tile ID */
        int NEXUS_TILE_ID = TileFactory.NEXUS;

        /* Add Monster Nexus at the first row */
        for (int col = 0; col < w; col++) {
            tileIds[0][col + horizontalOffset] = NEXUS_TILE_ID;
        }

        // Choosing the tile Ids to place in the specific cell
        for (int row = 1; row < (h-1); row++) {
            for (int col = 0; col < w; col++) {

                int id = getTileIdFromProbabilityList(probabilityMapping);

                // Assigning the tileId
                tileIds[row][col + horizontalOffset] = id;
                
            }
        }

        /* Add Monster Nexus at the first row */
        for (int col = 0; col < w; col++) {
            tileIds[h-1][col + horizontalOffset] = NEXUS_TILE_ID;
        }

        return tileIds;
    }

    public int getTileIdFromProbabilityList(Map<Integer,Float> probabilities) {
        // Variables for choosing the random tile type
        float rand = (float) Math.random();
        float currProbabilityThreshold = 0;
        int id = -1;

        // Figuring out the tile to place
        List<Integer> keys = new ArrayList<Integer>(probabilities.keySet());
        for (Integer probabilitykey : keys) {
            currProbabilityThreshold += probabilities.get(probabilitykey);
            if (rand <= currProbabilityThreshold) {
                id = probabilitykey;
                break;
            }
        }

        // For List<Float>
        // for (int i = 0; i < probabilities.size(); i++) {
        //     currProbabilityThreshold += probabilities.get(i);
        //     if (rand <= currProbabilityThreshold) {
        //         id = i;
        //         break;
        //     }
        // }

        // Default tile if none chosen
        if (id == -1) {
            id = keys.get(keys.size() - 1);
        }

        return id;
    }

}
