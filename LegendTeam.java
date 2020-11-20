/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent a Team in legend game
 * Subclass of Team
 **/

import java.util.*;

public class LegendTeam extends Team{
    
   /* Default constructor */
    LegendTeam(){
        super();
    }
    
    /** Take turn in a battle */
    public void takeTurn(){
        for(int i=0;i<members.size();i++){
            // For each member alive, take action
            BattleCharactor c = (BattleCharactor)this.members.get(i);
            if(c.isAlive()){
                c.action();
            }
        }
    }
    
    /** Display all monsters during battle*/
    public void display(){
        System.out.println("------------ ❈ ❈ ❈ ❈ ❈ ❈ --------------");
        System.out.println("       \u001B[35m TEAM STAT \u001B[0m");
        System.out.println("------------ ❈ ❈ ❈ ❈ ❈ ❈ --------------");
        System.out.println("ID   Level   Name                    HP      Defense       Damage");
        System.out.println("------------------------------------------------------------------------------");
        for(int i=0;i<this.members.size();i++){
            BattleCharactor c = (BattleCharactor)this.members.get(i);
            System.out.print((i+1)+"     ");
            c.display();
        }
    }
    
    /** Return true if no monster alive */
    public boolean isFaint(){
        for(BattleCharacter c: this.members){
            if(c.isAlive()){
                return false;
            }
        }
        return true;
    }
}
