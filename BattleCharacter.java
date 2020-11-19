/**
 * @author:Zhuyun Chen date: 11/08/2020 Class represent a Battle character
 *                Subclass of Character
 */

public abstract class BattleCharacter extends Character {

    protected double HP;
    protected int level;
    protected double defense;
   
    /** Default constructor */
    BattleCharacter() {
        super();
        this.level = 1;
        this.HP = this.level*100;
        this.defense = 0;
    }
   
    /** User defined constructor */
    // TODO: Either remove this constructor OR add constructor in Character Class with just (String name) as a parameter
    BattleCharacter(String name, int level, double defense) {
        // super();
        super(name, new Coordinate2D()); // Must pass in Coordinate2D as well
        this.level = level;
        this.HP = level*100;
        this.defense = defense;
    }

    /** User defined constructor */
    BattleCharacter(String name, Coordinate2D coords, int level, double defense) {
        super(name,coords);
        this.level = level;
        this.HP = level*100;
        this.defense = defense;
    }

    /**
     * Take an attack action
     * @param BattleCharacter, the enemy for the attack
     */
    public abstract void attack(BattleCharacter enemy);
    
    /**
     * Dodge an attack
     * @return boolean, true if success, false if not
     */
    protected abstract boolean dodge();
    
    /** Take certain amount of damage when being attacked */
    protected void takeDamage(double damage){ this.HP -= damage;}
    
    /**
     * Check if a Charactor is still alive
     * @return boolean, true if alive, false if dead
     **/
    public boolean isAlive(){
        if(this.HP > 0) return true;
        else return false;
    }
    
    /** GET METHODS **/
    public double getHP(){ return this.HP;}
    public int getLevel(){ return this.level;}
    public double getDefense(){ return this.defense;}
}
