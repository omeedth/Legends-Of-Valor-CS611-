/*
 *  Author: Alex Thomas
 *  Creation Date: 10/29/2020
 *  Purpose: A Board in general
 * 
 */

/* External Imports */
import java.util.*;
import jcolor.*;
import java.util.function.Function;

public class Board {

    /* Data Members */
    protected List<Lane> lanes;
    /* Final/Static Data Members */
    public static final int DEFAULT_NUM_LANES = 3;
    public static final int DEFAULT_BUFFER_SIZE_BETWEEN_LANES = 1;
    public static final int DEFAULT_BUFFER_TILE_ID = TileFactory.INACCESSIBLE;
    public static final char DEFAULT_BUFFER_CHAR = 'I';
    
    public static final int DEFAULT_LANE_WIDTH = 2;
    public static final int DEFAULT_LANE_HEIGHT = 8;
    
    // private List<Tile[]> buffers;
    private int bufferSize;         // Number of Tile objects in between lanes
    private int bufferTileID;
    
    private int laneWidth;
    private int laneHeight;
    
    public Board(int laneWidth, int laneHeight/*, int sizeBufferBetweenLanes*/, int bufferTileID) {
        /* Empty Board */
        this.lanes = new ArrayList<Lane>();
        setLaneWidth(laneWidth);
        setLaneHeight(laneHeight);
        // this.buffers = new ArrayList<Tile[]>(numLanes - 1);
        setBufferTileID(bufferTileID);
    }
    
    public Board() {
        this(DEFAULT_LANE_WIDTH, DEFAULT_LANE_HEIGHT/*, DEFAULT_BUFFER_SIZE_BETWEEN_LANES*/, DEFAULT_BUFFER_TILE_ID);
    }
    
    /* Accessor Methods */
    public Tile get(int x, int y){
        // Find the corresponding lane
        int laneIndex = Math.round(x/this.laneWidth);
        // Ask lane to return the corresponding tile
        return(this.lanes.get(laneIndex).get(x%this.laneWidth, y));
    }
    
    public Tile get(Coordinate2D cartesianCoords) {
        return getTile(cartesianCoords.getX(),cartesianCoords.getY());
    }
    
    public List<Tile> getNeighbors(Coordinate2D coord){
        int x = coord.getX();
        int y = coord.getY();
        return this.lanes.get(Math.round(x/this.laneWidth)).getNeighbors(x%this.laneWidth,y);
    }
    
    public ArrayList<HeroNexusTile> getHeroNexus(){
        ArrayList<HeroNexusTile> nexus = new ArrayList<HeroNexusTile>();
        for(Lane l: this.lanes){ nexus.addAll(l.getHeroNexus());}
        return nexus;
    }
    
    public ArrayList<MonsterNexusTile> getMonsterNexus(){
        ArrayList<MonsterNexusTile> nexus = new ArrayList<MonsterNexusTile>();
        for(Lane l: this.lanes){ nexus.addAll(l.getMonsterNexus());}
        return nexus;
    }
    
    /* Aliases for "get()" methods so it works with preexisting code */
    public Tile getTile(int x, int y) {
        return get(x,y);
    }

    public Tile getTile(Coordinate2D cartesianCoords) {
        return get(cartesianCoords);
    }
    /* Mutator Methods */

    public List<Lane> getLanes() { return this.lanes;}
    public Lane getLane(Coordinate2D pos){ return this.lanes.get(pos.getX()/this.lanes.size());}
    
    public int getHeight(){return laneHeight;}
    public int getWidth(){return laneWidth*this.lanes.size();}
    
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
    
    public void generateAllLanes(Function<Lane,int[][]> tileIdMatrixFunction) {
        for (Lane lane : this.lanes) {
            lane.generate(tileIdMatrixFunction);
        }
    }
    
    /* Logic Methods */
    
    public void displayMap() {
        System.out.println(this.toString());
    }
    
    public List<Tile> getPossibleTeleportTiles(Coordinate2D heroPos) {
        List<Tile> possibleTile = new ArrayList<Tile>();
        int heroLane = heroPos.getX()/this.lanes.size();
        if(heroLane<this.lanes.size()-1){
            possibleTile.addAll(this.lanes.get(heroLane+1).getPossibleTeleportTiles());
        }
        if(heroLane>0){
            possibleTile.addAll(this.lanes.get(heroLane-1).getPossibleTeleportTiles());
        }
        return possibleTile;
    }
    
    @Override
    public String toString() {
        String res = "";
        // Create LegendsOfValorBoard String
        for (int row = 0; row < this.laneHeight; row++) {
            
            for (int laneIndex = 0; laneIndex < this.lanes.size(); laneIndex++) {
                Lane lane = this.lanes.get(laneIndex);
                
                // Top - Outer tile character
                for (int col = 0; col < lane.getWidth(); col++) {
                    Tile tile = lane.get(col,row);
                    
                    for (int i = 0; i < 3; i++) {
                        res += tile.toCharacter();
                        if (i != 2) {
                            res += "--";
                        } else {
                            res += " ";
                        }
                    }
                    
                }
                
                // Buffer Tiles
                if (laneIndex != this.lanes.size() - 1) {
                    // Top - Outer tile character
                    for (int i = 0; i < 3; i++) {
                        res += DEFAULT_BUFFER_CHAR;
                        if (i != 2) {
                            res += "--";
                        } else {
                            res += " ";
                        }
                    }
                }
                
            }
            res += "\n";
            
            for (int laneIndex = 0; laneIndex < this.lanes.size(); laneIndex++) {
                Lane lane = this.lanes.get(laneIndex);
                
                // Middle - Empty
                for (int col = 0; col < lane.getWidth(); col++) {
                    res += "|     | ";
                }
                
                // Buffer Tiles
                if (laneIndex != this.lanes.size() - 1) {
                    res += "|     | ";
                }
                
            }
            res += "\n";
            
            for (int laneIndex = 0; laneIndex < this.lanes.size(); laneIndex++) {
                Lane lane = this.lanes.get(laneIndex);
                
                // Middle - Inner tile character
                for (int col = 0; col < lane.getWidth(); col++) {
                    Tile tile = lane.get(col,row);
                    
                    res += tile.toCharacter() + "  ";
                    
                    if (tile.isEmpty()) {
                        res += " ";
                    } else {
                        // Shows Piece on that Tile
                        res += tile.getPiece().represent();
                    }
                    res += "  ";
                    
                    res += tile.toCharacter() + " ";
                    
                }
                
                // Buffer Tiles
                if (laneIndex != this.lanes.size() - 1) {
                    // Middle - Inner tile character
                    res += DEFAULT_BUFFER_CHAR + " XXX " + DEFAULT_BUFFER_CHAR + " ";
                }
                
            }
            res += "\n";
            
            for (int laneIndex = 0; laneIndex < this.lanes.size(); laneIndex++) {
                Lane lane = this.lanes.get(laneIndex);
                
                // Middle - Empty
                for (int col = 0; col < lane.getWidth(); col++) {
                    res += "|     | ";
                }
                
                // Buffer Tiles
                if (laneIndex != this.lanes.size() - 1) {
                    res += "|     | ";
                }
                
            }
            res += "\n";
            
            for (int laneIndex = 0; laneIndex < this.lanes.size(); laneIndex++) {
                Lane lane = this.lanes.get(laneIndex);
                
                // Bottom - Outer tile character
                for (int col = 0; col < lane.getWidth(); col++) {
                    Tile tile = lane.get(col%this.lanes.size(),row);
                    for (int i = 0; i < 3; i++) {
                        res += tile.toCharacter();
                        if (i != 2) {
                            res += "--";
                        } else {
                            res += " ";
                        }
                    }
                    
                }
                
                // Buffer Tiles
                if (laneIndex != this.lanes.size() - 1) {
                    // Top - Outer tile character
                    for (int i = 0; i < 3; i++) {
                        res += DEFAULT_BUFFER_CHAR;
                        if (i != 2) {
                            res += "--";
                        } else {
                            res += " ";
                        }
                    }
                }
                
            }
            res += "\n";
            
            // After each row
            res += "\n";
            
        }
        return res;
    }

}
