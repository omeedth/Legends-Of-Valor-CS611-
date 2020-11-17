/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class represent a team of Heros (player controled charactor)
 * Subclass of Team
 **/

import java.util.*;

public class MonsterTeam extends Team{
    
   /* Default constructor */
    MonsterTeam(){
        super();
    }
    
    /** Take turn in a battle */
    public void takeTurn(PlayerTeam heroes){
        for(int i=0;i<members.size();i++){
            // If all monsters faint, end the battle
            if(heroes.isFaint()){ break;}
            Monster m = (Monster)this.members.get(i);
            Hero h = (Hero)heroes.getMember(i);
            // If both hero and monster is alive, fight
            if(h.isAlive() && m.isAlive()){
                m.attack(h);
            }else if(m.isAlive()){
                // if that hero die, attack the first hero that's still alive
                for(int j=0;j<heroes.size();j++){
                    Hero otherH = (Hero)heroes.getMember(j);
                    if(otherH.isAlive()){
                        m.attack(otherH);
                        break;
                    }
                }
            }
        }
    }
    
    /** Display all monsters during battle*/
    public void display(){
        System.out.println("------------ ❈ ❈ ❈ ❈ ❈ ❈ --------------");
        System.out.println("       \u001B[35m MONSTER STAT \u001B[0m");
        System.out.println("------------ ❈ ❈ ❈ ❈ ❈ ❈ --------------");
        System.out.println("ID   Level   Name                    HP      Defense       Damage");
        System.out.println("------------------------------------------------------------------------------");
        for(int i=0;i<this.members.size();i++){
            Monster m = (Monster)this.members.get(i);
            System.out.print((i+1)+"     ");
            m.display();
        }
    }
    
    /** Return true if no monster alive */
    public boolean isFaint(){
        for(Character c: this.members){
            Monster m = (Monster)c;
            if(m.isAlive()){
                return false;
            }
        }
        return true;
    }

    @Override
    public char represent() {
        // TODO Auto-generated method stub
        return 'M';
    }

    @Override
    public void move(Tile destination) {
        // TODO Auto-generated method stub
        System.out.println("Monster Team Moving...");
    }
}
