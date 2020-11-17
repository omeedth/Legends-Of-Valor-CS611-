/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent World for legend game
 * Superclass of LengendWorld
 **/

import java.util.*;

public class LegendWorld extends World {
    
    protected int teamX;
    protected int teamY;
    
    protected String teamOccuTile;
    
    LegendWorld(){
        super();
        this.teamX = 0;
        this.teamY = 0;
        this.teamOccuTile = " ";
    }
    
    LegendWorld(int width, int height){
        super(width,height);
        this.teamX = (int)Math.round(Math.random()*this.width);
        this.teamY = (int)Math.round(Math.random()*this.height);
        this.teamOccuTile = " ";
    }
    
    /** Generate the world */
    public void generate(){
        // Random generate with 20% non-accessible cells O, 30% markets M, 50% common cells.
        int totalTile = this.width*this.height;
        int numNA = (int)Math.round(totalTile*0.2);
        int numM = (int)Math.round(totalTile*0.3);
        for(int i=0; i<this.width;i++){
            for(int j=0; j<this.height;j++){
                double randNum = Math.random();
                if(randNum<0.2 && numNA>0){
                    this.map[i][j] = "O";
                    numNA -= 1;
                }else if(numNA>0 && randNum<0.6 && numM>0){
                    this.map[i][j] = "\u001B[34mM\u001B[0m";
                    numM -= 1;
                }else if(randNum<0.5 && numM>0){
                    this.map[i][j] = "\u001B[34mM\u001B[0m";
                    numM -= 1;
                }else{
                    this.map[i][j] = " ";
                }
            }
        }
        // Set Team pos
        this.setTile(this.teamX, this.teamY,"\u001B[31mX\u001B[0m");
    }

    /** Update the position of the team */
    public void updateTeamPos(int xPos, int yPos){
        updateMap(xPos, yPos);
        this.teamX = xPos;
        this.teamY = yPos;
        
    }
    
    /** Update the world map signs */
    public void updateMap(int xPos, int yPos){
        // refill old tile
        this.setTile(this.teamX, this.teamY, this.teamOccuTile);
        // Store mark on occupied tile
        this.teamOccuTile = getTile(xPos,yPos);
        // Change mark on new tile
        this.setTile(xPos,yPos, "\u001B[31mX\u001B[0m");
    }
        
    /** Check is a tile is an inaccessible tile */
    public boolean isOccupied(int x, int y){
        if(this.map[x][y].equals("O")){
            return true;
        }else{
            return false;
        }
    }
}
