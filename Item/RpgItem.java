/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent an RpgItem in general
 * Superclass of Weapon/Armor/Spell/Potion
 **/

public abstract class RpgItem extends Item{

    protected int levelReq;
    protected double price;
    protected boolean consumable;
    
    RpgItem(){
        super();
        this.levelReq = 0;
        this.price = 0;
        this.consumable = false;
	}
    
    /**
     * User defined constructor
     */
    RpgItem(String name, int levelReq, double price, boolean consumable){
        super(name);
        this.levelReq = levelReq;
        this.price = price;
        this.consumable = consumable;
    }
    
    /** GET METHODS */
    public int getLevelReq(){ return this.levelReq;}
    public double getPrice(){ return this.price;}
    
    /** Return if item is consumable */
    public boolean isConsumable(){
        return this.consumable;
    }

}
