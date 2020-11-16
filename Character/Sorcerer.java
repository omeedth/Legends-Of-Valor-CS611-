/**
 * @author:Zhuyun Chen
 * Class represent a Warrior
 **/

public class Sorcerer extends Hero{
   
    /** Default Constructor **/
    Sorcerer(){
        super();
    }
    
    /** User defined constructor */
    Sorcerer(String name, int exp, double mana, double str, double dex, double agi, double coins){
        super(name, exp, mana, str, dex, agi, coins);
    }

    /** Level up */
    public void levelUp(){
        this.level += 1;
        this.HP = this.level*100;
        this.mana *= 1.1;
        this.str *= 1.05;
        this.dex *= 1.1;
        this.agi *= 1.1;
    }
}
