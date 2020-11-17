
/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class to load Heroes
 **/

/* External Imports */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/* Internal Imports */

public class HeroLoader {
    
    static ArrayList<Warrior> loadWarriors(String filePath){
        File file = new File(filePath);
        try{
            Scanner f = new Scanner(file);
            String line;
            String[] config;
            ArrayList<Warrior> warList = new ArrayList<Warrior>();
            while(f.hasNextLine()) {
                line = f.nextLine();
                config = line.split("\\s+");
                warList.add( new Warrior(config[0],Integer.parseInt(config[6]),Double.parseDouble(config[1]),Double.parseDouble(config[2]),Double.parseDouble(config[3]),Double.parseDouble(config[4]),Double.parseDouble(config[5])));
                }
            f.close();
            return warList;
        }catch (FileNotFoundException e) {
            System.out.println("Can't find config for Warrior.");
            return null;
        }
    }

    static ArrayList<Sorcerer> loadSorcerers(String filePath){
        File file = new File(filePath);
        try{
            Scanner f = new Scanner(file);
            String line;
            String[] config;
            ArrayList<Sorcerer> socList = new ArrayList<Sorcerer>();
            while(f.hasNextLine()) {
                line = f.nextLine();
                config = line.split("\\s+");
                socList.add( new Sorcerer(config[0],Integer.parseInt(config[6]),Double.parseDouble(config[1]),Double.parseDouble(config[2]),Double.parseDouble(config[3]),Double.parseDouble(config[4]),Double.parseDouble(config[5])));
            }
            f.close();
            return socList;
        }catch (FileNotFoundException e) {
            System.out.println("Can't find config for Sorcerer.");
            return null;
        }
    }
    
    static ArrayList<Paladin> loadPaladins(String filePath){
        File file = new File(filePath);
        try{
            Scanner f = new Scanner(file);
            String line;
            String[] config;
            ArrayList<Paladin> paList = new ArrayList<Paladin>();
            while(f.hasNextLine()) {
                line = f.nextLine();
                config = line.split("\\s+");
                paList.add( new Paladin(config[0],Integer.parseInt(config[6]),Double.parseDouble(config[1]),Double.parseDouble(config[2]),Double.parseDouble(config[3]),Double.parseDouble(config[4]),Double.parseDouble(config[5])));
            }
            f.close();
            return paList;
        }catch (FileNotFoundException e) {
            System.out.println("Can't find config for Paladin.");
            return null;
        }
    }
}
