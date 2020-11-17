/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent a Potion
 **/

import java.util.*;

public class Potion extends RpgItem{
    
    protected double amount;
    protected String[] attribs;
    
    Potion(){
        super();
        this.amount = 0;
    }
    
    /**
     * Constructor
     * @note a potion is always consumable
     */
    Potion(String name, int levelReq, double price, String[] attribs, double amount){
        super(name,levelReq,price,true);
        this.amount = amount;
        this.attribs = attribs;
    }
    
    /** GET METHODS */
    public double getAmount(){ return this.amount;}
    public String[] getAttribs(){ return this.attribs;}
    
    /**
     * Display in format Name/cost/required level/attribute increase/attribute affected
     */
    public void display(){
        System.out.println(this.name+"\t\t "+this.price+"  "+this.levelReq+"  "+this.amount+"  "+String.join("/", this.attribs));
    }
    

}
