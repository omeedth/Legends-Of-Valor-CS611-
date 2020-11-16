/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent an RPG game
 * Superclass of LegendGame
 **/

import java.util.*;

public abstract class RpgGame extends Game{
    
    protected LegendWorld world;

   /* Default constructor */
    RpgGame(){
    }
    
   /* User defined constructor */
    RpgGame(LegendWorld world){
        this.world = world;
    }
    
    /** Battle in an rpg game */
    public abstract void battle();
    
    /** Trade in an rpg game*/
    public abstract void trade();
    
    /** Display the world map **/
    public void displayMap(){
        this.world.displayMap();
    }
}
