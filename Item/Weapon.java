/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent a Weapon
 **/

import java.util.*;

public class Weapon extends RpgItem{
    
    protected double damage;
    protected int numHands;
    
    /** Constructor */
    Weapon(){
        super();
        this.damage = 0;
        this.numHands = 1;
    }
    
    /** Constructor */
    Weapon(String name, int levelReq, double price, double damage, int numHands){
        super(name,levelReq,price,true);
        this.damage = damage;
        this.numHands = numHands;
    }
    
    /** Display */
    public void display(){
        System.out.println(this.name+"\t\t "+this.price+"  "+this.levelReq+"  "+this.damage+"  "+this.numHands);
    }
    
    /** GET METHOD */
    public double getDamage(){ return this.damage;}
    
    /** Return the attributes impact by the potion */
    public int getNumHands(){ return this.numHands;}

}
