/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class to load Heroes
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ItemLoader {

    static ArrayList<Weapon> loadWeapons(String filePath){
        File file = new File(filePath);
        try{
            Scanner f = new Scanner(file);
            String line;
            String[] config;
            ArrayList<Weapon> weaponList = new ArrayList<Weapon>();
            while(f.hasNextLine()) {
                line = f.nextLine();
                config = line.split("\\s+");
                weaponList.add(new Weapon(config[0],Integer.parseInt(config[1]),Double.parseDouble(config[2]),Double.parseDouble(config[3]),Integer.parseInt(config[4])));
            }
            f.close();
            return weaponList;
        }catch (FileNotFoundException e) {
            System.out.println("Can't find config for Weapon");
            return null;
        }
    }
    
    static ArrayList<Armor> loadArmors(String filePath){
        File file = new File(filePath);
        try{
            Scanner f = new Scanner(file);
            String line;
            String[] config;
            ArrayList<Armor> armorList = new ArrayList<Armor>();
            while(f.hasNextLine()) {
                line = f.nextLine();
                config = line.split("\\s+");
                armorList.add(new Armor(config[0],Integer.parseInt(config[1]),Double.parseDouble(config[2]),Double.parseDouble(config[3])));
                }
            f.close();
            return armorList;
        }catch (FileNotFoundException e) {
            System.out.println("Can't find config for Armor");
            return null;
        }
    }
    
    static ArrayList<Potion> loadPotions(String filePath){
        File file = new File(filePath);
        try{
            Scanner f = new Scanner(file);
            String line;
            String[] config;
            ArrayList<Potion> potionList = new ArrayList<Potion>();
            while(f.hasNextLine()) {
                line = f.nextLine();
                config = line.split("\\s+");
                String[] attribList = config[4].split("/ ");
                potionList.add(new Potion(config[0],Integer.parseInt(config[2]),Double.parseDouble(config[1]),attribList,Double.parseDouble(config[3])));
                }
            f.close();
            return potionList;
        }catch (FileNotFoundException e) {
            System.out.println("Can't find config for Potion");
            return null;
        }
    }
    
    static ArrayList<Spell> loadFireSpells(String filePath){
        File file = new File(filePath);
        try{
            Scanner f = new Scanner(file);
            String line;
            String[] config;
            ArrayList<Spell> spellList = new ArrayList<Spell>();
            while(f.hasNextLine()) {
                line = f.nextLine();
                config = line.split("\\s+");
                spellList.add(new FireSpell(config[0],Integer.parseInt(config[1]),Double.parseDouble(config[2]),Double.parseDouble(config[3]),Double.parseDouble(config[4])));
            }
            f.close();
            return spellList;
        }catch (FileNotFoundException e) {
            System.out.println("Can't find config for Firespell");
            return null;
        }
    }
    
    static ArrayList<Spell> loadIceSpells(String filePath){
        File file = new File(filePath);
        try{
            Scanner f = new Scanner(file);
            String line;
            String[] config;
            ArrayList<Spell> spellList = new ArrayList<Spell>();
            while(f.hasNextLine()) {
                line = f.nextLine();
                config = line.split("\\s+");
                spellList.add(new IceSpell(config[0],Integer.parseInt(config[1]),Double.parseDouble(config[2]),Double.parseDouble(config[3]),Double.parseDouble(config[4])));
            }
            f.close();
            return spellList;
        }catch (FileNotFoundException e) {
            System.out.println("Can't find config for Ice spell");
            return null;
        }
    }
    
    static ArrayList<Spell> loadLightningSpells(String filePath){
        File file = new File(filePath);
        try{
            Scanner f = new Scanner(file);
            String line;
            String[] config;
            ArrayList<Spell> spellList = new ArrayList<Spell>();
            while(f.hasNextLine()) {
                line = f.nextLine();
                config = line.split("\\s+");
                spellList.add(new LightningSpell(config[0],Integer.parseInt(config[1]),Double.parseDouble(config[2]),Double.parseDouble(config[3]),Double.parseDouble(config[4])));
            }
            f.close();
            return spellList;
        }catch (FileNotFoundException e) {
            System.out.println("Can't find config for Lightning spell");
            return null;
        }
    }
    
    
}
