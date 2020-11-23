/*
 *  Author: Alex Thomas
 *  Creation Date: 11/19/2020
 *  Purpose: Defines a Lane object which contains a 2D array of Tiles and is associated to a map that contains Lane objects
 * 
 */
/* External Imports */
import java.util.*;
import jcolor.*;
import java.util.function.Function;

public class Lane{
    
    /* Final/Static Data Members */
    public static final char NULL_TILE_CHARACTER = '~';
    /* Final/Static Data Members */
    public static final String DEFAULT_ALIAS = "Default Name";

    /* Data Members */
    private Tile[][] tiles;
    private int width, height;
    private String alias;
    
    private List<Hero> heroes;
    private int frontierY;
    
    private ArrayList<MonsterNexusTile> monsterNexus;
    private ArrayList<HeroNexusTile> heroNexus;
    
    private List<Tile> disconnectedGraphs;

    /* Constructors */

    public Lane(String alias, int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new Tile[this.height][this.width];
        
        // Initializing BoardInfo Variables
        disconnectedGraphs = new ArrayList<>();
        this.alias = alias;   
        this.heroes = new ArrayList<>();
        this.frontierY = 0;
        this.monsterNexus = new ArrayList<MonsterNexusTile>();
        this.heroNexus = new ArrayList<HeroNexusTile>();
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

    public Tile[][] getTiles() { return this.tiles;}
    public Tile get(int x, int y){ return this.tiles[cartesianYToComputerY(y)][x];}
    
    public int getWidth() { return this.width;}
    public int getHeight() { return this.height;}
    /* Accessor Methods */
    public String getAlias() { return alias;}
    public List<Hero> getHeroes() { return heroes;}
    public int getFrontierY() { return frontierY;}
    // Get nexus tiles
    public ArrayList<MonsterNexusTile> getMonsterNexus(){ return monsterNexus;}
    public ArrayList<HeroNexusTile> getHeroNexus(){ return heroNexus;}
    
    public List<Tile> getDisconnectedGraphs() {
        return disconnectedGraphs;
    }
    
    /* Mutator Methods */
    public void setFrontierCoordinate(int frontierY) {
        if(frontierY>this.height) throw new IllegalArgumentException("Coordinate outside Board!");
        //if (!this.insideBoard(frontierY)) throw new IllegalArgumentException("Coordinate outside Board!");
        this.frontierY = frontierY;
    }

    public void generate(Function<Lane,int[][]> tileIdMatrixFunction, int laneIndex) {
        int[][] tileIds = tileIdMatrixFunction.apply(this);
        // PrintUtility.printMatrix(tileIds);
        boolean dimensionsMatch = this.fillMatrixFromTileIdMatrix(tileIds, laneIndex);
        // System.out.println("Dimensions Match: " + dimensionsMatch);
    }

    public boolean fillMatrixFromTileIdMatrix(int [][] tileIds, int landIndex) {
        // Dimensions of the two matrices match - Continue
        if (tileIds != null && this.height == tileIds.length && this.height != 0 && this.width == tileIds[0].length && this.width != 0) {
            for (int row = 0; row < this.height; row++) {
                for (int col = 0; col < this.width; col++) {
                    Coordinate2D cartesianCoords = new Coordinate2D(col, row);
                    Coordinate2D currCoords = cartesianCoordinatesToComputerCoordinates(cartesianCoords);
                    Tile currTile = TileFactory.getTile(tileIds[row][col], currCoords, landIndex);
                    this.tiles[row][col] = currTile;
                    if(currTile instanceof MonsterNexusTile){ this.monsterNexus.add((MonsterNexusTile)currTile);}
                    else if(currTile instanceof HeroNexusTile){ this.heroNexus.add((HeroNexusTile)currTile);}
                }
            }
            return true;
        }
        // Dimensions don't match
        else {
            return false;
        }
    }
    
    public Coordinate2D cartesianCoordinatesToComputerCoordinates(Coordinate2D cartesianCoords) {
        return new Coordinate2D(cartesianCoords.getX(),cartesianYToComputerY(cartesianCoords.getY()));
    }
    public int cartesianYToComputerY(int y) {
        return this.height - (y + 1);
    }
    
    public ArrayList<Tile> getPossibleTeleportTiles() {
        ArrayList<Tile> possibleTile = new ArrayList<Tile>();
        // Check all tiles, if y < frontier y and not occupied, add
        for(Tile[] l:this.tiles){
            for(Tile t:l){
                if(t.isEmpty() && t.getCoords().getY()<=frontierY){possibleTile.add(t);}
            }
        }
        return possibleTile;
    }
    
    // Coordinate2D of where the piece is on the board right now
    public List<Tile> getPossibleTeleportTiles(Tile tile) {
        return getPossibleTeleportTiles();
    }
    
    // Check if a tile is can be moved to
    public boolean validMove(int x, int y){
        if(x>=this.width || x<0 || y<0 || y>=this.height){ return false;}
        Tile destination = this.tiles[x][y];
        if(destination instanceof InaccessibleTile){ return false;}
        if(destination.isEmpty()){ return true;}
        else{ return false;}
    }
    
    public boolean insideBoard(Coordinate2D coords) {
        int x = coords.getX();
        int y = coords.getY();
        return (x >= 0 && x < this.width) && (y >= 0 && y < this.height);
    }
    
    // Coordinate within the board
    // TODO: Get Diagonals as well
    public List<Tile> getNeighbors(int x,int y) {
        List<Tile> neighbors = new ArrayList<Tile>(4);

        if(y<this.height) neighbors.add(this.get(x,y+1));
        if(y>0) neighbors.add(this.get(x,y-1));
        if(x<this.width) neighbors.add(this.get(x+1,y));
        if(x>0) neighbors.add(this.get(x-1,y));
        if(x>0 && y>0){neighbors.add(this.get(x-1,y-1));}
        if(x>0 && y<0){neighbors.add(this.get(x-1,y+1));}
        if(x<this.width && y>this.height){neighbors.add(this.get(x+1,y-1));}
        if(x<this.width && y<this.height){neighbors.add(this.get(x+1,y+1));}
        
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

