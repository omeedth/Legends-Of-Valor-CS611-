/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent a Team in legend game
 * Subclass of Team
 **/

import java.util.*;

public class HeroTeam extends Team{
    
   /* Default constructor */
    LegendTeam(){
        super();
    }
    
    /** Take turn in a battle */
    public void takeTurn(Board gameBoard){
        for(int i=0;i<members.size();i++){
            // For each member alive, take action
            Hero h = (Hero)this.members.get(i);
            if(h.isAlive()){
                h.action(Board gameBoard);
            }else{
                c.revive();
            }
        }
    }
    
    /** Regain HP and Mana for each hero ALIVE */
    public void regain(){
        for(int i=0;i<members.size();i++){
            Hero h = (Hero)this.members.get(i);
            if(h.isAlive()){
                h.regain();
            }
        }
    }
    
    /** Display all monsters during battle*/
    public void display(){
        System.out.println("------------ ❈ ❈ ❈ ❈ ❈ ❈ --------------");
        System.out.println("       \u001B[35m HERO STATS \u001B[0m");
        System.out.println("------------ ❈ ❈ ❈ ❈ ❈ ❈ --------------");
        System.out.println("ID   Level     Class    Name\t\t Mana  Strength  Agility  Dexterity  Money  Exp");
        System.out.println("------------------------------------------------------------------------------");
        for(int i=0;i<this.members.size();i++){
            BattleCharactor c = (BattleCharactor)this.members.get(i);
            System.out.print((i+1)+"     ");
            c.display();
        }
    }
    
    /** return MaxLevel of the team */
    public int getLevel(){
        int level = 0;
        for(Character c: this.members){
            Hero h = (Hero)c;
            int newLevel = h.getLevel();
            if (newLevel>level){
                level = newLevel;
            }
        }
        return level;
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
