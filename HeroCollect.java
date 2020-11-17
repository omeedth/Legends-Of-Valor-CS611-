
/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class to store Hero datas in game
 **/

 /* External Imports */
import java.util.*;

public class HeroCollect{
    
    protected ArrayList<Warrior> warriors;
    protected ArrayList<Sorcerer> sorcerers;
    protected ArrayList<Paladin> paladins;
    
   /* Default constructor */
    HeroCollect(){
    }
    
    /* Constructor */
    HeroCollect(ArrayList<Warrior> warriors, ArrayList<Sorcerer> sorcerers, ArrayList<Paladin> paladins){
        this.warriors = warriors;
        this.sorcerers = sorcerers;
        this.paladins = paladins;
    }
    
    /** Return a selected hero */
    public Hero selectHero(){
        System.out.print("Select the type of hero 1.Warrior 2.Sorcerer 3.Paladin or Q/q to quit game:");
        Scanner in = new Scanner(System.in);
        String heroType;
        ArrayList<? extends Hero> heroList = warriors;
        boolean loop;
        do{
            loop = false;
            heroType = in.nextLine();
            if(heroType.equals("Q")||heroType.equals("q")){
                System.exit(0);
            }else if(heroType.equals("1")){
                heroList = warriors;
            }else if(heroType.equals("2")){
                heroList = sorcerers;
            }else if(heroType.equals("3")){
                heroList = paladins;
            }else{
                System.out.print("Invalid hero type, please select from 1.Warrior 2.Sorcerer 3.Paladin:");
                loop = true;
            }
        }while(loop == true);
        
        // select hero id
        System.out.print("Enter the ID of the hero you want to select or Q/q to quit game:");
        String key;
        int heroId;
        Hero hero = heroList.get(0);
        do{
            loop = false;
            key = in.nextLine();
            if(key.equals("Q")||key.equals("q")){
                System.exit(0);
            }else if(key.matches("\\d+")){
                heroId = Integer.parseInt(key);
                if (heroId>0 && heroId<=heroList.size()){
                    hero = heroList.get(heroId-1);
                    System.out.println();
                    System.out.println("\u001B[35m You select "+hero.getName()+".\u001B[0m");
                    System.out.println();
                }else{
                    System.out.print("Invalid ID, please select an ID between 0 and "+heroList.size()+":");
                    loop = true;
                }
            }else{
                System.out.print("Invalid ID, please select an ID between 0 and "+heroList.size()+":");
                loop = true;
            }
        }while(loop);
        return hero;
    }
    
    /** Display all heros */
    public void display(){
        System.out.println();
        System.out.println("\u001B[36m------------ ❈ ❈ ❈ ❈ ❈ ❈ --------------\u001B[0m");
        System.out.println();
        System.out.println("       \u001B[36m AVALIABLE HEROES \u001B[0m");
        System.out.println();
        System.out.println("\u001B[36m------------ ❈ ❈ ❈ ❈ ❈ ❈ --------------\u001B[0m");
        System.out.println();
        System.out.println("  ⚔  \u001B[36m WARRIORS \u001B[0m ⚔");
        System.out.println();
        this.displayCat(this.warriors);
        System.out.println();
        System.out.println("  🔮  \u001B[36m SORCERERS \u001B[0m 🔮");
        System.out.println();
        this.displayCat(this.sorcerers);
        System.out.println();
        System.out.println("  🛡  \u001B[36m PALADINS \u001B[0m 🛡");
        System.out.println();
        this.displayCat(this.paladins);
        System.out.println();
    }
    
    /** Display a sepcific type of hero */
    public void displayCat(ArrayList<? extends Hero> heros){
        System.out.println("ID\tName\t\t\tMana  Strength  Agility  Dexterity  Starting Money  Starting Experience");
        System.out.println("----------------------------------------------------------------------------------------------");
        for (int i=0; i<heros.size();i++){
            System.out.print(" "+(i+1)+"   ");
            heros.get(i).display();
        }
    }

}
