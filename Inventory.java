/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent an Inventory
 **/

import java.util.*;

public class Inventory{
    
    protected ArrayList<Weapon> weaponList;
    protected ArrayList<Armor> armorList;
    protected ArrayList<Potion> potionList;
    protected ArrayList<Spell> spellList;
    
   /* Default constructor */
    Inventory(){
        this.weaponList = new ArrayList<Weapon>();
        this.armorList = new ArrayList<Armor>();
        this.potionList = new ArrayList<Potion>();
        this.spellList = new ArrayList<Spell>();
    }
    
    /** Add Item to inventory */
    public void addItem(Item item){
        if(item instanceof Weapon){
            this.weaponList.add((Weapon)item);
        }else if(item instanceof Armor){
            this.armorList.add((Armor)item);
        }else if(item instanceof Potion){
            this.potionList.add((Potion)item);
        }else if(item instanceof Spell){
            this.spellList.add((Spell)item);
        }
    }
    
    /** GET METHODS */
    public ArrayList<Weapon> getWeapons(){ return this.weaponList;}
    public ArrayList<Armor> getArmors(){ return this.armorList;}
    public ArrayList<Potion> getPotions(){ return this.potionList;}
    public ArrayList<Spell> getSpells(){ return this.spellList;}
    
    /** GET METHODS WITH SELECT INDEX*/
    public Weapon getWeapon(int index){ return this.weaponList.get(index);}
    public Armor getArmor(int index){ return this.armorList.get(index);}
    public Potion getPotion(int index){ return this.potionList.get(index);}
    public Spell getSpell(int index){ return this.spellList.get(index);}
    
    
    /** REMOVE METHODS */
    public Weapon removeWeapon(int index){ return this.weaponList.remove(index);}
    public Armor removeArmor(int index){ return this.armorList.remove(index);}
    public Potion removePotion(int index){ return this.potionList.remove(index);}
    public Spell removeSpell(int index){ return this.spellList.remove(index);}
    
    /** display the inventory */
    public void display(){
        System.out.println();
        this.displayWeapons();
        System.out.println();
        this.displayArmors();
        System.out.println();
        this.displayPotions();
        System.out.println();
        this.displaySpells();
    }
    
    /** Display all weapons in the market */
    public void displayWeapons(){
        System.out.println("  ⚔  \u001B[36mWeapons Owned\u001B[0m ⚔");
        System.out.println();
        System.out.println("ID   Name\t\t Cost  Required Level  Damage  Required Hands");
        System.out.println("-------------------------------------------------------------------------------------");
        for (int i=0; i<this.weaponList.size();i++){
            System.out.print(" "+(i+1)+"   ");
            this.weaponList.get(i).display();
        }
    }
    
    /** Display all Armors in the market */
    public void displayArmors(){
        System.out.println("  🛡  \u001B[36mArmors Owned\u001B[0m 🛡");
        System.out.println();
        System.out.println("ID   Name\t\t Cost  Required Level  Damage Reduction");
        System.out.println("---------------------------------------------------------------------------");
        for (int i=0; i<this.armorList.size();i++){
            System.out.print(" "+(i+1)+"   ");
            this.armorList.get(i).display();
        }
    }
    
    /** Display all Potions in the market */
    public void displayPotions(){
        System.out.println("  ⚗  \u001B[36mPotions Owned\u001B[0m ⚗");
        System.out.println();
        System.out.println("ID   Name\t\t Cost  Required Level  Attribute Increase  Attribute Affected" );
        System.out.println("------------------------------------------------------------------------------------------------------");
        for (int i=0; i<this.potionList.size();i++){
            System.out.print(" "+(i+1)+"   ");
            this.potionList.get(i).display();
        }
    }
    
    /** Display all Spells in the market */
    public void displaySpells(){
        System.out.println("  🔮  \u001B[36mSpells Owned\u001B[0m 🔮");
        System.out.println();
        System.out.println("ID   Name\t\t Cost  Required Level  Damage  Mana Cost  Type");
        System.out.println("------------------------------------------------------------------------------------------");
        for (int i=0; i<this.spellList.size();i++){
            System.out.print(" "+(i+1)+"   ");
            this.spellList.get(i).display();
        }
    }

}
