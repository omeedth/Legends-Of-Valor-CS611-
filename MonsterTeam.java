/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent a Team in legend game
 * Subclass of Team
 **/

import java.util.*;

public class MonsterTeam extends Team{
    
   /* Default constructor */
    LegendTeam(){
        super();
    }
    
    /** Take turn in a battle */
    public void takeTurn(Board gameBoard){
        for(int i=0;i<members.size();i++){
            // For each member alive, take action
            Monster m = (Monster)this.members.get(i);
            if(m.isAlive()){
                m.action(Board gameBoard);
            }
        }
    }
    
    /** Display all monsters during battle*/
    public void display(){
        System.out.println("------------ ❈ ❈ ❈ ❈ ❈ ❈ --------------");
        System.out.println("       \u001B[35m MONSTER STATS \u001B[0m");
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
        for(Character c: this.members){
            BattleCharacter monster = (BattleCharacter)c;
            if(monster.isAlive()){
                return false;
            }
        }
        return true;
    }
    
    // MAY NOT COMPILE YET
    /** If the position on board is of type Hero nexus, win */
    public boolean isWin(Board gameBoard){
        for(Character c: this.members){
            if(gameBoard.getTile(c.getPos()) instanceof HeroNexusTile){
                System.out.println("------------ ❈ ❈ ❈ ❈ ❈ ❈ --------------");
                System.out.println("\u001B[35m Monster Reaches Hero Nexus \u001B[0m");
                System.out.println("       \u001B[35m HERO LOSE \u001B[0m");
                System.out.println("------------ ❈ ❈ ❈ ❈ ❈ ❈ --------------");
                return true;
            }
        }
        return false;
    }
}
