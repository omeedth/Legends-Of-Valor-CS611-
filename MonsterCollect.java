/**
 * @author: Zhuyun Chen
 * date: 11/08/20
 * Class to store monster datas in game
 **/

import java.util.*;

public class MonsterCollect{
    
    protected ArrayList<Dragon> dragons;
    protected ArrayList<Exoskeleton> skeletons;
    protected ArrayList<Spirit> spirits;
    
   /* Default constructor */
    MonsterCollect(){
    }
    
    /* Constructor */
    MonsterCollect(ArrayList<Dragon> dragons, ArrayList<Exoskeleton> skeletons, ArrayList<Spirit> spirits){
        this.dragons = dragons;
        this.skeletons = skeletons;
        this.spirits = spirits;
    }

    /**
     * Generate a team of monsters
     * @param int num, number of monsters in team
     * @param int level, level of monsters in team
     */
    public MonsterTeam generateTeam(int num, int level){
        MonsterTeam team = new MonsterTeam();
        return extendTeam(team, num,level);
    }
    
    /**
     * Add new monster to exist team
     * @param int num, number of new monsters in team
     * @param int level, level of new monsters in team
     */
    public MonsterTeam extendTeam(MonsterTeam team, int num, int level){
        ArrayList<Monster> mList = new ArrayList<Monster>();
        // Generate a list of selectable monsters according to level
        for(Dragon d: this.dragons){
            if(d.getLevel() == level){
                mList.add(d);
            }
        }
        for(Exoskeleton s: this.skeletons){
            if(s.getLevel() == level){
                mList.add(s);
            }
        }
        for(Spirit s: this.spirits){
            if(s.getLevel() == level){
                mList.add(s);
            }
        }
        // Randomly add monsters to team
        for (int i=0;i<num;i++){
            team.addMember(mList.get(new Random().nextInt(mList.size())));
        }
        return team;
    }
}
