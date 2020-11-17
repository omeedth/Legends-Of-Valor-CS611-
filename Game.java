/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent a Game in general
 * Superclass of RpgGame
 **/

import java.util.*;

public abstract class Game{

   /* Default constructor */
    Game(){
    }

    /** Initialize game setting based on game rules */
    public abstract void init();
    
    /** Play the game based on game rules */
    public abstract void play();
}
