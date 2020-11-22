/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent a Team in legend game
 * Subclass of Team
 **/

import java.util.*;

public class HeroTeam extends Team{
    
   /* Default constructor */
    HeroTeam(){
        super();
    }
    
    /** Take turn in a battle */
    public void takeTurn(Board gameBoard){
        for(int i=0;i<members.size();i++){
            // For each member alive, take action
            Hero h = (Hero)this.members.get(i);
            if(h.isAlive()){
                h.action(gameBoard);
            }else{
                h.revive();
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
            Hero c = (Hero)this.members.get(i);
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
    
    /** Return true if no hero alive */
    public boolean isFaint(){
        for(Character c: this.members){
            BattleCharacter hero = (BattleCharacter)c;
            if(hero.isAlive()){
                return false;
            }
        }
        return true;
    }
 
    // MAY NOT COMPILE YET
    /** If the position on board is of type Hero nexus, win */
    public boolean isWin(Board gameBoard){
        for(Character c: this.members){
            if(gameBoard.getTile(c.getPos()) instanceof MonsterNexus){
                System.out.println("------------ ❈ ❈ ❈ ❈ ❈ ❈ --------------");
                System.out.println("\u001B[35m Reaches Monster Nexus \u001B[0m");
                System.out.println("       \u001B[35m HERO WIN \u001B[0m");
                System.out.println("------------ ❈ ❈ ❈ ❈ ❈ ❈ --------------");
                return true;
            }
        }
        return false;
    }
}
