/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent a World in general
 * Superclass of LengendWorld
 **/

import java.util.*;

public abstract class World {
    
    protected String[][] map;
    
    protected int width;
    protected int height;
    
    World(){
        this.map = new String[8][8];
        this.width = 8;
        this.height = 8;
    }
    
    World(int width, int height){
        this.map = new String[width][height];
        this.width = width;
        this.height = height;
    }
    
    /** Generate the world */
    public abstract void generate();
    
    /** GET METHODS */
    public int getWidth(){ return this.width; }
    public int getHeight(){ return this.height; }
    
    /** Get mark on a tile */
    public String getTile(int xPos, int yPos){
        return this.map[xPos][yPos];
    }
    
    /** Set mark on a tile */
    public void setTile(int xPos, int yPos, String motif){
        this.map[xPos][yPos] = motif;
    }
    
    /** Display map */
    public void displayMap(){
        System.out.println();
        System.out.println("=======================================================");
        for(int i=0;i<this.width;i++){
            System.out.print("||");
            for(int j=0;j<this.height;j++){
                System.out.print(" "+this.map[j][i]+" ||");
            }
            System.out.println();
            System.out.println("===================================================");
            System.out.println();
        }
    }
}
