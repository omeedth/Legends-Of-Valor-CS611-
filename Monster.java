
/**
 * @author:Zhuyun Chen
 * date: 10/20/12
 * Class represent a Monster
 * Subclass of BattleCharacter
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Monster extends BattleCharacter{

    protected double baseDamage;
    protected double dodgeChance;
 
    Monster(){
        super();
    }
    
    Monster(String name, int level,double baseDamage, double defense, double dodgeChance){
        super(name, level, defense, new Coordinate2D());    // TODO: Add Coordinates to constructor calls for when you are calling super() I ADDED THIS SO MY CODE WILL COMPILE
        this.baseDamage = baseDamage;
        this.dodgeChance = dodgeChance;
    }

    /**
    * Dodge an attack
    * Chance to dodge =  dodge_chance*0.01
    * @return boolean, true if success, false if not
    */
    public boolean dodge(){
        return Math.random() < this.dodgeChance*0.01 ;
    }

    /* Take action based on rule */
    public void action(Board gameBoard){
        Hero enemy = this.getEnemy(gameBoard);
        // If no hero, move; If hero nearby, attack
        Tile dummyTile = gameBoard.get(new Coordinate2D());    // THIS IS SO MY CODE WILL COMPILE
        if(enemy == null){ move(dummyTile);} // TODO: Pass in argument!
        else{ attack(enemy);}
    }
    
    /** Get enemy in nearby area, return Null if no avaliable enemy */
    public Hero getEnemy(Board gameBoard) {
        List<Tile> neighbors = gameBoard.getNeighbors(this.position);
        // ArrayList<Hero> enemyList = new ArrayList<Monster>();    // TODO: choose ONE type      
        ArrayList<Hero> enemyList = null;                         // THIS IS A DUMMY VARIABLE SO MY CODE COMPILES
        for(int i = 0; i < neighbors.size(); i++){
            TileRepresentable piece = neighbors.get(i).getPiece();
            if(piece instanceof Hero){
                enemyList.add((Hero) piece);
            }
        }
        // If more than 1 hero, randomly attack 1.
        if (enemyList.size()>1){
            Random rand = new Random();
            return enemyList.get(rand.nextInt(enemyList.size()));
        }else if (enemyList.size()==1){
            return enemyList.get(0);
        }else{
            return null;
        }
    }
    
    /** Attack a hero in battle */
    public void attack(BattleCharacter enemy){
        Hero hero = (Hero)enemy;
        System.out.println("Turn \u001B[31m"+this.name+" V.S. "+enemy.getName()+"\u001B[0m");
        if (hero.dodge()){
            System.out.println();
            System.out.println("\u001B[33m ATTACK MISS! \u001B[0m");
            System.out.println();
        }else{
            double damage = Math.round(this.baseDamage-hero.getDefense());
            if(damage > 0){
                hero.takeDamage(damage);
            }
            // Display message
            System.out.println("\u001B[33m"+this.name+" attack "+hero.name+" with "+damage+" damage. \u001B[0m");
        }
    }

    /** Reduce given attribute by 10% */
    public void reduceAttrib(String attribName){
        if(attribName.equals("defense")){
            this.defense *= 0.9;
        }else if(attribName.equals("damage")){
            this.baseDamage *= 0.9;
        }else if(attribName.equals("dodge")){
            this.dodgeChance *= 0.9;
        }
    }
    
    public void display(){
        System.out.println(this.level+"  "+this.name+"\t"+this.HP+"  "+this.defense+"  "+this.baseDamage);
    }
    
    public char represent(){ return 'M';}
}
