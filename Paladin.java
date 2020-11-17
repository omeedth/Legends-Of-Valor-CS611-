/**
 * @author:Zhuyun Chen Class represent a Warrior
 **/
public class Paladin extends Hero{
   
    /** Default Constructor **/
    Paladin(){
        super();
    }
    
    /** User defined constructor */
    Paladin(String name, int exp, double mana, double str, double dex, double agi, double coins){
        super(name, exp, mana, str, dex, agi, coins);
    }

    /** Level up */
    public void levelUp(){
        this.level += 1;
        this.HP = this.level*100;
        this.mana *= 1.1;
        this.str *= 1.1;
        this.dex *= 1.1;
        this.agi *= 1.05;
    }
}
