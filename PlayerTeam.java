/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent a team of Heros (player controled charactor)
 * Subclass of Team
 **/

import java.util.*;

public class PlayerTeam extends Team{
    
    /* Change to Coordinate2D Please */
    protected int xPos;
    protected int yPos;
    
   /* Default constructor */
    PlayerTeam(){
        super();
        this.xPos = 0;
        this.yPos = 0;
    }
    
    /**
     * User defined constructor
     * @param xPos, yPos, start position
     */
    PlayerTeam(int xPos, int yPos){
        super();
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    /** Move 1 grid up */
    public void moveUp(){ this.yPos -= 1;}
    /** Move 1 grid down */
    public void moveDown(){ this.yPos += 1;}
    /** Move 1 grid left */
    public void moveLeft(){ this.xPos -= 1;}
    /** Move 1 grid right */
    public void moveRight(){ this.xPos += 1;}
    
    /** GET POSITION */
    public int getXPos(){ return this.xPos;}
    public int getYPos(){ return this.yPos;}

    /** Take turn in a battle */
    public void takeTurn(MonsterTeam monsters){
        for(int i=0;i<members.size();i++){
            // If all monsters faint, end the battle
            if(monsters.isFaint()){ break;}
            Hero h = (Hero)this.members.get(i);
            Monster m = (Monster)monsters.getMember(i);
            // If both hero and monster is alive, fight
            if(h.isAlive() && m.isAlive()){
                h.action(m);
            }else if(h.isAlive()){
                // if that monster die, attack the first monster that's still alive
                for(int j=0;j<monsters.size();j++){
                    Monster otherM = (Monster)monsters.getMember(j);
                    if(otherM.isAlive()){
                        h.action(otherM);
                        break;
                    }
                }
            }
        }
    }

    /* MODIFIED - Code so that my (Alex) code works */

    public Coordinate2D getCoords() {
        return new Coordinate2D(this.xPos, this.yPos);
    }

    /*----------------------------------------------*/
    
    /** regain Some HP and MP for alive heroes */
    public void regain(){
        for(Character c: this.members){
            Hero h = (Hero)c;
            if(h.isAlive()){
                h.regain();
            }
        }
    }
    /**
     * When win a battle, get rewards, faint hero revived by the other
     * @param mLevel, level of the monsters defeat by team
     */
    public void win(int mLevel){
        for(Character c: this.members){
            Hero h = (Hero)c;
            if (h.isAlive()){
                h.battleReward(100*mLevel,2);
            }else{
                h.revive();
            }
        }
    }
    
    /** Display all members in the team */
    public void display(){
        System.out.println("------------------------");
        System.out.println("        YOUR TEAM ");
        System.out.println("------------------------");
        System.out.println();
        System.out.println("ID   Level     Class    Name\t\t Mana  Strength  Agility  Dexterity  Money  Exp");
        System.out.println("----------------------------------------------------------------------------------------------");
        for(int i=0;i<this.members.size();i++){
            Hero h = (Hero)this.members.get(i);
            System.out.print((i+1)+"      "+h.getLevel());
            if (h instanceof Warrior){
                System.out.print("  Warrior     ");
            }else if (h instanceof Sorcerer){
                System.out.print("  Sorcerer    ");
            }else if (h instanceof Paladin){
                System.out.print("  Paladin     ");
            }
            h.display();
        }
    }
    
    /** Display all members in the team during battle*/
    public void display_battle(){
        System.out.println("--------------------------");
        System.out.println("      YOUR TEAM STAT ");
        System.out.println("--------------------------");
        System.out.println("ID   Level   Name\t\t HP  Mana  Equipped Weapon\t\t Equipped Armor");
        System.out.println("----------------------------------------------------------------------------------------------");
        for(int i=0;i<this.members.size();i++){
            Hero h = (Hero)this.members.get(i);
            System.out.print((i+1)+"      "+h.getLevel());
            h.displayStat();
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
            Hero h = (Hero)c;
            if(h.isAlive()){
                return false;
            }
        }
        return true;
    }

    @Override
    public char represent() {
        // TODO Auto-generated method stub
        return 'P';
    }

    @Override
    public void move(Tile destination) {
        // TODO Auto-generated method stub
        System.out.println("Player Team Moving...");
    }
}
