/**
 * @author: Zhuyun Chen date: 11/08/20 Class represent a Lightning Spell
 **/

public class LightningSpell extends Spell{
    
    LightningSpell(){
        super();
    }
    
    /**  Constructor */
    LightningSpell(String name, int levelReq, double price, double damage, double manaCost){
        super(name,levelReq,price, damage, manaCost);
    }
    
    public void display(){
        System.out.println(this.name+"\t\t "+this.price+"  "+this.levelReq+"  "+this.damage+"  "+this.manaCost+"  Lightning");
    }
    
    /** Return the attributes impact by the spell */
    public String getAttrib(){
        return "dodge";
    }

}
