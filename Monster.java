/**
 * @author:Zhuyun Chen
 * date: 10/20/12
 * Class represent a Monster
 * Subclass of BattleCharacter
 */
  
import java.util.Random;

public abstract class Monster extends BattleCharacter{

    protected double baseDamage;
    protected double dodgeChance;
 
    Monster(){
        super();
    }
    
    Monster(String name, int level,double baseDamage, double defense, double dodgeChance){
        super(name, level, defense);
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
