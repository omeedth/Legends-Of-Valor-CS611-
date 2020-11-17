/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent a Market in RPG Game
 **/

import java.util.*;

public class LegendMarket extends Market{

    protected ArrayList<Weapon> weaponList;
    protected ArrayList<Armor> armorList;
    protected ArrayList<Potion> potionList;
    protected ArrayList<Spell> spellList;
    
   /* Default constructor */
    LegendMarket(){
        this.weaponList = new ArrayList<Weapon>();
        this.armorList = new ArrayList<Armor>();
        this.potionList = new ArrayList<Potion>();
        this.spellList = new ArrayList<Spell>();
    }
    
    /* User defined constructor */
    LegendMarket(ArrayList<Weapon> weaponList, ArrayList<Armor> armorList, ArrayList<Potion> potionList, ArrayList<Spell> spellList){
        this.weaponList = weaponList;
        this.armorList = armorList;
        this.potionList = potionList;
        this.spellList = spellList;
    }
    
    /** Sale item in market */
    public void sale(Hero hero){
        // Display all items
        this.display();
        // Shopping loop
        Scanner in = new Scanner(System.in);
        boolean loop;
        String cont;
        do{
            loop = false;
            this.saleOpt(hero);
            System.out.print("Enter C/c to continue shopping, Q/q to exit game, or any other key to finish:");
            cont = in.nextLine();
            if(cont.equals("c")||cont.equals("C")){
                loop = true;
            }else if(cont.equals("Q")||cont.equals("q")){
                System.exit(0);
            }
        }while(loop);
    }
    
    /** Actual shopping */
    public void saleOpt(Hero hero){
        // Ask user what item catagory they want to buy
        System.out.print("Enter the type of the item you want to buy 1.Weapon 2.Armor 3.Potion 4.Spell or R/r to return:");
        Scanner in = new Scanner(System.in);
        String itemType;
        boolean loop;
        do{
            loop = false;
            itemType = in.nextLine();
            if(itemType.equals("Q")||itemType.equals("q")){
                System.exit(0);
            }else if(itemType.equals("R")||itemType.equals("r")){
                return;
            }else if(itemType.equals("1")){
                // select item want to buy
                System.out.print("Enter the ID of the weapon you want to buy or Q/q to return:");
                String key;
                int itemId;
                do{
                    loop = false;
                    key = in.nextLine();
                    if(key.equals("Q")||key.equals("q")){
                        return;
                    }else if(key.matches("\\d+")){
                        itemId = Integer.parseInt(key);
                        if(itemId>0 && itemId<=this.weaponList.size()){
                            Weapon product = this.weaponList.get(itemId-1);
                            if(product.getPrice()>hero.getCoins()){
                                System.out.println();
                                System.out.println("\u001B[31m Don't have enough money for that item!");
                                System.out.println();
                            }else{
                                hero.buy(product);
                                System.out.println();
                                System.out.println("\u001B[33m"+hero.getName()+" bought "+product.getName()+"\u001B[0m");
                                System.out.println();
                            }
                        }else{
                            System.out.print("Invalid ID, please select an ID between 1 and "+this.weaponList.size()+":");
                            loop = true;
                        }
                    }else{
                        System.out.print("Invalid ID, please select an ID between 1 and "+this.weaponList.size()+":");
                        loop = true;
                    }
                }while(loop);
            }else if(itemType.equals("2")){
                // select item want to buy
                System.out.print("Enter the ID of the armor you want to buy:");
                String key;
                int itemId;
                do{
                    loop = false;
                    key = in.nextLine();
                    if(key.equals("Q")||key.equals("q")){
                        return;
                    }else if(key.matches("\\d+")){
                        itemId = Integer.parseInt(key);
                        if(itemId>0 && itemId<=this.armorList.size()){
                            Armor product = this.armorList.get(itemId-1);
                            if(product.getPrice()>hero.getCoins()){
                                System.out.println();
                                System.out.println("\u001B[31m Don't have enough money for that item!");
                                System.out.println();
                            }else{
                                hero.buy(product);
                                System.out.println();
                                System.out.println("\u001B[33m"+hero.getName()+" bought "+product.getName()+"\u001B[0m");
                                System.out.println();
                            }
                        }else{
                            System.out.println("Invalid ID, please select an ID between 1 and "+this.armorList.size()+":");
                            loop = true;
                        }
                    }else{
                        System.out.println("Invalid ID, please select an ID between 1 and "+this.armorList.size()+":");
                        loop = true;
                    }
                }while(loop);
            }else if(itemType.equals("3")){
                // select item want to buy
                System.out.print("Enter the ID of the potion you want to buy or Q/q to return:");
                String key;
                int itemId;
                do{
                    loop = false;
                    key = in.nextLine();
                    
                    if(key.equals("Q")||key.equals("q")){
                        break;
                    }else if(key.matches("\\d+")){
                        itemId = Integer.parseInt(key);
                        if(itemId>0 && itemId<=this.potionList.size()){
                            Potion product = this.potionList.get(itemId-1);
                            if(product.getPrice()>hero.getCoins()){
                                System.out.println();
                                System.out.println("\u001B[31m Don't have enough money for that item!");
                                System.out.println();
                            }else{
                                hero.buy(product);
                                System.out.println();
                                System.out.println("\u001B[33m"+hero.getName()+" bought "+product.getName()+"\u001B[0m");
                                System.out.println();
                            }
                        }else{
                            System.out.println("Invalid item ID, please select an ID between 1 and "+this.potionList.size()+":");
                            loop = true;
                        }
                    }else{
                        System.out.println("Invalid item ID, please select an ID between 1 and "+this.potionList.size()+":");
                        loop = true;
                    }
                }while(loop);
            }else if (itemType.equals("4")){
                // select item want to buy
                System.out.print("Enter the ID of the item you want to buy or Q/q to return:");
                String key;
                int itemId;
                do{
                    loop = false;
                    key = in.nextLine();
                    if(key.equals("Q")||key.equals("q")){
                        break;
                    }else if(key.matches("\\d+")){
                        itemId = Integer.parseInt(key);
                        if(itemId>0 && itemId<=this.spellList.size()){
                            Spell product = this.spellList.get(itemId-1);
                            if(product.getPrice()>hero.getCoins()){
                                System.out.println();
                                System.out.println("\u001B[31m Don't have enough money for that item!");
                                System.out.println();
                            }else{
                                hero.buy(product);
                                System.out.println();
                                System.out.println("\u001B[33m"+hero.getName()+" bought "+product.getName()+"\u001B[0m");
                                System.out.println();
                            }
                        }else{
                            System.out.println("Invalid ID, please select an ID between 1 and "+this.spellList.size()+":");
                            loop = true;
                        }
                    }else{
                        System.out.println("Invalid ID, please select an ID between 1 and "+this.spellList.size()+":");
                        loop = true;
                    }
                }while(loop);
            }else{
                System.out.print("Invalid item type, please select from 1.Weapon 2.Armor 3.Potion 4.Spell or Q/q to return:");
                loop = true;
            }
        }while(loop == true);
        
    }
    
    /** Display Items in the market */
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
        System.out.println("  ⚔  \u001B[36mWeapons For Sale\u001B[0m ⚔");
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
        System.out.println("  🛡  \u001B[36mArmors For Sale\u001B[0m 🛡");
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
        System.out.println("  ⚗  \u001B[36mPotions For Sale\u001B[0m ⚗");
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
        System.out.println("  🔮  \u001B[36mSpells For Sale\u001B[0m 🔮");
        System.out.println();
        System.out.println("ID   Name\t\t Cost  Required Level  Damage  Mana Cost  Type");
        System.out.println("------------------------------------------------------------------------------------------");
        for (int i=0; i<this.spellList.size();i++){
            System.out.print(" "+(i+1)+"   ");
            this.spellList.get(i).display();
        }
    }
    
}
