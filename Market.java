/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent a Market in general
 * Superclass of LegendMarket
 **/

import java.util.*;

public abstract class Market{
    
   /* Default constructor */
    Market(){
    }

    /** Welcome information for a player controled team entering the market */
    public void welcome(PlayerTeam team){
        System.out.println();
        System.out.println("\u001B[32m------------ ❈ ❈ ❈ ❈ ❈ ❈ --------------");
        System.out.println();
        System.out.println("        WELCOME TO THE MARKET! ");
        System.out.println();
        System.out.println("------------ ❈ ❈ ❈ ❈ ❈ ❈ --------------\u001B[0m");
        System.out.println();

        Scanner in = new Scanner(System.in);
        if (team.size() == 1){
            this.takeAction((Hero)team.getMember(0));
        }else{
            team.display();
            System.out.print("Please select a team member or R/r to leave market:");
            boolean loop = true;
            String key;
            int memberOpt;
            do{
                key = in.nextLine();
                if (key.equals("Q") || key.equals("q")){
                    System.exit(0);
                }
                if (key.equals("R") || key.equals("r")){
                    loop = false;
                }else if(key.matches("\\d+")){
                   memberOpt = Integer.parseInt(key);
                    if (memberOpt > 0 && memberOpt <= team.size()){
                        this.takeAction((Hero)team.getMember(memberOpt-1));
                        System.out.print("Please select a team member or R/r to leave market:");
                    }else{
                        System.out.print("Please select a valid team member or R/r to leave market:");
                    }
                }else{
                    System.out.print("Please select a valid team member or R/r to leave market:");
                }
            }while(loop);
        }
    }
    
    /**
     * Ask player to decide if they want to sale or buy
     * @param Hero, a hero who's buy or sale in the market
     */
    public void takeAction(Hero hero){
        Scanner in = new Scanner(System.in);
        boolean loop;
        String opt;
        System.out.print("Do you want to 1.Buy 2.Sale or R/r to return:");
        do{
            loop = false;
            opt = in.nextLine();
            if(opt.equals("Q")||opt.equals("q")){
                System.exit(0);
            }else if(opt.equals("R")||opt.equals("r")){
                return;
            }else if (opt.equals("1")){
                this.sale(hero);
            }else if (opt.equals("2")){
                hero.sale();
            }else{
                System.out.print("Invalid option, please select 1.Buy 2.Sale or R/r to return:");
                loop = true;
            }
        }while(loop);
    }
    
    /** Sale things in the market to a player controled heros */
    public abstract void sale(Hero hero);

    /** Display items in the market */
    public abstract void display();
}
