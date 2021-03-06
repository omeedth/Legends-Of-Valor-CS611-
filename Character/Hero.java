/**
 * @author:Zhuyun Chen
 * Class represent a Hero
 * Subclass of BattleCharacter
 * Superclass of different Hero types (Warrior/Sorcerer/Paladin)
 **/
  
import java.util.*;

public class Hero extends BattleCharacter{

    protected int exp;
    protected double coins;
    
    protected double mana;
    
    protected double str;
    protected double dex;
    protected double agi;
    
    protected Inventory inventory;

    protected Weapon weapon;
    protected Armor armor;

   
    /** Default Constructor **/
    Hero(){
        super();
        this.exp = 0;
        this.coins = 0;
        this.mana = 0;
        this.defense = 0;
        this.str = 1;
        this.dex = 1;
        this.agi = 1;
        this.inventory = new Inventory();
    }
    
    /** User defined constructor */
    Hero(String name, int exp, double mana, double str, double dex, double agi, double coins){
        super(name, 1, 0);
        this.exp = exp;
        this.coins = coins;
        this.mana = mana;
        this.str = str;
        this.dex = dex;
        this.agi = agi;
        this.inventory = new Inventory();
      }

    /** GET METHODS */
    public double getCoins(){ return this.coins;}
    public double getMana(){ return this.mana;}
    
    /** Buy item from the market */
    public void buy(RpgItem product){
        this.coins -= product.getPrice();
        this.inventory.addItem(product);
    }
    
    /** Sale item to the market */
    public void sale(){
        this.inventory.display();
        // Shopping loop
        Scanner in = new Scanner(System.in);
        boolean loop;
        String cont;
        do{
            loop = false;
            this.saleOpt();
            System.out.print("Enter C/c to sell another item or any other key to finish:");
            cont = in.nextLine();
            if(cont.equals("Q")||cont.equals("q")){
                System.exit(0);
            }else if(cont.equals("C")||cont.equals("c")){
                loop = true;
            }
        }while(loop);
    }
    
    public void saleOpt(){
        // Ask user what item catagory they want to buy
        System.out.print("Enter the type of the item you want to sale 1.Weapon 2.Armor 3.Potion 4.Spell or R/r to return:");
        Scanner in = new Scanner(System.in);
        String itemType;
        boolean loop;
        boolean innerLoop;
        do{
            loop = false;
            itemType = in.nextLine();
            if(itemType.equals("Q")||itemType.equals("q")){
                System.exit(0);
            }else if(itemType.equals("R")||itemType.equals("r")){
                return;
            }else if(itemType.equals("1")){
                ArrayList<Weapon> itemList = this.inventory.getWeapons();
                if(itemList.size()<1){
                    System.out.println("\u001B[31m No such item in your inventory! \u001B[0m");
                }else{
                    // select item want to buy
                    System.out.print("Enter the ID of the item you want to sale:");
                    String itemId;
                    do{
                        innerLoop = false;
                        itemId = in.nextLine();
                        int idNum = Integer.parseInt(itemId);
                        if(idNum>0 && idNum<=itemList.size()){
                            RpgItem i = itemList.remove(idNum-1);
                            // Sale with half price
                            double outPrice = i.getPrice()/2;
                            this.coins += outPrice;
                            System.out.print("\u001B[33m Sale "+i.getName()+" with "+outPrice+"coins.\u001B[0m");
                        }else{
                            System.out.print("Invalid item ID, please select an ID between 1 and "+itemList.size()+":");
                            innerLoop = true;
                        }
                    }while(innerLoop);
                }
            }else if(itemType.equals("2")){
                ArrayList<Armor> itemList = this.inventory.getArmors();
                if(itemList.size()<1){
                    System.out.println("\u001B[31m No such item in your inventory! \u001B[0m");
                }else{
                    // select item want to buy
                    System.out.print("Enter the ID of the item you want to sale:");
                    String itemId;
                    do{
                        innerLoop = false;
                        itemId = in.nextLine();
                        int idNum = Integer.parseInt(itemId);
                        if(idNum>0 && idNum<=itemList.size()){
                            RpgItem i = itemList.remove(idNum);
                            // Sale with half price
                            double outPrice = i.getPrice()/2;
                            this.coins += outPrice;
                            System.out.println("\u001B[33m Sale "+i.getName()+" with "+outPrice+"coins.\u001B[0m");
                        }else{
                            System.out.print("Invalid item ID, please select an ID between 1 and "+itemList.size()+":");
                            innerLoop = true;
                        }
                    }while(innerLoop);
                }
            }else if(itemType.equals("3")){
                ArrayList<Potion> itemList = this.inventory.getPotions();
                if(itemList.size()<1){
                    System.out.println("\u001B[31m No such item in your inventory! \u001B[0m");
                }else{
                    // select item want to buy
                    System.out.print("Enter the ID of the item you want to sale:");
                    String itemId;
                    do{
                        innerLoop = false;
                        itemId = in.nextLine();
                        int idNum = Integer.parseInt(itemId);
                        if(idNum>0 && idNum<=itemList.size()){
                            RpgItem i = itemList.remove(idNum-1);
                            // Sale with half price
                            double outPrice = i.getPrice()/2;
                            this.coins += outPrice;
                            System.out.println("\u001B[33m Sale "+i.getName()+" with "+outPrice+"coins.\u001B[0m");
                        }else{
                            System.out.print("Invalid item ID, please select an ID between 1 and "+itemList.size()+":");
                            innerLoop = true;
                        }
                    }while(innerLoop);
                }
            }else if (itemType.equals("4")){
                ArrayList<Spell> itemList = this.inventory.getSpells();
                if(itemList.size()<1){
                    System.out.println("\u001B[31m No such item in your inventory! \u001B[0m");
                }else{
                    // select item want to buy
                    System.out.print("Enter the ID of the item you want to sale:");
                    String itemId;
                    do{
                        innerLoop = false;
                        itemId = in.nextLine();
                        int idNum = Integer.parseInt(itemId);
                        if(idNum>0 && idNum<=itemList.size()){
                            RpgItem i = itemList.remove(idNum-1);
                            // Sale with half price
                            double outPrice = i.getPrice()/2;
                            this.coins += outPrice;
                            System.out.println("\u001B[33m Sale "+i.getName()+" with "+outPrice+"coins.\u001B[0m");
                        }else{
                            System.out.print("Invalid item ID, please select an ID between 1 and "+itemList.size()+":");
                            innerLoop = true;
                        }
                    }while(innerLoop);
                }
            }else{
                System.out.print("Invalid item type, please select from 1.Weapon 2.Armor 3.Potion 4.Spell:");
                loop = true;
            }
        }while(loop);
        
    }
    
    /**
     * Dodge an attack
     * Chance to dodge = agility*0.002
     * @return boolean, true if success, false if not
     * Should only be called automatically when being attacked
     */
    protected boolean dodge(){
        return Math.random() < this.agi*0.002;
    }
    
    /** Take an action in battle */
    public void action(BattleCharacter enemy){
        Monster monster = (Monster)enemy;
        Scanner in = new Scanner(System.in);
        String choice;
        boolean loop;
        System.out.println();
        System.out.println("\u001B[31m Turn "+this.name+" V.S "+enemy.getName()+"\u001B[0m");
        System.out.println();
        do{
            loop = false;
            System.out.print("Choose action 1.Attack 2.Cast Spell 3.Drink Potion 4.Change Weapon 5.Change Armor or I/i to view info:");
            choice = in.nextLine();
            if(choice.equals("Q")||choice.equals("q")){
                System.exit(0);
            }else if(choice.equals("I")||choice.equals("i")){
                System.out.println();
                System.out.println("----------------");
                System.out.println("  YOUR HERO");
                System.out.println("----------------");
                System.out.println("Level   Name\t\t HP  Mana  Equipped Weapon\t\t Equipped Armor");
                System.out.println("---------------------------------------------------------------------");
                this.displayStat();
                System.out.println();
                System.out.println("----------------");
                System.out.println("  YOUR ENEMY");
                System.out.println("----------------");
                System.out.println("Level   Name\t\t HP  Defense");
                System.out.println("------------------------------------");
                enemy.display();
                System.out.println();
                loop = true;
            }else if(choice.equals("1")){
                attack(monster);
            }else if(choice.equals("2")){
                if(this.inventory.getSpells().size()<1){
                    System.out.println("No spell in inventory:");
                    loop = true;
                }else{
                    castSpell(monster);
                }
            }else if(choice.equals("3")){
                if(this.inventory.getPotions().size()<1){
                    System.out.println("No potion in inventory:");
                    loop = true;
                }else{
                    drinkPotion();
                }
            }else if(choice.equals("4")){
                if(this.inventory.getWeapons().size()<1){
                    System.out.println("No weapon in inventory:");
                    loop = true;
                }else{
                    changeWeapon();
                }
            }else if(choice.equals("5")){
                if(this.inventory.getArmors().size()<1){
                    System.out.println("No armor in inventory:");
                    loop = true;
                }else{
                    changeArmor();
                }
            }else{
                System.out.println("Invalid action, please choose 1.Attack 2.Cast Spell 3.Drink Potion 4.Change Weapon 5.Change Armor or I/i to view info:");
                loop = true;
            }
         }while(loop);
      }
   
    /**
     * Attack a monster
     * if success, damage = (strength + weapon damage)*0.05
     */
    public void attack(BattleCharacter enemy){
        Monster monster = (Monster)enemy;
        if (monster.dodge()){
            System.out.println();
            System.out.println("\u001B[33m"+this.name+" attack "+monster.name);
            System.out.println("ATTACK MISS! \u001B[0m");
            System.out.println();
        }else{
            double damage;
            if(this.weapon == null){
                damage = (this.str-monster.getDefense())*0.05;
            }else{
                damage = (this.str+this.weapon.getDamage()-monster.getDefense())*0.05;
            }
            if(damage > 0){
                monster.takeDamage(damage);
            }
            // Display message
            System.out.println();
            System.out.println("\u001B[33m"+this.name+" attack "+monster.name);
            System.out.println("CAUSE "+damage+" DAMAGE. \u001B[0m");
            System.out.println();
        }
    }
    
    /**
     * Cast a spell
     * damage = spellbase_damage*(1+dexterity/10000)
     * deteriorate enemy skill by 10%
     */
    public void castSpell(Monster monster){
        this.inventory.displaySpells();
        System.out.print("Enter the ID of the spell you want to cast:");
        ArrayList<Spell> itemList = this.inventory.getSpells();
        Scanner in = new Scanner(System.in);
        String itemID;
        boolean loop = true;
        do{
            itemID = in.nextLine();
            if(itemID.matches("\\d+")){
                int idNum = Integer.parseInt(itemID);
                if(idNum>0 && idNum<=itemList.size()){
                    loop = false;
                    Spell spell = itemList.get(idNum-1);
                    if(spell.getCost()>this.mana){
                        System.out.println("\u001B[33m"+this.name+" cast "+spell.getName()+" on  "+monster.getName()+"\u001B[0m");
                        System.out.println("\u001B[31m Casting failed, don't have enough mana for the spell! \u001B[0m");
                    }else{
                        // Reduce mana
                        this.mana -= spell.getCost();
                        // Take damage
                        double damage = spell.getDamage()*(1+this.dex/10000);
                        String aff_attrib = spell.getAttrib();
                        monster.takeDamage(damage);
                        monster.reduceAttrib(aff_attrib);
                        // Display message
                        System.out.println("\u001B[33m"+this.name+" cast "+spell.getName()+" on  "+monster.getName()+"\u001B[0m");
                        System.out.println("\u001B[33m CAUSE"+damage+" DAMAGE, REDUCE "+aff_attrib+"\u001B[0m");
                    }
                }else{
                    System.out.println("Please type a valid ID:");
                }
            }else{
                System.out.println("Please type a valid ID:");
            }
        }while(loop);
        
    }
    
    /**
     * Drink potion, Increase each attrib by potion amount
     */
    public void drinkPotion(){
        this.inventory.displayPotions();
        System.out.println("Enter the ID of the potion you want to drink:");
        ArrayList<Potion> itemList = this.inventory.getPotions();
        Scanner in = new Scanner(System.in);
        String itemID;
        boolean loop = true;
        do{
            itemID = in.nextLine();
            if(itemID.matches("\\d+")){
                int idNum = Integer.parseInt(itemID);
                if(idNum>0 && idNum<=itemList.size()){
                    loop = false;
                    Potion potion = itemList.get(idNum-1);
                    System.out.println("\u001B[33m"+this.name+" DRINK "+potion.getName()+"\u001B[0m");
                    double amount = potion.getAmount();
                    String[] attribs = potion.getAttribs();
                    for(String attrib: attribs){
                        if(attrib.equals("Health")){
                            this.HP += amount;
                        }else if(attrib.equals("Health")){
                            this.HP += amount;
                        }else if(attrib.equals("Mana")){
                            this.mana += amount;
                        }else if(attrib.equals("Strength")){
                            this.str += amount;
                        }else if(attrib.equals("Dexterity")){
                            this.dex += amount;
                        }else if(attrib.equals("Agility")){
                            this.agi += amount;
                        }else if(attrib.equals("Defense")){
                            this.defense += amount;
                        }
                    }
                }else{
                    System.out.println("Please type a valid ID:");
                }
            }else{
                System.out.println("Please type a valid ID:");
            }
        }while(loop);
    }
    
    /** Change the weapon in hand */
    public void changeWeapon(){
        // Display all weapons in inventory
        this.inventory.displayWeapons();
        System.out.println("Enter the ID of the weapon you want to choose:");
        ArrayList<Weapon> itemList = this.inventory.getWeapons();
        Scanner in = new Scanner(System.in);
        String itemID;
        boolean loop = true;
        do{
            itemID = in.nextLine();
            if(itemID.matches("\\d+")){
                int idNum = Integer.parseInt(itemID);
                if(idNum>0 && idNum<=itemList.size()){
                    loop = false;
                    this.weapon = itemList.get(idNum-1);
                    System.out.println("\u001B[33m"+this.name+" SWITCH WEAPON TO "+this.weapon.getName()+"\u001B[0m");
                }else{
                    System.out.println("Please type a valid ID:");
                }
            }else{
                System.out.println("Please type a valid ID:");
            }
        }while(loop);
    }
    
    /** Change the armor dressed */
    public void changeArmor(){
        // Display all armors in inventory
        this.inventory.displayArmors();
        System.out.println("Enter the ID of the armor you want to choose:");
        ArrayList<Armor> itemList = this.inventory.getArmors();
        Scanner in = new Scanner(System.in);
        String itemID;
        boolean loop = true;
        do{
            itemID = in.nextLine();
            if(itemID.matches("\\d+")){
                int idNum = Integer.parseInt(itemID);
                if(idNum>0 && idNum<=itemList.size()){
                    loop = false;
                    this.armor = itemList.get(idNum-1);
                    System.out.println("\u001B[33m"+this.name+" SWITCH ARMOR TO "+this.armor.getName()+"\u001B[0m");
                }else{
                    System.out.println("Please type a valid ID:");
                }
            }else{
                System.out.println("Please type a valid ID:");
            }
        }while(loop);
        
        this.defense = this.armor.getDefense();
    }
    
    /** Regain 10% hp and mana */
    public void regain(){
        this.mana *= 1.1;
        this.HP *= 1.1;
    }
    
    /** Revive with half HP */
    public void revive(){
        this.HP = this.level*50;
    }
    
    /**
     * Gain reward from battle
     * Level up when exp = current_level*10
     */
    public void battleReward(int coins, int exp){
        // Gain money and exp
        this.coins += coins;
        this.exp += exp;
        // Check if exp =  level up
        if(this.exp>this.level*10){
            this.exp = 0;
            this.levelUp();
        }
    }
    
    /** Level up a hero */
    public void levelUp(){
        this.level += 1;
        this.HP = this.level*100;
        this.mana *= 1.1;
    }
    
    
    /** Display information of Inventory */
    public void displayInventory(){
        this.inventory.displayWeapons();
        this.inventory.displayArmors();
        this.inventory.displayPotions();
        this.inventory.displaySpells();
    }
    
    /** Display information of the hero */
    public void display(){
        System.out.println(this.name+"\t\t"+this.mana+"  \t"+this.str+"  \t"+this.agi+"  \t"+this.dex+"  \t"+this.coins+"  \t"+this.exp);
    }
    
    /** Display hero stat in battle */
    public void displayStat(){
        System.out.print(this.name+"\t\t"+this.HP+"  \t"+this.mana+"  \t");
        if(this.weapon == null && this.armor == null){
            System.out.println("Not Equipped  \tNot Equipped");
        }else if(this.weapon == null){
            System.out.println("Not Equipped  \t"+this.armor.getName());
        }else if(this.armor == null){
            System.out.println(this.weapon.getName()+"  \tNot Equipped");
        }else{
            System.out.print(this.weapon.getName()+"  \t"+this.armor.getName());
        }
    }
}
