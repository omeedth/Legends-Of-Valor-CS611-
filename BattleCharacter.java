/**
 * @author:Zhuyun Chen date: 11/08/2020 Class represent a Battle character
 *                Subclass of Character
 */

public abstract class BattleCharacter extends Character {

    protected double HP;
    protected int level;
    protected double defense;
    
    protected Coordinate2D nexus;
   
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
        super(name); 
        this.level = level;
        this.HP = level*100;
        this.defense = defense;
    }

    // Set original pos of hero
    public void spawn(Coordinate2D nexus){
        this.setPos(nexus);
        this.nexus = nexus;
    }
    
    /** Take action based on rule */
    public abstract void action(Board gameBoard);
    
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
