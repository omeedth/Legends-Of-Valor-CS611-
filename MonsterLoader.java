/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class to load Monsters
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MonsterLoader {
    
    static ArrayList<Dragon> loadDragons(String filePath){
        File file = new File(filePath);
        try{
            Scanner f = new Scanner(file);
            String line;
            String[] config;
            ArrayList<Dragon> draList = new ArrayList<Dragon>();
            while(f.hasNextLine()) {
                line = f.nextLine();
                config = line.split("\\s+");
            draList.add(new Dragon(config[0], Integer.parseInt(config[1]),Double.parseDouble(config[2]),Double.parseDouble(config[3]),Double.parseDouble(config[4])));
            }
            f.close();
            return draList;
        }catch (FileNotFoundException e) {
            System.out.println("Can't find config for Dragon.");
            return null;
        }
    }
    
    static ArrayList<Exoskeleton> loadExoskeletons(String filePath){
        File file = new File(filePath);
        try{
            Scanner f = new Scanner(file);
            String line;
            String[] config;
            ArrayList<Exoskeleton> exoList = new ArrayList<Exoskeleton>();
            while(f.hasNextLine()) {
                line = f.nextLine();
                config = line.split("\\s+");
                exoList.add(new Exoskeleton(config[0], Integer.parseInt(config[1]),Double.parseDouble(config[2]),Double.parseDouble(config[3]),Double.parseDouble(config[4])));
            }
            f.close();
            return exoList;
        }catch (FileNotFoundException e) {
            System.out.println("Can't find config for Exoskeleton.");
            return null;
        }
    }
    
    static ArrayList<Spirit> loadSpirits(String filePath){
        File file = new File(filePath);
        try{
            Scanner f = new Scanner(file);
            String line;
            String[] config;
            ArrayList<Spirit> spiList = new ArrayList<Spirit>();
            while(f.hasNextLine()) {
                line = f.nextLine();
                config = line.split("\\s+");
                spiList.add(new Spirit(config[0], Integer.parseInt(config[1]),Double.parseDouble(config[2]),Double.parseDouble(config[3]),Double.parseDouble(config[4])));
                }
            f.close();
            return spiList;
        }catch (FileNotFoundException e) {
            System.out.println("Can't find config for Spirit.");
            return null;
        }
    
    }
}
