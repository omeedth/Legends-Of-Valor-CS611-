/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent an Armor
 **/

import java.util.*;

public class Armor extends RpgItem{
    
    protected double defense;
    
    /** Constructor */
    Armor(){
        super();
        this.defense = 0;
    }
    /** Constructor */
    Armor(String name, int levelReq, double price, double defense){
        super(name,levelReq,price,false);
        this.defense = defense;
    }
    
    /** GET METHOD */
    public double getDefense(){ return this.defense;}
    
    /** Display */
    public void display(){
        System.out.print(this.name+"\t\t "+this.price+"  "+this.levelReq+"  "+this.defense);
    }
    


}
