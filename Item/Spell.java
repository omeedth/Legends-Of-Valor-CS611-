/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent a Spell
 **/

import java.util.*;

public abstract class Spell extends RpgItem{
    
    protected double damage;
    protected double manaCost;
    
    Spell(){
        super();
        this.damage = 0;
        this.manaCost = 0;
    }
    
    /**  Constructor */
    Spell(String name, int levelReq, double price, double damage, double manaCost){
        super(name,levelReq,price,false);
        this.damage = damage;
        this.manaCost = manaCost;
    }
    
    /** GET PROPERTIES */
    public double getDamage(){ return this.damage;}
    public double getCost(){ return this.manaCost;}
    
    /** display spell, depends on type */
    public abstract void display();
    
    /** Return the attribute impact by the spell */
    public abstract String getAttrib();

}
